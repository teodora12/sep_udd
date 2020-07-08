package com.ftn.sep_udd.service;


import com.ftn.sep_udd.dto.WorkDataDTO;

import com.ftn.sep_udd.model.Work;
import com.ftn.sep_udd.model.WorkES;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public interface ESWorkService {

    WorkES findWorkByTitle(String title);

    void saveWorkData(WorkDataDTO workDataDTO) throws UnsupportedEncodingException;

    WorkES findWorkById(Integer id);

    public String parsePDF(Work work) throws UnsupportedEncodingException;

}
