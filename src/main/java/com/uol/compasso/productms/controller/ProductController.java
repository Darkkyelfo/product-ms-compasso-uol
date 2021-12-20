package com.uol.compasso.productms.controller;

import com.uol.compasso.productms.dto.ProductRequestDTO;
import com.uol.compasso.productms.dto.ProductResponseDTO;
import com.uol.compasso.productms.exception.ParamsInvalidException;
import com.uol.compasso.productms.exception.ProductNotFoundException;
import com.uol.compasso.productms.model.ProductSearchParam;
import com.uol.compasso.productms.service.ProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "products")
@Repository
public class ProductController {

    @Autowired()
    private ProductService productService;

    @ApiOperation(value = "Recupera todos os produtos cadastrados",
            notes = "Este recurso lista todos os produtos cadastros,podendo realizar paginação ou não",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Requisição com parâmetros inválidos"),
            @ApiResponse(code = 500, message = "O servidor encontrou um erro não previsto")
    })
    @GetMapping()
    public ResponseEntity<List<ProductResponseDTO>> listAllProducts(@RequestParam(required = false) Long page) {
        if (page != null && page > 0) {
            return ResponseEntity.ok(productService.getAll(page.intValue(), 5));
        }
        return ResponseEntity.ok(productService.getAll());
    }

    @ApiOperation(value = "Recupera um produto por ID", notes = "Este recurso busca e retorna um product por ID", response = ProductResponseDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Requisição com parâmetros inválidos"),
            @ApiResponse(code = 404, message = "Not found - Product de ID não foi encontrado"),
            @ApiResponse(code = 500, message = "O servidor encontrou um erro não previsto")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getOne(@PathVariable Long id) throws ProductNotFoundException {
        return ResponseEntity.ok(productService.getOne(id));
    }

    @ApiOperation(value = "Busca varios produtos com base nos parâmetros passados",
            notes = "Este recurso lista os produtos seguindo os critérios informados.Suporta paginação",
            response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Requisição com parâmetros inválidos"),
            @ApiResponse(code = 500, message = "O servidor encontrou um erro não previsto")
    })
    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDTO>> searchProducts(@RequestParam(name = "q", required = false) String searchText,
                                                                   @RequestParam(name = "min_price", required = false) Double minPrice,
                                                                   @RequestParam(name = "max_price", required = false) Double maxPrice,
                                                                   @RequestParam(name = "page", required = false) Long page
    ) throws ParamsInvalidException {
        ProductSearchParam params = new ProductSearchParam();
        params.setSearchText(searchText);
        params.setMaxPrice(maxPrice);
        params.setMinPrice(minPrice);
        params.setPageNum(page);
        return ResponseEntity.ok(productService.searchItens(params));
    }


    @ApiOperation(value = "Criação de um produto", notes = "Este recurso adiciona um product", response = ProductResponseDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Product cadastrado"),
            @ApiResponse(code = 400, message = "Requisição com parâmetros inválidos"),
            @ApiResponse(code = 500, message = "O servidor encontrou um erro não previsto")
    })
    @PostMapping()
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductRequestDTO productDTO) {
        return ResponseEntity.status(201).body(this.productService.insertProduct(productDTO));
    }

    @ApiOperation(value = "Deleta um produto por ID", notes = "Este recurso remove um product por ID", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not found - Product de ID não foi encontrado"),
            @ApiResponse(code = 500, message = "O servidor encontrou um erro não previsto")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) throws ProductNotFoundException {
        return ResponseEntity.ok(this.productService.deleteProduct(id));
    }

    @ApiOperation(value = "Atualiza um produto por ID", notes = "Este recurso atualiza as informações de um product por ID", response = ProductResponseDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not found - Product de ID não foi encontrado"),
            @ApiResponse(code = 500, message = "O servidor encontrou um erro não previsto")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequestDTO productDTO) throws ProductNotFoundException {
        return ResponseEntity.ok(this.productService.updateProduct(id, productDTO));
    }
}
