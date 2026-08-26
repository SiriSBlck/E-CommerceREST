package com.example.demo.controller;

import com.example.demo.service.SupplierService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Product;
import com.example.demo.model.Supplier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;



@AllArgsConstructor
@Controller
public final class SupplierController {

    private final SupplierService supplierService;
    
    @GetMapping("/supplier/{supplier-id}")
    public ResponseEntity<Supplier> getSupplier(@RequestParam Long supplierId) {
        return ResponseEntity.ok(this.supplierService.getSupplier(supplierId));
    }

    @PutMapping("/supplier/{supplier-id}")
    public ResponseEntity<Supplier> editSupplier(@RequestParam Supplier supplier) {
        return ResponseEntity.ok(this.supplierService.editSupplier(supplier));
    }
    
    @GetMapping("/supplier/{supplier-id}/catalog")
    public ResponseEntity<Page<Product>> getCatalog(@RequestParam Long supplierId, @PageableDefault(size=20, sort="name") Pageable pageable) {
        return ResponseEntity.ok(this.supplierService.getCatalogProducts(supplierId, pageable));
    }
    
    @PutMapping("/supplier/{supplier-id}/catalog")
    public ResponseEntity<Product> addCatalogProduct(@RequestParam Long supplierId, @RequestParam Product product) {        
        return ResponseEntity.ok(this.supplierService.addToCatalog(supplierId, product));
    }

    @PutMapping("/supplier/{supplier-id}/catalog")
    public ResponseEntity<Product> deleteCatalogProduct(@RequestParam Long supplierId, @RequestParam Product product) {        
        return ResponseEntity.ok(this.supplierService.deleteFromCatalog(supplierId, product));
    }
}
