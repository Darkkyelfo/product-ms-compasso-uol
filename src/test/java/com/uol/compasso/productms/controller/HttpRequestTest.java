package com.uol.compasso.productms.controller;

import com.google.gson.Gson;
import com.uol.compasso.productms.dto.ProductDTO;
import com.uol.compasso.productms.exception.ParamsInvalidException;
import com.uol.compasso.productms.exception.ProductNotFoundException;
import com.uol.compasso.productms.model.ProductSearchParam;
import com.uol.compasso.productms.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ProductController.class)
public class HttpRequestTest {

    @MockBean
    private ProductService productService;

    @Autowired
    private MockMvc mvc;

    @Captor
    ArgumentCaptor<ProductSearchParam> paramArgumentCaptor;

    @Test
    void getOneWhenNotExists() throws Exception {
        Long id = 1L;
        Mockito.when(productService.getOne(id)).thenThrow(new ProductNotFoundException(id));
        mvc.perform(get("/products/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ProductNotFoundException));
    }

    @Test
    void createProductCheckDTO() throws Exception {
        Gson gson = new Gson();
        ArrayList<ProductDTO> productDTOS = this.generateListForTest();
        for (ProductDTO dto : productDTOS) {
            mvc.perform(post("/products")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(gson.toJson(dto)))
                    .andExpect(status().isBadRequest())
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
        }
    }

    @Test
    void updateProductCheckDTO() throws Exception {
        Gson gson = new Gson();
        ArrayList<ProductDTO> productDTOS = this.generateListForTest();
        for (ProductDTO dto : productDTOS) {
            mvc.perform(post("/products")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(gson.toJson(dto)))
                    .andExpect(status().isBadRequest())
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
        }

    }

    @Test
    void searchProductsInvalidParams() throws Exception {
        Mockito.when(this.productService.searchItens(paramArgumentCaptor.capture())).thenThrow(new ParamsInvalidException(""));
        mvc.perform(get("/products/search")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ParamsInvalidException));
    }

    private ArrayList<ProductDTO> generateListForTest() {
        ArrayList<ProductDTO> productDTOS = new ArrayList<>();
        ProductDTO productDTO1 = new ProductDTO();
        productDTO1.setName("name");
        productDTO1.setDescription("description");
        //Teste
        ProductDTO productDTO2 = new ProductDTO();
        productDTO2.setPrice(10D);
        productDTO2.setDescription("description");

        ProductDTO productDTO3 = new ProductDTO();
        productDTO3.setPrice(10D);
        productDTO3.setName("name");

        productDTOS.add(new ProductDTO());
        productDTOS.add(productDTO1);
        productDTOS.add(productDTO2);
        productDTOS.add(productDTO3);
        return productDTOS;
    }
}
