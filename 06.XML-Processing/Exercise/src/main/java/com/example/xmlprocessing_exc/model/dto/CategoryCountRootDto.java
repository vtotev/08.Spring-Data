package com.example.xmlprocessing_exc.model.dto;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryCountRootDto {
    @XmlElement(name = "category")
    List<CategoryCountDto> categories;

    public List<CategoryCountDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryCountDto> categories) {
        this.categories = categories;
    }
}
