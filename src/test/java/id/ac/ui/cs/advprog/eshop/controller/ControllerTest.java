package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.UUID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService service;

    @Test
     void testCreateProductPage() throws Exception {
        mockMvc.perform(get("/product/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("CreateProduct"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
     void testCreateProductPost() throws Exception {
        mockMvc.perform(post("/product/create")
                        .param("productName", "Test Product")
                        .param("productQuantity", "10"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"));

        Mockito.verify(service).create(Mockito.any(Product.class));
    }

    @Test
     void testProductListPage() throws Exception {
        Product product1 = new Product();
        product1.setProductId(UUID.randomUUID().toString());
        product1.setProductName("Product One");
        product1.setProductQuantity(50);

        Product product2 = new Product();
        product2.setProductId(UUID.randomUUID().toString());
        product2.setProductName("Product Two");
        product2.setProductQuantity(100);

        Mockito.when(service.findAll()).thenReturn(Arrays.asList(product1, product2));

        mockMvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("productlist"))
                .andExpect(model().attributeExists("products"));
    }

    @Test
     void testDeleteProduct() throws Exception {
        String id = UUID.randomUUID().toString();

        mockMvc.perform(get("/product/delete/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));

        Mockito.verify(service).delete(id);
    }

    @Test
     void testEditProductPage() throws Exception {
        String id = UUID.randomUUID().toString();
        Product product = new Product();
        product.setProductId(id);
        product.setProductName("Editable Product");
        product.setProductQuantity(20);

        Mockito.when(service.findid(id)).thenReturn(product);

        mockMvc.perform(get("/product/edit/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("editProduct"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
     void testEditProductPost() throws Exception {
        mockMvc.perform(post("/product/edit")
                        .param("productId", UUID.randomUUID().toString())
                        .param("productName", "Updated Product")
                        .param("productQuantity", "30"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));

        Mockito.verify(service).update(Mockito.any(Product.class));
    }
}
