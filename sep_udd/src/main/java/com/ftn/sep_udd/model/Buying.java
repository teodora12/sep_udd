package com.ftn.sep_udd.model;

import javax.persistence.*;

@Table
@Entity(name = "BUYING")
public class Buying {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long productId;

    @Column
    private String status;

//    @Column
//    private String typeOfProduct;

    public Buying() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//    public String getTypeOfProduct() {
//        return typeOfProduct;
//    }
//
//    public void setTypeOfProduct(String typeOfProduct) {
//        this.typeOfProduct = typeOfProduct;
//    }
}
