package com.ftn.sep_udd.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/test", produces = MediaType.APPLICATION_JSON_VALUE)
public class TestController {


    @GetMapping(value = "/get")
    public ResponseEntity testMethod() {

        return ResponseEntity.ok().build();
    }


}
