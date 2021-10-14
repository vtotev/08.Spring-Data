package com.example.springdataautomappingobjects_exc.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.regex.Pattern;

@Entity
@Table(name = "games")
public class Game extends BaseEntity {
    private String title;
    private String trailer;
    private String imageThumbnail;
    private Double size;
    private Double price;
    private String description;
    private LocalDate releaseDate;

    public Game() {
    }

    public Game(String title, String trailer, String imageThumbnail, Double size, Double price, String description, LocalDate releaseDate) {
        setTitle(title);
        setTrailer(trailer);
        setImageThumbnail(imageThumbnail);
        setSize(size);
        setPrice(price);
        setDescription(description);
        setReleaseDate(releaseDate);
    }

    @Column(name = "title", nullable = false, unique = true)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        String titlePattern = "^[A-Z]{1}[\\W\\w]{2,100}$";
        Pattern pattern = Pattern.compile(titlePattern);
        if (!pattern.matcher(title).find()) {
            throw new IllegalArgumentException("Title must start with upper case letter and be between 3 and 100 characters long.");
        }
        this.title = title;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer){
        if (trailer.length() != 11) {
            throw new IllegalArgumentException("Invalid youtube trailer URL");
        }
        this.trailer = trailer;
    }

    public String getImageThumbnail() {
        return imageThumbnail;
    }

    public void setImageThumbnail(String imageThumbnail) {
        String thumbnailPatt = "^(http|https)(:\\/\\/)[\\w\\W]+$";
        Pattern pattern = Pattern.compile(thumbnailPatt);
        if (!pattern.matcher(imageThumbnail).find()) {
            throw new IllegalArgumentException("Invalid URL address");
        }
        this.imageThumbnail = imageThumbnail;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size)
    {
        if (size < 0) {
            throw new IllegalArgumentException("Size can't be negative number");
        }
        this.size = size;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price can't be negative number");
        }
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

}
