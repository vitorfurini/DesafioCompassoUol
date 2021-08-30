package com.compassouol.product.controller;

import com.compassouol.product.dto.ProductDto;
import com.compassouol.product.entity.Product;
import com.compassouol.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAll() {
        List<ProductDto> productDtos = productService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(productDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> searchProductById(@PathVariable Long id) {
        ProductDto productDto = productService.searchProductById(id);

        return ResponseEntity.status(HttpStatus.OK).body(productDto);
    }

    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam(required = false) Double min_price,
            @RequestParam(required = false) Double max_price,
            @RequestParam(required = false) String nameOrDesc) {
        return productService.searchProducts(min_price, max_price, nameOrDesc);
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody @Valid ProductDto dto) {
        ProductDto result = productService.createProduct(dto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@RequestBody @Valid ProductDto dto, @PathVariable Long id) {
        return ResponseEntity.ok(productService.updateProduct(dto, id));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Product> deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }
}
