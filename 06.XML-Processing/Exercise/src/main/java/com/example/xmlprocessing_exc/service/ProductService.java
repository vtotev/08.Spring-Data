package com.example.xmlprocessing_exc.service;

import com.example.xmlprocessing_exc.model.dto.ProductSeedDto;
import com.example.xmlprocessing_exc.model.dto.ProductViewRootDto;

import java.util.List;

public interface ProductService {
    long getCount();

    void seedProducts(List<ProductSeedDto> products);

    ProductViewRootDto findProductsInRangeWithoutBuyer();
}
