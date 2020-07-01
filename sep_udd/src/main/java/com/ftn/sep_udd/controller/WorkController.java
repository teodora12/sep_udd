package com.ftn.sep_udd.controller;

import com.ftn.sep_udd.dto.WorkDataDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
@RequestMapping(value = "/api/works", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins="http://localhost:4200")
public class WorkController {


    @PostMapping(value = "/submitWorkData")
    public ResponseEntity submitWorkData(@RequestBody WorkDataDTO dto) throws IOException {


        BASE64Decoder decoder = new BASE64Decoder();
        byte[] decodedBytes = decoder.decodeBuffer(dto.getFile());

        File file = new File("src/main/pdf/" + dto.getFileName());
        FileOutputStream fop = new FileOutputStream(file);
        fop.write(decodedBytes);
        fop.flush();
        fop.close();

        return ResponseEntity.ok().build();
    }

}
