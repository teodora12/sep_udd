package com.ftn.sep_udd.controller;

import com.ftn.sep_udd.dto.MagazineDTO;
import com.ftn.sep_udd.dto.WorkDataDTO;
import com.ftn.sep_udd.model.Buying;
import com.ftn.sep_udd.security.TokenUtils;
import com.ftn.sep_udd.service.BuyingService;
import com.ftn.sep_udd.service.MagazineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/magazines", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class MagazineController {


    @Autowired
    private MagazineService magazineService;

    @Autowired
    private BuyingService buyingService;

    private static final String PAYPAL_URI= "http://localhost:8090/api/payment";

    @GetMapping(value = "/getAll")
    public ResponseEntity getAll() {

        List<MagazineDTO> magazineDTOS = this.magazineService.getAllMagazines();


        for(MagazineDTO magazineDTO: magazineDTOS){
            Buying buying = this.buyingService.findBuyingByProductIdAndProductType(magazineDTO.getId(), "MAGAZINE");
            if(buying != null){
                if(buying.getStatus().equals("PAID")){
                    magazineDTO.setPaid(true);
                } else {
                    magazineDTO.setPaid(false);
                }
            }
        }

        if(magazineDTOS.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity(magazineDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/buy/{id}")
    public Map<String, String> buyMagazine(@PathVariable Long id, @RequestHeader(value = "Authorization") String authorization){

        String email = getEmailFromToken(authorization);

        HttpEntity requestEntity = this.magazineService.buyMagazine(id,email);

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

    @GetMapping(value = "/complete/{id}")
    public void successOrder(@PathVariable Long id){
        this.buyingService.changeStatus(id, "PAID");

    }

    @GetMapping(value = "/cancel/{id}")
    public void cancelOrder(@PathVariable Long id){

        this.buyingService.changeStatus(id, "CANCELLED");

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

}
