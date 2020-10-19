package com.ftn.sep_udd.repository;

import com.ftn.sep_udd.model.WorkES;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface WorkESRepository extends ElasticsearchRepository<WorkES, Integer> {

    WorkES findWorkESByTitle(String title);

    WorkES save(WorkES workES);

    WorkES findWorkESById(Integer id);
}
