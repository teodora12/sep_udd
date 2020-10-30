package com.ftn.sep_udd.dto;

import com.ftn.sep_udd.model.Magazine;

import java.util.ArrayList;
import java.util.List;

public class MagazineDTO {

    private Long id;
    private String title;
    private String wayOfPayment;
    private boolean openAccess;
    private List<ScientificFieldDTO> scientificFields;
    private boolean paid;

    public MagazineDTO() {
        this.scientificFields = new ArrayList<>();
    }

    public MagazineDTO(Magazine magazine){
        this.id = magazine.getId();
        this.title = magazine.getTitle();
        if(!magazine.getWayOfPayment().equals("")) {
            this.wayOfPayment = magazine.getWayOfPayment();
        }
        this.openAccess = magazine.isOpenAccess();
    }

    public MagazineDTO(Long id, String title) {
        this.id = id;
        this.title = title;
    }


    public MagazineDTO(Long id, String title, List<ScientificFieldDTO> scientificFields) {
        this.id = id;
        this.title = title;
        this.scientificFields = scientificFields;
    }

    public boolean isOpenAccess() {
        return openAccess;
    }

    public void setOpenAccess(boolean openAccess) {
        this.openAccess = openAccess;
    }

    public List<ScientificFieldDTO> getScientificFields() {
        return scientificFields;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public void setScientificFields(List<ScientificFieldDTO> scientificFields) {
        this.scientificFields = scientificFields;
    }

    public String getWayOfPayment() {
        return wayOfPayment;
    }

    public void setWayOfPayment(String wayOfPayment) {
        this.wayOfPayment = wayOfPayment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
