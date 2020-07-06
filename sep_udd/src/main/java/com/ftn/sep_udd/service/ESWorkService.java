package com.ftn.sep_udd.service;


import com.ftn.sep_udd.dto.WorkDataDTO;
import com.ftn.sep_udd.dto.WorkEsDTO;
import com.ftn.sep_udd.model.WorkES;
import org.springframework.stereotype.Service;

@Service
public interface ESWorkService {

    WorkES findWorkByTitle(String title);
    void saveWorkData(WorkDataDTO workDataDTO);
    WorkES findWorkById(Integer id);

}
