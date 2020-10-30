package com.ftn.sep_udd.service;

import com.ftn.sep_udd.model.Buying;
import org.springframework.stereotype.Service;

@Service
public interface BuyingService {

    void save(Buying buying);
    Buying findBuyingByMagazineId(Long id);
    Buying findBuyingByProductIdAndProductType(Long id, String productType);
    void changeStatus(Long id, String status);


}
