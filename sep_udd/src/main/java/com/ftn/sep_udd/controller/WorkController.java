package com.ftn.sep_udd.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftn.sep_udd.dto.*;
import com.ftn.sep_udd.model.Buying;
import com.ftn.sep_udd.model.Magazine;
import com.ftn.sep_udd.model.ScientificField;
import com.ftn.sep_udd.model.Work;
import com.ftn.sep_udd.security.TokenUtils;
import com.ftn.sep_udd.service.*;
import org.apache.pdfbox.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/works", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class WorkController {

    @Autowired
    private ESWorkService esWorkService;

    @Autowired
    private WorkService workService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ScientificFieldService scientificFieldService;

    @Autowired
    private MagazineService magazineService;

    private static final String PAYPAL_URI= "http://localhost:8090/api/payment";

    @Autowired
    private BuyingService buyingService;

    @PostMapping(value = "/submitWorkData")
    public ResponseEntity submitWorkData(@RequestBody WorkDataDTO dto) throws UnsupportedEncodingException {

        this.esWorkService.saveWorkData(dto);

        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/getWorks/{id}")
    public ResponseEntity getWorks(@PathVariable Long id) {

        Magazine magazine = this.magazineService.findMagazineById(id);
        List<WorkDTO> workDTOS = new ArrayList<>();
        for(Work w: magazine.getWorks()){
            WorkDTO workDTO = new WorkDTO();
            workDTO.setTitle(w.getTitle());
            workDTO.setAbstr(w.getAbstr());
            workDTO.setMagazineOpenAccess(magazine.isOpenAccess());
            workDTO.setId(w.getId());

            Buying buying = this.buyingService.findBuyingByProductIdAndProductType(Long.valueOf(w.getId()), "WORK");
            if(buying != null){
                if(buying.getStatus().equals("PAID")){
                    workDTO.setPaid(true);
                } else {
                    workDTO.setPaid(false);
                }
            }
            workDTOS.add(workDTO);
        }
        if(workDTOS.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity(workDTOS, HttpStatus.OK);

    }

    @GetMapping(value = "/buy/{id}")
    public Map<String, String> buyWork(@PathVariable Integer id, @RequestHeader(value = "Authorization") String authorization){

        String email = getEmailFromToken(authorization);

        HttpEntity requestEntity = this.workService.buyWork(id,email);

        if(requestEntity == null){
            return (Map<String, String>) ResponseEntity.notFound().build();
        }

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> resp = restTemplate.postForEntity(PAYPAL_URI + "/pay",requestEntity, String.class);
        HashMap<String, String> map = new HashMap<>();
        map.put("url", resp.getBody());
        System.out.println(resp.getBody() + " url");

        return map;
    }

    public String getEmailFromToken (String authorization) {

        String email = TokenUtils.getEmailFromToken(authorization);
        if (email == null) {
            try {
                throw new Exception("Jwt error");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return email;
    }


    @GetMapping(path = "/downloadWork/get/{id}")
    public @ResponseBody
    ResponseEntity downloadWork(@PathVariable Integer id) throws IOException {

        Work work = workService.findWorkById(id);

        InputStream in = new FileInputStream(new File("src/main/pdf/" + work.getPdf()));

        byte[] bytes = IOUtils.toByteArray(in);


        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/pdf"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "rad.pdf\"")
                .body(new ByteArrayResource(bytes));
    }


    @GetMapping(value = "/getScientificFields")
    public ResponseEntity getScientificFields() {

        List<ScientificFieldDTO> dtos = this.scientificFieldService.getAll();
        return new ResponseEntity(dtos, HttpStatus.OK);
    }

    @GetMapping(value = "/get/{param}/{phrase}/{type}")
    public ResponseEntity customSearch(@PathVariable String param, @PathVariable Integer phrase, @PathVariable String type) throws JsonProcessingException {


        System.out.println("fraza: " + phrase);
        System.out.println("parametar: " + param);
        System.out.println("tip: " + type);


        String query = "";
        if (phrase == 0) {

            query = "{\n" +
                    "    \"query\": {\n" +
                    "        \"match\" : {\n" +
                    "            \"" + type + "\" : {\n" +
                    "                \"query\" : \"" + param + "\"\n" +
                    "            }\n" +
                    "           \n" +
                    "        }\n" +
                    "    },\n" +
                    "    \"highlight\" : {\n" +
                    "        \"fields\" : {\n" +
                    "            \"" + type + "\" : {\n" +
                    "            \t\"type\":\"plain\"\n" +
                    "            }\n" +
                    "        }\n" +
                    "    }\n" +
                    "}";


        } else if (phrase == 1) {

            query = "{\n" +
                    "    \"query\": {\n" +
                    "        \"match_phrase\" : {\n" +
                    "            \"" + type + "\" : {\n" +
                    "                \"query\" : \"" + param + "\"\n" +
                    "         \n" +
                    "            }\n" +
                    "           \n" +
                    "        }\n" +
                    "    },\n" +
                    "    \"highlight\" : {\n" +
                    "        \"fields\" : {\n" +
                    "            \"" + type + "\" : {\n" +
                    "            \t\"type\":\"plain\"\n" +
                    "            }\n" +
                    "        }\n" +
                    "    }\n" +
                    "}";
        }


        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonquery = objectMapper.readTree(query);
        HttpEntity<JsonNode> request = new HttpEntity<>(jsonquery);

        String resourceUrl = "http://localhost:9200/journal/work/_search?pretty";
        ResponseEntity<String> response = restTemplate.postForEntity(resourceUrl, request, String.class);
        JsonNode rootNode = objectMapper.readTree(response.getBody());
        JsonNode locatedNode = rootNode.path("hits").path("hits");
        List<WorkEsDTO> retVal = getWorkEsDTO(locatedNode, type);


        return new ResponseEntity(retVal, HttpStatus.OK);

//        return ResponseEntity.ok().build();
    }


    public List<WorkEsDTO> getWorkEsDTO(JsonNode node, String highlight) {

        List<WorkEsDTO> retVal = new ArrayList<>();

        for (int i = 0; i < node.size(); i++) {

            WorkEsDTO workEsDTO = new WorkEsDTO();

            Integer workId = Integer.parseInt(node.get(i).path("_source").path("id").asText());

            workEsDTO.setId(workId);
            workEsDTO.setTitle(node.get(i).path("_source").path("title").asText());
            workEsDTO.setJournalTitle(node.get(i).path("_source").path("journalTitle").asText());
            workEsDTO.setAbstr(node.get(i).path("_source").path("abstr").asText());
            workEsDTO.setKeyTerms(node.get(i).path("_source").path("keyTerms").asText());
            workEsDTO.setScientificField(node.get(i).path("_source").path("scientificField").asText());
            workEsDTO.setAuthors(node.get(i).path("_source").path("authors").asText());


            String highText = "";
            for (int j = 0; j < node.get(i).path("highlight").path(highlight).size(); j++) {
                highText += node.get(i).path("highlight").path(highlight).get(j).asText() + "...";
            }

            workEsDTO.setHighlight(highText);
            retVal.add(workEsDTO);
        }
        return retVal;

    }


    @PostMapping(path = "/get/advancedSearch", consumes = "application/json", produces = "application/json")
    public ResponseEntity advancedSearch(@RequestBody CombinedSearchDTO dto) throws JsonProcessingException {


        String must = "\"must\" : [\n";
        String should = "\"should\" : [\n";
        String fraza = "";

        boolean shouldBe = false;
        boolean mustBe = false;

        if (dto.isPhraseJournalTitle()) {
            fraza = "match_phrase";
        } else {
            fraza = "match";
        }
        if (dto.isJournalTitleChecked() && !dto.getJournalTitle().equals("")) {
            must += "{ \"" + fraza + "\" : { \"journalTitle\" : \"" + dto.getJournalTitle() + "\" } },";
            mustBe = true;
        } else if (!dto.isJournalTitleChecked() && !dto.getJournalTitle().equals("")) {
            should += "{ \"" + fraza + "\" : { \"journalTitle\" : \"" + dto.getJournalTitle() + "\" } },";
            shouldBe = true;
        }

        if (dto.isPhraseTitle()) {
            fraza = "match_phrase";
        } else {
            fraza = "match";
        }
        if (dto.isTitleChecked() && !dto.getTitle().equals("")) {
            must += "{ \"" + fraza + "\" : { \"title\" : \"" + dto.getTitle() + "\" } },";
            mustBe = true;

        } else if (!dto.isTitleChecked() && !dto.getTitle().equals("")) {
            should += "{ \"" + fraza + "\" : { \"title\" : \"" + dto.getTitle() + "\" } },";
            shouldBe = true;
        }

        if (dto.isPhraseKeyTerms()) {
            fraza = "match_phrase";
        } else {
            fraza = "match";
        }
        if (dto.isKeyTermsChecked() && !dto.getKeyTerms().equals("")) {
            must += "{ \"" + fraza + "\" : { \"keyTerms\" : \"" + dto.getKeyTerms() + "\" } },";
            mustBe = true;

        } else if (!dto.isKeyTermsChecked() && !dto.getKeyTerms().equals("")) {
            should += "{ \"" + fraza + "\" : { \"keyTerms\" : \"" + dto.getKeyTerms() + "\" } },";
            shouldBe = true;
        }

        if (dto.isPhraseAuthors()) {
            fraza = "match_phrase";
        } else {
            fraza = "match";
        }
        if (dto.isAuthorsChecked() && !dto.getAuthors().equals("")) {
            must += "{ \"" + fraza + "\" : { \"authors\" : \"" + dto.getAuthors() + "\" } },";
            mustBe = true;

        } else if (!dto.isAuthorsChecked() && !dto.getAuthors().equals("")) {
            should += "{ \"" + fraza + "\" : { \"authors\" : \"" + dto.getAuthors() + "\" } },";
            shouldBe = true;
        }

        if (dto.isPhraseText()) {
            fraza = "match_phrase";
        } else {
            fraza = "match";
        }
        if (dto.isTextChecked() && !dto.getText().equals("")) {
            must += "{ \"" + fraza + "\" : { \"abstr\" : \"" + dto.getText() + "\" } },";
            mustBe = true;

        } else if (!dto.isTextChecked() && !dto.getText().equals("")) {
            should += "{ \"" + fraza + "\" : { \"abstr\" : \"" + dto.getText() + "\" } },";
            shouldBe = true;
        }
        must = must.substring(0, must.length() - 1);
        should = should.substring(0, should.length() - 1);

        must += "],";
        should += "],";

        String query = "{\n" +
                "  \"query\": {\n" +
                "    \"bool\" : {\n";
        if (mustBe) {
            query += must;
        }

        if (shouldBe) {
            query += should;
        }

        query += "         \"boost\" : 1.0\n" +
                "    }\n" +
                "  },\n" +
                "\"highlight\" : {\n" +
                "        \"fields\" : {\n" +
                "            \"journalTitle\" : {" +
                "               \t\"type\":\"plain\"\n" +
                "},\n" +
                "        \"title\" : {" +
                "           \t\"type\":\"plain\"\n" +
                "},\n" +
                "        \"keyTerms\" : {" +
                "             \t\"type\":\"plain\"\n" +
                "},\n" +
                "       \"abstr\" : {" +
                "               \t\"type\":\"plain\"\n" +
                "},\n" +
                "        \"authors\" : {" +
                "               \t\"type\":\"plain\"\n" +
                "}\n" +
                "        }\n" +
                "    }" +
                "}";


        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonquery = objectMapper.readTree(query);
        HttpEntity<JsonNode> request = new HttpEntity<>(jsonquery);

        String resourceUrl = "http://localhost:9200/journal/work/_search?pretty";
        ResponseEntity<String> response = restTemplate.postForEntity(resourceUrl, request, String.class);
        JsonNode rootNode = objectMapper.readTree(response.getBody());
        JsonNode locatedNode = rootNode.path("hits").path("hits");
        List<WorkEsDTO> retVal = getWorkEsDTOAdvanced(locatedNode);

        return new ResponseEntity<>(retVal, HttpStatus.OK);


//        return ResponseEntity.ok().build();
    }

    public List<WorkEsDTO> getWorkEsDTOAdvanced(JsonNode node) {

        List<WorkEsDTO> retVal = new ArrayList<>();

        for (int i = 0; i < node.size(); i++) {

            WorkEsDTO workEsDTO = new WorkEsDTO();

            Integer workId = Integer.parseInt(node.get(i).path("_source").path("id").asText());


            workEsDTO.setId(workId);
            workEsDTO.setTitle(node.get(i).path("_source").path("title").asText());
            workEsDTO.setJournalTitle(node.get(i).path("_source").path("journalTitle").asText());
            workEsDTO.setAbstr(node.get(i).path("_source").path("abstr").asText());
            workEsDTO.setKeyTerms(node.get(i).path("_source").path("keyTerms").asText());
            workEsDTO.setScientificField(node.get(i).path("_source").path("scientificField").asText());
            workEsDTO.setAuthors(node.get(i).path("_source").path("authors").asText());


            String highText = node.get(i).path("highlight").toString();

            highText = highText.replace("\"", "");
            highText = highText.replace("{", "...");
            highText = highText.replace("}", "...");
            highText = highText.replace("[", " ");
            highText = highText.replace("]", " ");
            highText = highText.replace("\\r\\n", "...");
            workEsDTO.setHighlight(highText);

            retVal.add(workEsDTO);
        }
        return retVal;

    }


}
