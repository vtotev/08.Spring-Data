package com.example.xmlprocessing_exc.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductViewRootDto {
    @XmlElement(name = "product")
    private List<ProductWithSellerDto> product;

    public List<ProductWithSellerDto> getProduct() {
        return product;
    }

    public void setProduct(List<ProductWithSellerDto> product) {
        this.product = product;
    }
}
