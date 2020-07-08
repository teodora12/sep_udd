package com.ftn.sep_udd.service.ESService.impl;

import com.ftn.sep_udd.dto.WorkDataDTO;
import com.ftn.sep_udd.model.Work;
import com.ftn.sep_udd.repository.WorkRepository;
import com.ftn.sep_udd.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;

@Service
public class WorkServiceImpl implements WorkService {

    @Autowired
    private WorkRepository workRepository;

    @Override
    public Work findWorkById(Integer id) {
        return this.workRepository.findWorkById(id);
    }

    @Override
    public Work saveWork(Work work) {
        return this.workRepository.save(work);
    }

    @Override
    public void savePdf(WorkDataDTO dto) {
        try {

            BASE64Decoder decoder = new BASE64Decoder();
            byte[] decodedBytes = decoder.decodeBuffer(dto.getFile());

            File file = new File("src/main/pdf/" + dto.getFileName());
            FileOutputStream fop = new FileOutputStream(file);
            fop.write(decodedBytes);
            fop.flush();
            fop.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
