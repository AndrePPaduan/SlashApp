package com.slash.slash.controllers;

import com.slash.slash.exceptions.CompanyDoesNotExist;
import com.slash.slash.exceptions.ProducDoesNotExist;
import com.slash.slash.exceptions.ProductAlreadyExists;
import com.slash.slash.exceptions.ProductHasNoName;
import com.slash.slash.models.Product;
import com.slash.slash.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(String companyName, Product product, MultipartFile file) throws ProductAlreadyExists, ProductHasNoName, CompanyDoesNotExist {
        Product createdProduct = productService.addProduct(product, companyName, file);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @DeleteMapping("/product")
    public ResponseEntity<?> deleteProduct(String productName) throws ProducDoesNotExist {
        productService.deleteProduct(productName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/product")
    public ResponseEntity<?> editProduct(String productName, Product newProduct) throws ProducDoesNotExist, ProductAlreadyExists {
        productService.editProduct(productName, newProduct);
        return new ResponseEntity<>(newProduct, HttpStatus.OK);
    }

    @GetMapping("/product")
    public ResponseEntity<?> listProducts() {
        List<Product> productList = productService.listProducts();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/product/type")
    public ResponseEntity<?> listProductByType(String type) {
        List<Product> productList = productService.listProductByType(type);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/product/city")
    public ResponseEntity<?> listProductByCity(String city) {
        List<Product> productList = productService.listProductByCity(city);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/product/image")
    public ResponseEntity<?> retrieveProductImageByName(String name, HttpServletRequest request) throws ProducDoesNotExist {

        Resource resource = productService.retrieveProductImage(name);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/product/name")
    public ResponseEntity<?> retrieveProductByName(String name) throws ProducDoesNotExist {

        Product product = productService.retrieveProductByName(name);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}

