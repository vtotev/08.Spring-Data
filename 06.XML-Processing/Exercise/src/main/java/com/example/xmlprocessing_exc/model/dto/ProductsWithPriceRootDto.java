package com.example.xmlprocessing_exc.model.dto;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "sold-products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductsWithPriceRootDto {
    @XmlAttribute
    private int count;
    @XmlElement(name = "product")
    private List<ProductsWithPriceDto> products;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ProductsWithPriceDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsWithPriceDto> products) {
        this.products = products;
        this.setCount(this.products.size());
    }
}
