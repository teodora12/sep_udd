package com.ftn.sep_udd.repository;

import com.ftn.sep_udd.model.Buying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyingRepository extends JpaRepository<Buying, Long> {

    Buying findBuyingByProductId(Long id);
    Buying findBuyingById(Long id);
    Buying findBuyingByProductIdAndProductType(Long id, String type);
    void deleteBuyingById(Long id);
}
