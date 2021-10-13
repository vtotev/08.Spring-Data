package com.example.advqueryingex.models.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public class ReducedBook {

    private String title;
    private EditionType editionType;
    private AgeRestriction ageRestriction;
    private BigDecimal price;

    public ReducedBook(String title, EditionType editionType, AgeRestriction ageRestriction, BigDecimal price) {
        this.title = title;
        this.editionType = editionType;
        this.ageRestriction = ageRestriction;
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %.2f",
                this.title, this.editionType, this.ageRestriction, this.price);
    }
}
