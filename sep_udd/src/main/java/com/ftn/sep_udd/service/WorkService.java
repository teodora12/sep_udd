package com.ftn.sep_udd.service;

import com.ftn.sep_udd.dto.WorkDataDTO;
import com.ftn.sep_udd.model.Work;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;

@Service
public interface WorkService {

    Work findWorkById(Integer id);

    Work saveWork(Work work);

    public void savePdf(WorkDataDTO work);


    HttpEntity buyWork(Integer id, String email);
}
