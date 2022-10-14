package io.github.vsverchkov.stockservice.mapper;

import io.github.vsverchkov.stockservice.model.dto.ProductDto;
import io.github.vsverchkov.stockservice.model.entity.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductEntity toEntity(ProductDto dto);

    ProductDto toDto(ProductEntity productEntity);

}
