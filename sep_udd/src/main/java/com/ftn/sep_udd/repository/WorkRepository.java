package com.ftn.sep_udd.repository;

import com.ftn.sep_udd.model.Work;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkRepository extends JpaRepository<Work, Integer> {

    Work findWorkById(Integer id);
}
