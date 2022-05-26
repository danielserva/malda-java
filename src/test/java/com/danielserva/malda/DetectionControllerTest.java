package com.danielserva.malda;

import static org.assertj.core.api.Assertions.assertThat;

import com.danielserva.malda.controller.DetectionController;
import com.danielserva.malda.controller.DeviceController;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DetectionControllerTest {

    @Autowired
	DeviceController deviceController;

	@Autowired
	DetectionController detectionController;

	@Test
	void contextLoads() {
		assertThat(deviceController).isNotNull();
		assertThat(detectionController).isNotNull();
	}
    
}
