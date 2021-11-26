package com.cergy.javaav;

import com.cergy.javaav.controllers.ProductController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class JavaavApplicationTests {

	@Autowired
	private ProductController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();

	}

}
