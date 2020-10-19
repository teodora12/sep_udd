package com.ftn.sep_udd.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkDataDTO {

    private String title;
    private String journalTitle;
    private String abstr;
    private String scientificField;
    private String file;
    private String fileName;
    private String keyTerms;
    private String authors;

    public WorkDataDTO() {
    }

}
