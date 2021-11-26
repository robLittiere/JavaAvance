package com.cergy.javaav;

import com.cergy.javaav.Services.ProductDao;
import com.cergy.javaav.controllers.ProductController;
import com.cergy.javaav.models.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class)
public class ApiProductControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductDao productDao;

    @Test
    public void testGetProducts() throws Exception {
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetProductById() throws Exception {
        mockMvc.perform(get("/products/2  "))
                    .andExpect(status().isOk());
    }

    @Test
    public void testPostProduct() throws Exception {
        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productDao.asJsonString(new Product()))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testPutProductById() throws Exception {
        mockMvc.perform(put("/products/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productDao.asJsonString(new Product()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteProduct() throws Exception {
        mockMvc.perform(delete("/products/2  "))
                .andExpect(status().isOk());
    }

}
