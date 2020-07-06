package com.ftn.sep_udd.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class WorkEsDTO {

    private String title;
    private String journalTitle;
    private String abstr;
    private String scientificField;
    private String pdf;
    private String text;
    private String keyTerms;

    public WorkEsDTO() {
    }
}
