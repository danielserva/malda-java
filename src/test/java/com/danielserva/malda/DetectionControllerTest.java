package com.danielserva.malda;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.danielserva.malda.controller.DetectionController;
import com.danielserva.malda.controller.DeviceController;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class DetectionControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
    @Autowired
	DeviceController deviceController;

	@Autowired
	DetectionController detectionController;

	
	@Test
	public void shouldReturn200WhenSendingRequestToController() throws Exception {
	  
	  this.mockMvc.perform(get("/detection"))
	  	.andDo(print())
		.andExpect(status().isOk()) ;
	}

	@Test
	public void shouldReturn2XXWhenPostingDetection() throws Exception {
	  
	  this.mockMvc.perform(post("/detection")
	  	  .contentType(MediaType.APPLICATION_JSON)
		  .content("{\"uuid\":\"8be1e5f3-746f-4c01-99ed-b33015fd2a35\", \"type\": \"IOS\", \"model\":\"iPhone X\", \"osVersion\":\"iOS 11.0.1\", \"detections\": [{\"uuid\":\"b3527d46-b01c-45d6-9099-97cd1d5beb9a\",\"type\":\"NEW\",\"time\":\"2022-05-24T17:29:56.740+00:00\",\"nameOfApp\":\"Fireball\",\"typeOfApp\":\"Adware\"},{\"uuid\":\"27e9873e-cdd8-4d1e-9fed-c3fbf054c240\",\"type\":\"NEW\",\"time\":\"2022-05-24T17:29:56.740+00:00\",\"nameOfApp\":\"Emotet\",\"typeOfApp\":\"Trojan\"}] }")
		  )
	  	.andDo(print())
		.andExpect(status().is2xxSuccessful()) ;
	}

	@Test
	public void shouldReturnErrorWhenPostingDetectionWithoutTime() throws Exception {
	  
	  this.mockMvc.perform(post("/detection")
	  	  .contentType(MediaType.APPLICATION_JSON)
		  .content("{\"uuid\":\"8be1e5f3-746f-4c01-99ed-b33015fd2a35\", \"type\": \"IOS\", \"model\":\"iPhone X\", \"osVersion\":\"iOS 11.0.1\", \"detections\": [{\"uuid\":\"b3527d46-b01c-45d6-9099-97cd1d5beb9a\",\"type\":\"NEW\",\"nameOfApp\":\"Fireball\",\"typeOfApp\":\"Adware\"},{\"uuid\":\"27e9873e-cdd8-4d1e-9fed-c3fbf054c240\",\"type\":\"NEW\",\"time\":\"2022-05-24T17:29:56.740+00:00\",\"nameOfApp\":\"Emotet\",\"typeOfApp\":\"Trojan\"}] }")
		  )
	  	.andDo(print())
		.andExpect(status().is4xxClientError()) ;
	}


    
}
