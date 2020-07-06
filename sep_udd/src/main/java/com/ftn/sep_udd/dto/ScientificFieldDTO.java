package com.ftn.sep_udd.dto;


import com.ftn.sep_udd.model.ScientificField;

public class ScientificFieldDTO {

    private Long id;
    private String name;

    public ScientificFieldDTO(){}

    public ScientificFieldDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public ScientificFieldDTO(ScientificField scientificField){
        this.id = scientificField.getId();
        this.name = scientificField.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
