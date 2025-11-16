package com.s2r.smarts2r.service;

import com.s2r.smarts2r.dto.ProductDTO;
import com.s2r.smarts2r.model.Category;
import com.s2r.smarts2r.model.Product;
import com.s2r.smarts2r.model.User;
import com.s2r.smarts2r.repository.CategoryRepository;
import com.s2r.smarts2r.repository.ProductRepository;
import com.s2r.smarts2r.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public Page<ProductDTO> getProductsByCategory(Long categoryId, Pageable pageable) {
        Page<Product> products = productRepository.findByCategoryIdAndActiveTrue(categoryId, pageable);
        return products.map(this::mapToDTO);
    }

    public Page<ProductDTO> getProductsBySupplier(Long supplierId, Pageable pageable) {
        Page<Product> products = productRepository.findBySupplierIdAndActiveTrue(supplierId, pageable);
        return products.map(this::mapToDTO);
    }

    public Page<ProductDTO> getProductsBySupplierAndCategory(Long supplierId, Long categoryId, Pageable pageable) {
        Page<Product> products = productRepository.findBySupplierIdAndCategoryIdAndActiveTrue(supplierId, categoryId, pageable);
        return products.map(this::mapToDTO);
    }

    public Page<ProductDTO> searchProducts(String query, Pageable pageable) {
        Page<Product> products = productRepository.findByNameContainingIgnoreCaseAndActiveTrue(query, pageable);
        return products.map(this::mapToDTO);
    }

    public Page<ProductDTO> searchProductsByCategory(String query, Long categoryId, Pageable pageable) {
        Page<Product> products = productRepository.findByNameContainingIgnoreCaseAndCategoryIdAndActiveTrue(query, categoryId, pageable);
        return products.map(this::mapToDTO);
    }

    public ProductDTO getProductById(Long id) {
        return productRepository.findById(id)
            .map(this::mapToDTO)
            .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public ProductDTO createProduct(Long supplierId, ProductDTO productDTO) {
        User supplier = userRepository.findById(supplierId)
            .orElseThrow(() -> new RuntimeException("Supplier not found"));

        Category category = categoryRepository.findById(productDTO.getCategoryId())
            .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = Product.builder()
            .supplier(supplier)
            .category(category)
            .name(productDTO.getName())
            .description(productDTO.getDescription())
            .price(productDTO.getPrice())
            .quantity(productDTO.getQuantity())
            .sku(productDTO.getSku())
            .imageUrl(productDTO.getImageUrl())
            .active(true)
            .build();

        Product savedProduct = productRepository.save(product);
        return mapToDTO(savedProduct);
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found"));

        if (productDTO.getName() != null) {
            product.setName(productDTO.getName());
        }
        if (productDTO.getDescription() != null) {
            product.setDescription(productDTO.getDescription());
        }
        if (productDTO.getPrice() != null) {
            product.setPrice(productDTO.getPrice());
        }
        if (productDTO.getQuantity() != null) {
            product.setQuantity(productDTO.getQuantity());
        }
        if (productDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
            product.setCategory(category);
        }
        if (productDTO.getImageUrl() != null) {
            product.setImageUrl(productDTO.getImageUrl());
        }
        if (productDTO.getActive() != null) {
            product.setActive(productDTO.getActive());
        }
        product.setUpdatedAt(OffsetDateTime.now());

        Product updatedProduct = productRepository.save(product);
        return mapToDTO(updatedProduct);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setActive(false);
        product.setUpdatedAt(OffsetDateTime.now());
        productRepository.save(product);
    }

    private ProductDTO mapToDTO(Product product) {
        return ProductDTO.builder()
            .id(product.getId())
            .supplierId(product.getSupplier().getId())
            .categoryId(product.getCategory().getId())
            .categoryName(product.getCategory().getName())
            .name(product.getName())
            .description(product.getDescription())
            .price(product.getPrice())
            .quantity(product.getQuantity())
            .sku(product.getSku())
            .imageUrl(product.getImageUrl())
            .active(product.getActive())
            .createdAt(product.getCreatedAt())
            .updatedAt(product.getUpdatedAt())
            .build();
    }
}
