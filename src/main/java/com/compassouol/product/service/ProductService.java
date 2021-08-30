package com.compassouol.product.service;

import com.compassouol.product.dto.ProductDto;
import com.compassouol.product.entity.Product;
import com.compassouol.product.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private static final String NOT_FOUND_MESSAGE = "PRODUTO NÃO ENCONTRADO COM O ID FORNECIDO";
    private static final String FOUND_SIMILAR_PRODUCT = "JÁ HÁ REGISTRADO UM PRODUTO COM NOME E DESCRIÇÃO IGUAIS AOS "
            + "INFORMADOS";
    private final ProductRepository productRepository;

    @Autowired
    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDto> getAll() {

        var products = productRepository.findAll();

        return products.stream().map(p -> new ProductDto(p.getId(), p.getName(), p.getDescription(), p.getPrice()))
                .collect(Collectors.toList());
    }

    public ProductDto searchProductById(Long id) {

        var product = productRepository.findById(id);

        if (product.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE);
        } else {
            return modelMapper.map(product.get(), ProductDto.class);
        }
    }

    @Transactional
    public ProductDto createProduct(ProductDto dto) {

        boolean existsByNameOrDescription = productRepository.existsAllByDescriptionOrName(dto.getName(),
                dto.getDescription());

        if (existsByNameOrDescription) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, FOUND_SIMILAR_PRODUCT);
        }
        var productSave = new Product(dto.getName(), dto.getDescription(), dto.getPrice());
        productRepository.save(productSave);
        return modelMapper.map(productSave, (Type) ProductDto.class);
    }

    @Transactional
    public Object updateProduct(ProductDto dto, Long id) {
        var updateDto = productRepository.findById(id);
        if (updateDto.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE);
        } else {
            Product productSave = updateDto.get();
            updateDto.orElseThrow().setId(id);
            updateDto.orElseThrow().setDescription(dto.getDescription());
            updateDto.orElseThrow().setName(dto.getName());
            updateDto.orElseThrow().setPrice(dto.getPrice());
            productRepository.save(productSave);
        }
        return modelMapper.map(updateDto.get(), ProductDto.class);
    }

    public ResponseEntity<Product> deleteProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            productRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else
            return ResponseEntity.notFound().build();
    }

    public List<Product> searchProducts(Double min_price, Double max_price, String nameOrDesc) {
        return productRepository.searchProduct(min_price, max_price, nameOrDesc);
    }
}
