package com.gherex.Alumnado;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
		"spring.datasource.url=jdbc:mysql://localhost:3306/alumnado",
		"spring.datasource.username=root",
		"spring.datasource.password=imreg091194"
})
class AlumnadoApplicationTests {

	@Test
	void contextLoads() {
	}

}
