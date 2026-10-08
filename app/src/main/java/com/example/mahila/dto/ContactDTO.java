package com.example.mahila.dto;

public class ContactDTO {
    private Long id;
    private String contactName;
    private String phoneNumber;
    private String category;
    private Boolean isFavorite;

    public ContactDTO() {}

    public ContactDTO(String contactName, String phoneNumber, String category, Boolean isFavorite) {
        this.contactName = contactName;
        this.phoneNumber = phoneNumber;
        this.category = category;
        this.isFavorite = isFavorite;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getContactName() { return contactName; }
    public void setContactName(String contactName) { this.contactName = contactName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Boolean getIsFavorite() { return isFavorite; }
    public void setIsFavorite(Boolean isFavorite) { this.isFavorite = isFavorite; }
}
