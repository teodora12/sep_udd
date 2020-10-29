package com.ftn.sep_udd.service.ESService.impl;

import com.ftn.sep_udd.model.Buying;
import com.ftn.sep_udd.repository.BuyingRepository;
import com.ftn.sep_udd.service.BuyingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuyingServiceImpl implements BuyingService {

    @Autowired
    private BuyingRepository buyingRepository;


    @Override
    public void save(Buying buying) {
        this.buyingRepository.save(buying);
    }

    @Override
    public Buying findBuyingByMagazineId(Long id) {
        return this.buyingRepository.findBuyingByProductId(id);
    }

    @Override
    public void changeStatus(Long id, String status) {
        Buying buying = this.buyingRepository.findBuyingById(id);
        if(buying != null) {
            System.out.println(buying.getStatus() + " before");
            buying.setStatus(status);
            this.buyingRepository.save(buying);
            System.out.println(buying.getStatus() + " after");
        }
    }
}
