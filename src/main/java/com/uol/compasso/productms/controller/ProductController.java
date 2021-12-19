package com.uol.compasso.productms.controller;

import com.uol.compasso.productms.dto.ProductDTO;
import com.uol.compasso.productms.exception.ParamsInvalidException;
import com.uol.compasso.productms.exception.ProductNotFoundException;
import com.uol.compasso.productms.model.ProductSearchParam;
import com.uol.compasso.productms.service.ProductService;
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

    @GetMapping()
    public ResponseEntity<List<ProductDTO>> listAllProducts() {
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getOne(@PathVariable Long id) throws ProductNotFoundException {
        return ResponseEntity.ok(productService.getOne(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> searchProducts(@RequestParam(name = "q", required = false) String searchText,
                                                           @RequestParam(name = "min_price", required = false) Double minPrice,
                                                           @RequestParam(name = "max_price", required = false) Double maxPrice
    ) throws ParamsInvalidException {
        ProductSearchParam params = new ProductSearchParam();
        params.setSearchText(searchText);
        params.setMaxPrice(maxPrice);
        params.setMinPrice(minPrice);
        return ResponseEntity.ok(productService.searchItens(params));
    }

    @PostMapping()
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        return ResponseEntity.status(201).body(this.productService.insertProduct(productDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) throws ProductNotFoundException {
        return ResponseEntity.ok(this.productService.deleteProduct(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id,@Valid @RequestBody ProductDTO productDTO) throws ProductNotFoundException {
        return ResponseEntity.ok(this.productService.updateProduct(id, productDTO));
    }
}
