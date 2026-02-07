package com.example.candystore.domain.repository;

import com.example.candystore.application.dto.ProducTODetailDTO;
import com.example.candystore.application.interfaces.ProducTODetailProjection;
import com.example.candystore.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query(value = "CALL sp_get_products()", nativeQuery = true)
    List<Product> getProducts();

    boolean existsByName(String name);

    @Query(value = "CALL sp_get_products_by_ids(:idsJson)", nativeQuery = true)
    List<ProducTODetailProjection> getProductsByIds(@Param("idsJson") String idsJson);
}
