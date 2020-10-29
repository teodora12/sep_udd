package com.ftn.sep_udd.service;

import com.ftn.sep_udd.dto.MagazineDTO;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MagazineService {

    List<MagazineDTO> getAllMagazines();
    HttpEntity buyMagazine(Long id, String email);
}
