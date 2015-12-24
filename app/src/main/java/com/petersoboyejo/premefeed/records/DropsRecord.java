package com.petersoboyejo.premefeed.records;

// image, images, id, title, style, link, description, price, availability

import org.json.JSONArray;

public class DropsRecord {
    private String image;
    private JSONArray images;
    private String id;
    private String title;
    private String style;
    private String link;
    private String description;
    private int price;
    public String availability;

    public DropsRecord(String image, JSONArray images, String id, String title, String style, String link, String description, int price, String availability) {
        this.image = image;
        this.images = images;
        this.id = id;
        this.title = title;
        this.style = style;
        this.link = link;
        this.description = description;
        this.price = price;
        this.availability = availability;

    }

    public String getImage() {
        return image;
    }

    public JSONArray getImages() {
        return images;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getStyle() {
        return style;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public String getAvailability() {
        return availability;
    }


}