package com.uol.compasso.productms.controller;

import com.uol.compasso.productms.dto.ProductRequestDTO;
import com.uol.compasso.productms.dto.ProductResponseDTO;
import com.uol.compasso.productms.exception.ParamsInvalidException;
import com.uol.compasso.productms.exception.ProductNotFoundException;
import com.uol.compasso.productms.model.ProductSearchParam;
import com.uol.compasso.productms.service.ProductService;
import com.uol.compasso.productms.util.GeneralMessages;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;


@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @Captor
    ArgumentCaptor<ProductSearchParam> paramArgumentCaptor;

    @Test
    void listAllProducts() {
        ArrayList<ProductResponseDTO> productDTOS = new ArrayList<>();
        productDTOS.add(new ProductResponseDTO());
        Mockito.when(this.productService.getAll()).thenReturn(productDTOS);
        ResponseEntity<List<ProductResponseDTO>> response = this.productController.listAllProducts(0L);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(response.getBody().size(), productDTOS.size());
    }

    @Test
    void getOne() throws ProductNotFoundException {
        Long id = 1L;
        Mockito.when(this.productService.getOne(id)).thenReturn(new ProductResponseDTO());
        ResponseEntity<ProductResponseDTO> response = this.productController.getOne(id);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void searchProducts() throws ParamsInvalidException {
        ArrayList<ProductResponseDTO> productDTOS = new ArrayList<>();
        productDTOS.add(new ProductResponseDTO());
        Mockito.when(this.productService.searchItens(paramArgumentCaptor.capture())).thenReturn(productDTOS);
        ResponseEntity<List<ProductResponseDTO>> response = this.productController.searchProducts("oi", 1D, 2D, null);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(response.getBody().size(), productDTOS.size());
    }

    @Test
    void createProduct() {
        ProductRequestDTO productDTO = new ProductRequestDTO();
        productDTO.setName("teste");
        productDTO.setDescription("desc");
        productDTO.setPrice(25D);


        Mockito.when(this.productService.insertProduct(productDTO)).thenReturn(new ProductResponseDTO());

        ResponseEntity<ProductResponseDTO> response = this.productController.createProduct(productDTO);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    void deleteProduct() throws ProductNotFoundException {
        Long id = 1L;
        Mockito.when(this.productService.deleteProduct(id)).thenReturn(GeneralMessages.SUCCESS_TO_DELETE.get());
        ResponseEntity<String> response = this.productController.deleteProduct(id);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(response.getBody(), GeneralMessages.SUCCESS_TO_DELETE.get());
    }

    @Test
    void updateProduct() throws ProductNotFoundException {
        Long id = 1L;
        ProductRequestDTO productDTO = new ProductRequestDTO();
        productDTO.setName("teste");
        productDTO.setDescription("desc");
        productDTO.setPrice(25D);
        Mockito.when(this.productService.updateProduct(id, productDTO)).thenReturn(new ProductResponseDTO());
        ResponseEntity<ProductResponseDTO> response = this.productController.updateProduct(id, productDTO);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
}