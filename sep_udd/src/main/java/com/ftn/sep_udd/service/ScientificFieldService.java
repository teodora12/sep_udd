package com.ftn.sep_udd.service;

import com.ftn.sep_udd.dto.ScientificFieldDTO;
import com.ftn.sep_udd.model.ScientificField;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface ScientificFieldService {

    List<ScientificFieldDTO> getAll();

}
