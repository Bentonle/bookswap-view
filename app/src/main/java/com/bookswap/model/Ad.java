package com.bookswap.model;

import com.bookswap.model.user.User;

public class Ad {
    private Long id;

    private Double price;
    private String description;
    private Product product;
    private User user;

    public Ad() {}

    public Ad(Long id,Double price, String description, Product product, User user) {
        super();
        this.id = id;
        this.price = price;
        this.description = description;
        this.product = product;
        this.user = user;
    }

    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
