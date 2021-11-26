package com.cergy.javaav;
import com.cergy.javaav.Services.CategoryDao;
import com.cergy.javaav.Services.ProductDao;
import com.cergy.javaav.controllers.CategoryController;
import com.cergy.javaav.controllers.ProductController;
import com.cergy.javaav.models.Category;
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

@WebMvcTest(controllers = CategoryController.class)
public class ApiCategoryControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryDao categoryDao;

    @Test
    public void testGetCategories() throws Exception {
        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk());
    }

    @Test
    public void testPostCategory() throws Exception {
        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryDao.asJsonString(new Category()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testPutCategoryId() throws Exception {
        mockMvc.perform(put("/categories/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryDao.asJsonString(new Category()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}

