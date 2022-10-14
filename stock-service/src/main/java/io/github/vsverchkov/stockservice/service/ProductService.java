package io.github.vsverchkov.stockservice.service;

import io.github.vsverchkov.stockservice.model.dto.ProductDto;
import io.github.vsverchkov.stockservice.model.dto.ReservedDto;
import java.util.UUID;

public interface ProductService {

    ProductDto getByCatalogId(UUID catalogId);

    ReservedDto reservation(UUID catalogId, Integer quantity);

    

}
