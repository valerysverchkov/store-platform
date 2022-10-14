package io.github.vsverchkov.stockservice.repository;

import io.github.vsverchkov.stockservice.model.entity.ProductEntity;
import io.github.vsverchkov.stockservice.model.type.ProductStatus;
import java.util.UUID;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    ProductEntity findByCatalogId(UUID catalogId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM ProductEntity p WHERE p.catalogId = ?1 AND p.status = ?2")
    ProductEntity findByCatalogIdWithLock(UUID catalogId, ProductStatus status);

}
