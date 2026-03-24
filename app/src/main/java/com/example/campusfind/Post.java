package com.example.campusfind;

public class Post {
    private int id;
    private String type;
    private String itemName;
    private String category;
    private String location;
    private String date;
    private String description;
    private String contact;

    public Post(int id, String type, String itemName, String category, String location,
                String date, String description, String contact) {
        this.id = id;
        this.type = type;
        this.itemName = itemName;
        this.category = category;
        this.location = location;
        this.date = date;
        this.description = description;
        this.contact = contact;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getItemName() {
        return itemName;
    }

    public String getCategory() {
        return category;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getContact() {
        return contact;
    }
}