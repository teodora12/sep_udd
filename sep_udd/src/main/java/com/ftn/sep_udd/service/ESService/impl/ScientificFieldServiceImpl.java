package com.ftn.sep_udd.service.ESService.impl;

import com.ftn.sep_udd.dto.ScientificFieldDTO;
import com.ftn.sep_udd.model.ScientificField;
import com.ftn.sep_udd.repository.ScientificFieldRepository;
import com.ftn.sep_udd.service.ScientificFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScientificFieldServiceImpl implements ScientificFieldService {


    @Autowired
    private ScientificFieldRepository scientificFieldRepository;

    @Override
    public List<ScientificFieldDTO> getAll() {

        List<ScientificFieldDTO> dtos = new ArrayList<>();
        List<ScientificField> scientificFields = this.scientificFieldRepository.findAll();

        for (ScientificField scientificField : scientificFields) {
            ScientificFieldDTO dto = new ScientificFieldDTO(scientificField);
            dtos.add(dto);
        }

        return dtos;
    }
}
