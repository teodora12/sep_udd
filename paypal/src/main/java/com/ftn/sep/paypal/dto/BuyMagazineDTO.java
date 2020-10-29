package com.ftn.sep.paypal.dto;

public class BuyMagazineDTO {

    private Long productId;
    private String productName;
    private String userEmail;
    private double price;
    private Long buyingId;

    public BuyMagazineDTO() {
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getBuyingId() {
        return buyingId;
    }

    public void setBuyingId(Long buyingId) {
        this.buyingId = buyingId;
    }
}
