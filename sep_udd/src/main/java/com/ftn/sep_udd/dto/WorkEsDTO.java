package com.ftn.sep_udd.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkEsDTO {

    private Integer id;
    private String title;
    private String journalTitle;
    private String abstr;
    private String keyTerms;
    private String scientificField;
    private String authors;
    private String highlight;
//    private boolean openAccess;

    public WorkEsDTO() {
    }
}
