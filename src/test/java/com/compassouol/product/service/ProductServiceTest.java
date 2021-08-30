package com.compassouol.product.service;

import com.compassouol.product.dto.ProductDto;
import com.compassouol.product.entity.Product;
import com.compassouol.product.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductService productService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        this.productService = new ProductService(productRepository);
    }

    @Test
    public void getAll() {

        productService.getAll();

        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void searchProductById() {

        when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(new Product()));

        productService.searchProductById(1L);

        verify(productRepository, times(1)).findById(1L);

    }

    @Test(expected = ResponseStatusException.class)
    public void searchProductByIdWithResponseStatusException() {

        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        productService.searchProductById(1L);

    }

    @Test
    public void createProduct() {

        productService.createProduct(productDtoMock());

        verify(productRepository, times(1)).save(productMock());
    }

    @Test
    public void updateProduct() {

        when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(productMock()));

        productService.updateProduct(productDtoMock(), 1L);

        verify(productRepository, times(1)).save(Mockito.any(Product.class));
    }

    @Test(expected = ResponseStatusException.class)
    public void updateProductByIdWithResponseStatusException() {

        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        productService.updateProduct(productDtoMock(), 1L);

    }

    @Test
    public void deleteProduct() {

        when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(productMock()));

        productService.deleteProduct(1L);

        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    public void deleteProductByIdWithResponseStatusException() {

        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Product> product = productService.deleteProduct(1L);

        assertEquals(product.getStatusCodeValue(), HttpStatus.NOT_FOUND.value());

    }

    @Test
    public void searchProducts() {

        Product product = productMock();

        List<Product> products = new ArrayList<>();
        products.add(product);

        when(productRepository.searchProduct(10.0, 15.0, "teste")).thenReturn(products);

        productService.searchProducts(10.0, 15.0, "teste");
    }

    private ProductDto productDtoMock() {
        ProductDto productDto = new ProductDto();

        productDto.setDescription("description");
        productDto.setPrice(2.0);
        productDto.setName("name");
        productDto.setId(1L);

        return productDto;
    }

    private Product productMock() {

        ProductDto productDto = productDtoMock();

        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());

        return product;
    }
}