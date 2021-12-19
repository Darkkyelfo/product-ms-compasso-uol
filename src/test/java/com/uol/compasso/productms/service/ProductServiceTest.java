package com.uol.compasso.productms.service;

import com.uol.compasso.productms.UtilForTest;
import com.uol.compasso.productms.dto.ProductCreateDTO;
import com.uol.compasso.productms.dto.ProductDTO;
import com.uol.compasso.productms.exception.ParamsInvalidException;
import com.uol.compasso.productms.exception.ProductNotFoundException;
import com.uol.compasso.productms.mapper.ProductMapper;
import com.uol.compasso.productms.model.ProductSearchParam;
import com.uol.compasso.productms.model.entity.Product;
import com.uol.compasso.productms.repository.product.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.uol.compasso.productms.util.GeneralMessages.SUCCESS_TO_DELETE;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Captor
    ArgumentCaptor<Specification<Product>> specificationArgumentCaptor;

    @Captor
    ArgumentCaptor<Product> productArgumentCaptor;

    @Test
    void getAllWithItem() {
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product());
        Mockito.when(this.productRepository.findAll()).thenReturn(products);
        Assertions.assertEquals(this.productService.getAll().size(), products.size());
    }

    @Test
    void getAllEmpity() {
        ArrayList<Product> products = new ArrayList<>();
        Mockito.when(this.productRepository.findAll()).thenReturn(products);
        Assertions.assertEquals(this.productService.getAll().size(), products.size());
    }

    @Test
    void getOneWhenExists() throws ProductNotFoundException {
        Long id = 1L;
        Product product = new Product();
        product.setId(id);
        product.setDescription("Teste");
        product.setName("nome");
        product.setPrice(25D);
        Mockito.when(productRepository.findById(id)).thenReturn(Optional.of(product));
        ProductDTO dto = this.productService.getOne(id);
        UtilForTest.checkIfIsEquals(product, dto);


    }

    @Test
    public void getOneWhenNotExist() {
        Long id = 888L;
        Mockito.when(productRepository.findById(id)).thenReturn(Optional.ofNullable(null));
        Assertions.assertThrows(ProductNotFoundException.class, () -> {
            this.productService.getOne(888L);
        }, id.toString());

    }

    @Test
    void searchItensWithInvalid() {
        ProductSearchParam productSearchParam = new ProductSearchParam();
        this.searchMockTestInvalid(productSearchParam);

        productSearchParam = new ProductSearchParam();
        productSearchParam.setMinPrice(-50D);
        this.searchMockTestInvalid(productSearchParam);

        productSearchParam = new ProductSearchParam();
        productSearchParam.setMaxPrice(-50D);
        this.searchMockTestInvalid(productSearchParam);

        productSearchParam.setSearchText("");
        this.searchMockTestInvalid(productSearchParam);

    }

    @Test
    void searchItensWithValid() throws ParamsInvalidException {

        ProductSearchParam productSearchParam = new ProductSearchParam();
        productSearchParam.setMinPrice(10D);
        this.searchMockTest(productSearchParam);

        productSearchParam.setMinPrice(0D);
        this.searchMockTest(productSearchParam);

        productSearchParam = new ProductSearchParam();
        productSearchParam.setMaxPrice(10D);
        this.searchMockTest(productSearchParam);

        productSearchParam.setMaxPrice(0D);
        this.searchMockTest(productSearchParam);

        productSearchParam = new ProductSearchParam();
        productSearchParam.setSearchText("teste");
        this.searchMockTest(productSearchParam);


    }


    @Test
    void insertProduct() {
        ProductCreateDTO productDTO = new ProductCreateDTO();
        productDTO.setName("nome");
        productDTO.setDescription("descricao");
        productDTO.setPrice(25D);

        this.productService.insertProduct(productDTO);
        Mockito.verify(productRepository, Mockito.atLeast(1)).save(productArgumentCaptor.capture());
    }

    @Test
    void deleteProductWhenNotExist() {
        Long id = 1L;
        Mockito.when(productRepository.findById(id)).thenReturn(Optional.ofNullable(null));
        Assertions.assertThrows(ProductNotFoundException.class, () -> {
            this.productService.deleteProduct(id);
        }, id.toString());
    }

    @Test
    void deleteProductWhenExist() throws ProductNotFoundException {
        Long id = 1L;
        Product product = new Product();
        product.setId(id);
        Mockito.when(productRepository.findById(id)).thenReturn(Optional.of(product));
        Assertions.assertEquals(SUCCESS_TO_DELETE.get(), this.productService.deleteProduct(id));
    }

    @Test
    void updateProductWhenNotExists() {
        Long id = 1L;
        Mockito.when(productRepository.findById(id)).thenReturn(Optional.ofNullable(null));
        Assertions.assertThrows(ProductNotFoundException.class, () -> {
            this.productService.updateProduct(id, new ProductDTO());
        }, id.toString());
    }

    @Test
    void updateProductWhenExists() throws ProductNotFoundException {
        Long id = 1L;

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(id);
        productDTO.setPrice(25D);
        productDTO.setDescription("desc");
        productDTO.setName("nome");

        Product product = new Product();
        product.setId(productDTO.getId());
        product.setDescription("descricao diferente");
        product.setName("outro nome");
        product.setPrice(50D);

        Mockito.when(productRepository.findById(id)).thenReturn(Optional.of(product));
        this.productService.updateProduct(id, productDTO);
        UtilForTest.checkIfIsEquals(product, productDTO);
    }

    private void searchMockTest(ProductSearchParam productSearchParam) throws ParamsInvalidException {
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product());
        Mockito.when(productRepository.findAll(this.specificationArgumentCaptor.capture())).thenReturn(products);
        List<ProductDTO> productDTOS = productService.searchItens(productSearchParam);
        Assertions.assertEquals(productDTOS.size(), products.size());
    }

    private void searchMockTestInvalid(ProductSearchParam productSearchParam) {
        Assertions.assertThrows(ParamsInvalidException.class, () -> {
            this.productService.searchItens(productSearchParam);
        }, "invalid");
    }


}