package com.ftn.sep_udd.repository;

import com.ftn.sep_udd.model.Magazine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MagazineRepository extends JpaRepository<Magazine, Long> {

    Magazine findMagazineById(Long id);

}
