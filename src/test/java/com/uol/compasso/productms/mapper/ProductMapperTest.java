package com.uol.compasso.productms.mapper;

import com.uol.compasso.productms.UtilForTest;
import com.uol.compasso.productms.dto.ProductDTO;
import com.uol.compasso.productms.mapper.ProductMapper;
import com.uol.compasso.productms.model.entity.Product;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ProductMapperTest {

    private List<Product> productList;

    @BeforeEach
    public void init() {
        this.productList = new ArrayList<>();
        Product product = new Product();
        product.setId(25L);
        product.setName("produto teste");
        product.setDescription("Descricao do produto");
        product.setPrice(30D);

        this.productList.add(product);

        product = new Product();
        product.setId(10L);
        product.setName("produto teste 2");
        product.setDescription("Descricao do produto 2");
        product.setPrice(50D);

        this.productList.add(product);
    }

    @Test
    public void productToDTOTestOK() {
        Product product = this.productList.get(0);

        ProductDTO dto = ProductMapper.productToDTO(product);

        UtilForTest.checkIfIsEquals(product, dto);
    }

    @Test
    public void productToDTOTestWhenNull() {
        ProductDTO dto = ProductMapper.productToDTO(null);
        Assertions.assertNull(dto.getId());
        Assertions.assertNull(dto.getName());
        Assertions.assertNull(dto.getDescription());
        Assertions.assertNull(dto.getPrice());
    }

    @Test
    public void listProductToDTOSTestOK() {
        List<ProductDTO> dtos = ProductMapper.listProductDTOS(this.productList);
        Assertions.assertEquals(dtos.size(), this.productList.size());
        for (int i = 0; i < this.productList.size(); i++) {
            UtilForTest.checkIfIsEquals(this.productList.get(i), dtos.get(i));
        }
    }

}
