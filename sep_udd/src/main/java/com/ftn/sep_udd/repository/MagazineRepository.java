package com.ftn.sep_udd.repository;

import com.ftn.sep_udd.model.Magazine;
import com.ftn.sep_udd.model.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MagazineRepository extends JpaRepository<Magazine, Long> {

    Magazine findMagazineById(Long id);


}
