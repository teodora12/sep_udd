package com.ftn.sep_udd.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CombinedSearchDTO {

    private String journalTitle;
    private String title;
    private String authors;
    private String keyTerms;
    private String text;
    private String scientificFields;

    private boolean journalTitleChecked;
    private boolean titleChecked;
    private boolean authorsChecked;
    private boolean keyTermsChecked;
    private boolean textChecked;

    private boolean phraseJournalTitle;
    private boolean phraseTitle;
    private boolean phraseAuthors;
    private boolean phraseKeyTerms;
    private boolean phraseText;

    public CombinedSearchDTO(){}

}
