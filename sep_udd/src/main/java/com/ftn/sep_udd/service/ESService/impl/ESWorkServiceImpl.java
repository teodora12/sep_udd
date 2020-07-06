package com.ftn.sep_udd.service.ESService.impl;

import com.ftn.sep_udd.dto.WorkDataDTO;
import com.ftn.sep_udd.model.Work;
import com.ftn.sep_udd.model.WorkES;
import com.ftn.sep_udd.repository.WorkESRepository;
import com.ftn.sep_udd.service.ESWorkService;
import com.ftn.sep_udd.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ESWorkServiceImpl implements ESWorkService {

    @Autowired
    private WorkESRepository workRepository;

    @Autowired
    private WorkService workService;

    @Override
    public WorkES findWorkByTitle(String title) {
        return this.workRepository.findWorkESByTitle(title);
    }

    //indeksiranje
    @Override
    public void saveWorkData(WorkDataDTO workDataDTO) {

        //mysql
        Work work = new Work(workDataDTO);
        Work savedWork = this.workService.saveWork(work);

        WorkES workES = new WorkES();
        workES.setTitle(workDataDTO.getTitle());
        workES.setJournalTitle(workDataDTO.getJournalTitle());
        workES.setAbstr(workDataDTO.getAbstr());
        workES.setKeyTerms(workDataDTO.getKeyTerms());
        workES.setPdf(workDataDTO.getFile());
        workES.setPdfName(workDataDTO.getFile());
        workES.setScientificField(workDataDTO.getScientificField());

        workES.setId(savedWork.getId());
        //elasticsearch za indeksiranje
        WorkES w = this.workRepository.save(workES);
    }

    @Override
    public WorkES findWorkById(Integer id) {
        return this.workRepository.findWorkESById(id);
    }
}
