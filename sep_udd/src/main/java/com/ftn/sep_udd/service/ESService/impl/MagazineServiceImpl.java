package com.ftn.sep_udd.service.ESService.impl;

import com.ftn.sep_udd.dto.BuyMagazineDTO;
import com.ftn.sep_udd.dto.MagazineDTO;
import com.ftn.sep_udd.model.Buying;
import com.ftn.sep_udd.model.Magazine;
import com.ftn.sep_udd.model.User;
import com.ftn.sep_udd.model.Work;
import com.ftn.sep_udd.repository.MagazineRepository;
import com.ftn.sep_udd.service.BuyingService;
import com.ftn.sep_udd.service.MagazineService;
import com.ftn.sep_udd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MagazineServiceImpl implements MagazineService {

    @Autowired
    private MagazineRepository magazineRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BuyingService buyingService;

    @Override
    public HttpEntity buyMagazine(Long id, String email) {

        User user = this.userService.findUserByEmail(email);

//        Buying buying = null;
//        Magazine magazine = null;
//        Work work = null;
        BuyMagazineDTO buyMagazineDTO = new BuyMagazineDTO();

        Magazine magazine = this.magazineRepository.findMagazineById(id);

        Buying buying = new Buying();
        buying.setStatus("NEW");
        buying.setProductId(magazine.getId());
        this.buyingService.save(buying);

        buyMagazineDTO.setProductId(id);
        buyMagazineDTO.setProductName(magazine.getTitle());
        buyMagazineDTO.setUserEmail(user.getEmail());
        buyMagazineDTO.setPrice(magazine.getPrice());
        buyMagazineDTO.setBuyingId(buying.getId());

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity requestEntity = new HttpEntity<>(buyMagazineDTO, requestHeaders);

        return requestEntity;
    }

    @Override
    public List<MagazineDTO> getAllMagazines() {

        List<MagazineDTO> magazineDTOS = new ArrayList<>();
        List<Magazine> magazines = this.magazineRepository.findAll();

        for (Magazine magazine: magazines){
            MagazineDTO magazineDTO = new MagazineDTO(magazine);
            magazineDTOS.add(magazineDTO);
        }

        return magazineDTOS;
    }
}
