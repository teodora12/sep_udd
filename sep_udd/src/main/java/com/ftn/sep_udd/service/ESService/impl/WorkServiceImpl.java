package com.ftn.sep_udd.service.ESService.impl;

import com.ftn.sep_udd.dto.BuyMagazineDTO;
import com.ftn.sep_udd.dto.WorkDataDTO;
import com.ftn.sep_udd.model.Buying;
import com.ftn.sep_udd.model.User;
import com.ftn.sep_udd.model.Work;
import com.ftn.sep_udd.repository.WorkRepository;
import com.ftn.sep_udd.service.BuyingService;
import com.ftn.sep_udd.service.UserService;
import com.ftn.sep_udd.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.Arrays;

@Service
public class WorkServiceImpl implements WorkService {

    @Autowired
    private WorkRepository workRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BuyingService buyingService;


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

    @Override
    public HttpEntity buyWork(Integer id, String email) {

        User user = this.userService.findUserByEmail(email);

        BuyMagazineDTO buyMagazineDTO = new BuyMagazineDTO();
        Work work = this.workRepository.findWorkById(id);

        Buying buying = new Buying();
        buying.setStatus("NEW");
        buying.setProductId(Long.valueOf(work.getId()));
        buying.setProductType("WORK");
        this.buyingService.save(buying);

        buyMagazineDTO.setProductId(Long.valueOf(id));
        buyMagazineDTO.setProductName(work.getTitle());
        buyMagazineDTO.setUserEmail(user.getEmail());
        buyMagazineDTO.setPrice(work.getPrice());
        buyMagazineDTO.setBuyingId(buying.getId());

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity requestEntity = new HttpEntity<>(buyMagazineDTO, requestHeaders);

        return requestEntity;

    }

}
