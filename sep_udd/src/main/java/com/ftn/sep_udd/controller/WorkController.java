package com.ftn.sep_udd.controller;

import com.ftn.sep_udd.dto.ScientificFieldDTO;
import com.ftn.sep_udd.dto.WorkDataDTO;
import com.ftn.sep_udd.model.WorkES;
import com.ftn.sep_udd.service.ESWorkService;
import com.ftn.sep_udd.service.ScientificFieldService;
import com.ftn.sep_udd.service.WorkService;
import javafx.concurrent.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/works", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class WorkController {

    @Autowired
    private ESWorkService esWorkService;

    @Autowired
    private WorkService workService;

    @Autowired
    private ScientificFieldService scientificFieldService;


    @PostMapping(value = "/submitWorkData")
    public ResponseEntity submitWorkData(@RequestBody WorkDataDTO dto) {

        this.esWorkService.saveWorkData(dto);
        this.workService.savePdf(dto);

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/get/{naslov}")
    public ResponseEntity submitWorkData(@PathVariable String naslov) {

        WorkES workES = this.esWorkService.findWorkByTitle(naslov);
        System.out.println(workES + " ...");

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/gett/{id}")
    public ResponseEntity submitWorkData(@PathVariable Integer id) {

        WorkES workES = this.esWorkService.findWorkById(id);
        System.out.println(workES + " ...");

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/getScientificFields")
    public ResponseEntity getScientificFields() {

        List<ScientificFieldDTO> dtos = this.scientificFieldService.getAll();
        return new ResponseEntity(dtos, HttpStatus.OK);
    }

}
