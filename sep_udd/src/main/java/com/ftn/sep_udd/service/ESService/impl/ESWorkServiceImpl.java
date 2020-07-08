package com.ftn.sep_udd.service.ESService.impl;

import com.ftn.sep_udd.dto.WorkDataDTO;
import com.ftn.sep_udd.model.Work;
import com.ftn.sep_udd.model.WorkES;
import com.ftn.sep_udd.repository.WorkESRepository;
import com.ftn.sep_udd.service.ESWorkService;
import com.ftn.sep_udd.service.WorkService;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

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
    public void saveWorkData(WorkDataDTO workDataDTO) throws UnsupportedEncodingException {

        this.workService.savePdf(workDataDTO);


        //mysql
        Work work = new Work(workDataDTO);
        Work savedWork = this.workService.saveWork(work);

        WorkES workES = new WorkES();
        workES.setTitle(workDataDTO.getTitle());
        workES.setJournalTitle(workDataDTO.getJournalTitle());
        workES.setAbstr(workDataDTO.getAbstr());
        workES.setKeyTerms(workDataDTO.getKeyTerms());
        workES.setPdfLocation("src/main/pdf/" + savedWork.getPdf());
        String text = parsePDF(work);
        workES.setText(text);
        workES.setScientificField(workDataDTO.getScientificField());
        workES.setAuthors(workDataDTO.getAuthors());

        workES.setId(savedWork.getId());

        WorkES w = this.workRepository.save(workES);
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAYYYYYYYYYYYYYYYYYYYYYYYYYYYYya");
    }

    @Override
    public WorkES findWorkById(Integer id) {
        return this.workRepository.findWorkESById(id);
    }

    @Override
    public String parsePDF(Work w) throws UnsupportedEncodingException {
        Work work = workService.findWorkById(w.getId());
        String workPdfName = work.getPdf();

        File pdf = new File("src/main/pdf/" + workPdfName);
        String text = null;
        try {
            System.out.println("*******************************************");
            System.out.println("Parsiranje PDF-a");
            System.out.println("*******************************************");
            PDFParser parser = new PDFParser(new RandomAccessFile(pdf, "r"));
            parser.parse();
            text = getText(parser);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    public String getText(PDFParser parser) {
        try {
            PDFTextStripper textStripper = new PDFTextStripper();
            String text = textStripper.getText(parser.getPDDocument());
            return text;
        } catch (IOException e) {
            System.out.println("Greksa pri konvertovanju dokumenta u pdf");
        }
        return null;
    }
}
