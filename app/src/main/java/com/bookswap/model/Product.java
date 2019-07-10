package com.bookswap.model;

public class Product {
    private Long id;

    private String title;
    private String isbn;
    private String author;
    private String edition;
    private String publisher;
    private String description;
    private Ad ad;

    public Product() {}

    public Product(Long id, String title, String isbn, String author, String edition, String publisher,
                   String description, Ad ad) {
        super();
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.author = author;
        this.edition = edition;
        this.publisher = publisher;
        this.description = description;
        this.ad = ad;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getEdition() {
        return edition;
    }
    public void setEdition(String edition) {
        this.edition = edition;
    }
    public String getPublisher() {
        return publisher;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Ad getAd() {
        return ad;
    }
    public void setAd(Ad ad) {
        this.ad = ad;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
