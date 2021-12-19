package com.uol.compasso.productms;

import com.uol.compasso.productms.dto.ProductDTO;
import com.uol.compasso.productms.model.entity.Product;
import org.junit.jupiter.api.Assertions;

public class UtilForTest {
    public static void checkIfIsEquals(Product product, ProductDTO dto) {
        Assertions.assertEquals(dto.getId(), product.getId());
        Assertions.assertEquals(dto.getName(), product.getName());
        Assertions.assertEquals(dto.getDescription(), product.getDescription());
        Assertions.assertEquals(dto.getPrice(), product.getPrice());
    }
}
