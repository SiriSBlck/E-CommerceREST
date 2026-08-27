package com.example.demo.controller;

import com.example.demo.service.SupplierService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Product;
import com.example.demo.model.Supplier;


@AllArgsConstructor
@Controller
public final class SupplierController {

    private final SupplierService supplierService;
    
    @GetMapping("/supplier/{supplierId}")
    public ResponseEntity<Supplier> getSupplier(@PathVariable Long supplierId) {
        return ResponseEntity.ok(this.supplierService.getSupplier(supplierId));
    }

    @PutMapping("/supplier/{supplierId}")
    public ResponseEntity<Supplier> editSupplier(@RequestParam Supplier supplier) {
        return ResponseEntity.ok(this.supplierService.editSupplier(supplier));
    }
    
    @GetMapping("/supplier/{supplierId}/catalog")
    public ResponseEntity<Page<Product>> getCatalog(@PathVariable Long supplierId, @PageableDefault(size=20, sort="name") Pageable pageable) {
        return ResponseEntity.ok(this.supplierService.getCatalogProducts(supplierId, pageable));
    }
    
    @PostMapping("/supplier/{supplierId}/catalog")
    public ResponseEntity<Product> addCatalogProduct(@PathVariable Long supplierId, @RequestParam Product product) {        
        return ResponseEntity.ok(this.supplierService.addToCatalog(supplierId, product));
    }

    @DeleteMapping("/supplier/{supplierId}/catalog/{productId}")
    public ResponseEntity<HttpStatusCode> deleteCatalogProduct(@PathVariable Long supplierId, @PathVariable Long productId) {  
        return (this.supplierService.deleteFromCatalog(supplierId, productId) ? new ResponseEntity<>(HttpStatusCode.valueOf(200)) : new ResponseEntity<>(HttpStatusCode.valueOf(412)));
    }
}
