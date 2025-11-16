package com.s2r.smarts2r.repository;

import com.s2r.smarts2r.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findBySupplierIdAndActiveTrue(Long supplierId, Pageable pageable);
    Page<Product> findByCategoryIdAndActiveTrue(Long categoryId, Pageable pageable);
    Page<Product> findBySupplierIdAndCategoryIdAndActiveTrue(Long supplierId, Long categoryId, Pageable pageable);
    Page<Product> findByNameContainingIgnoreCaseAndActiveTrue(String name, Pageable pageable);
    Page<Product> findByNameContainingIgnoreCaseAndCategoryIdAndActiveTrue(String name, Long categoryId, Pageable pageable);
    List<Product> findBySupplierIdAndActiveTrue(Long supplierId);
}
