package io.github.vsverchkov.stockservice.service;

import io.github.vsverchkov.stockservice.mapper.ProductMapper;
import io.github.vsverchkov.stockservice.model.dto.ProductDto;
import io.github.vsverchkov.stockservice.model.dto.ReservedDto;
import io.github.vsverchkov.stockservice.model.entity.ProductEntity;
import io.github.vsverchkov.stockservice.model.exception.RuntimeLogicException;
import io.github.vsverchkov.stockservice.model.type.ProductStatus;
import io.github.vsverchkov.stockservice.model.type.ReservedStatus;
import io.github.vsverchkov.stockservice.repository.ProductRepository;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDto getByCatalogId(UUID catalogId) {
        if (catalogId == null) {
            throw new RuntimeLogicException("Catalog id must be not null");
        }
        final var product = productRepository.findByCatalogId(catalogId);
        return productMapper.toDto(product);
    }

    @Override
    @Transactional
    public ReservedDto reservation(UUID catalogId, Integer quantity) {
        if (catalogId == null || quantity == null) {
            throw new RuntimeLogicException("Catalog id and quantity must be not null");
        }

        final var product = productRepository.findByCatalogIdWithLock(catalogId, ProductStatus.AVAILABLE);
        if (product == null) {
            log.warn("Product with catalog id {} is not available or not exist", catalogId);
            return ReservedDto.builder()
                    .catalogId(catalogId)
                    .status(ReservedStatus.NOT_RESERVED)
                    .quantity(0)
                    .build();
        }

        final var isExistProductQnt = product.getQuantity() - quantity >= 0;
        if (!isExistProductQnt) {
            product.setQuantity(0);
            product.setStatus(ProductStatus.OUT_OF_STOCK);
        }

        productRepository.save(product);

        return ReservedDto.builder()
                .catalogId(catalogId)
                .status(isExistProductQnt ? ReservedStatus.RESERVED : ReservedStatus.PART_RESERVED)
                .quantity(isExistProductQnt ? quantity : product.getQuantity())
                .build();
    }
}
