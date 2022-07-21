package com.danielserva.malda;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.danielserva.malda.controller.DetectionController;
import com.danielserva.malda.controller.DeviceController;

@SpringBootTest
@AutoConfigureMockMvc
public class DeviceControllerTest {
    @Autowired
	private MockMvc mockMvc;

	@Autowired
	DeviceController deviceController;

	@Autowired
	DetectionController detectionController;

	@Test
	public void shouldReturn200WhenSendingRequestToController() throws Exception {
	  
	  this.mockMvc.perform(get("/device"))
	  	.andDo(print())
		.andExpect(status().isOk()) ;
	}
}
