package io.github.vsverchkov.stockservice.model.dto;

import io.github.vsverchkov.stockservice.model.type.ProductStatus;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductDto {

    private final Long id;

    private final UUID catalogId;

    private final Integer quantity;

    private final ProductStatus status;

}
