package com.danielserva.malda;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.danielserva.malda.controller.DetectionController;
import com.danielserva.malda.controller.DeviceController;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class MaldaApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	DeviceController deviceController;

	@Autowired
	DetectionController detectionController;

	@Test
	void contextLoads() {
		assertThat(deviceController).isNotNull();
		assertThat(detectionController).isNotNull();
	}

	@Test
	public void shouldReturnDefaultMessage() throws Exception {
		this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Welcome to Malda, a simple Malware detection api written in java")));
	}

}
