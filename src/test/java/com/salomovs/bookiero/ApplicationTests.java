package com.salomovs.bookiero;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {
	@Test
	void contextLoads() {
    assertEquals(2, 1+1);
	}
}
