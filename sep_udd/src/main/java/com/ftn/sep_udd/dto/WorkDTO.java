package com.ftn.sep_udd.dto;

public class WorkDTO {

    private Integer id;
    private String title;
    private String abstr;
    private String scientificField;
    private boolean magazineOpenAccess;
    private boolean paid;

    public WorkDTO() {
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isMagazineOpenAccess() {
        return magazineOpenAccess;
    }

    public void setMagazineOpenAccess(boolean magazineOpenAccess) {
        this.magazineOpenAccess = magazineOpenAccess;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbstr() {
        return abstr;
    }

    public void setAbstr(String abstr) {
        this.abstr = abstr;
    }

    public String getScientificField() {
        return scientificField;
    }

    public void setScientificField(String scientificField) {
        this.scientificField = scientificField;
    }
}
