package com.ftn.sep_udd.controller;

import com.ftn.sep_udd.service.ESWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/search")
public class SearchController {


    @Autowired
    private ESWorkService esWorkService;

}
