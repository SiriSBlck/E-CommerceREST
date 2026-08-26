package com.example.demo.service;

import com.example.demo.repository.ProductRepository;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.model.Product;
import com.example.demo.model.Supplier;
import com.example.demo.repository.SupplierRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class SupplierService {

    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    
    public Supplier getSupplier(Long supplierId) {
        return (this.supplierRepository.findById(supplierId).orElseThrow(() -> new IllegalArgumentException("Supplier not found")));
    }

    public Supplier editSupplier(Supplier supplier) {
        Supplier sup = this.supplierRepository.save(supplier);
        if(Objects.isNull(sup)) {
            throw new IllegalArgumentException("Supplier not found");
        }
        return sup;
    }

    public Page<Product> getCatalogProducts(Long supplierId, Pageable pageable) {
        return this.productRepository.findAllBySupplierId(supplierId, pageable);
    }

    public Product addToCatalog(Long supplierId, Product product) {
        Product p = this.productRepository.save(product);
        Supplier supplier = this.supplierRepository.findById(supplierId).orElseThrow(() -> new IllegalArgumentException("Supplier not found"));
        supplier.getCatalog().add(p.getId());
        this.supplierRepository.save(supplier);
        return p;
    }

    public boolean deleteFromCatalog(Long supplierId, Long productId) {
        Supplier supplier = this.supplierRepository.findById(supplierId).orElseThrow(() -> new IllegalArgumentException("Supplier not found"));
        supplier.getCatalog().remove(productId);
        return (this.supplierRepository.save(supplier) != null);
    }
}
