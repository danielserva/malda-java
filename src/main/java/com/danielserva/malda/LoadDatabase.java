package com.danielserva.malda;

import com.danielserva.malda.model.DetectionRepository;
import com.danielserva.malda.model.DeviceRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
	CommandLineRunner initDatabase(DeviceRepository deviceRepository, DetectionRepository detectionRepository) {
	  return args -> {
		  log.info("Preloading ");
      // Set<Detection> detectionList = new LinkedHashSet<>();
      // Set<Detection> detectionList2 = new LinkedHashSet<>();
      
      // Device device =  deviceRepository.save(new Device(UUID.randomUUID(), DeviceType.ANDROID, "Asus ZenFone 5", "KitKat"));
      // detectionList.add(new Detection(UUID.randomUUID(), new Timestamp(System.currentTimeMillis()) ,"RYUK","Ransomware"));
      // detectionList.add(new Detection(UUID.randomUUID(), new Timestamp(System.currentTimeMillis()) ,"Astaroth","Fileless Malware"));
      // detectionList.add(new Detection(UUID.randomUUID(), new Timestamp(System.currentTimeMillis()) ,"DarkHotel","Spyware"));
      // detectionList.add(new Detection(UUID.randomUUID(), new Timestamp(System.currentTimeMillis()) ,"Fireball","Adware"));
      // detectionList.add(new Detection(UUID.randomUUID(), new Timestamp(System.currentTimeMillis()) ,"Emotet","Trojan"));
      // detectionList2.add(new Detection(UUID.randomUUID(), new Timestamp(System.currentTimeMillis()) ,"Stuxnet","Worm"));
      // detectionList2.add(new Detection(UUID.randomUUID(), new Timestamp(System.currentTimeMillis()) ,"Zacinlo","Rootkit"));
      // detectionList2.add(new Detection(UUID.randomUUID(), new Timestamp(System.currentTimeMillis()) ,"Olympic Vision","Keylogger"));
      // detectionList2.add(new Detection(UUID.randomUUID(), new Timestamp(System.currentTimeMillis()) ,"Echobot","Bot"));
      // detectionList2.add(new Detection(UUID.randomUUID(), new Timestamp(System.currentTimeMillis()) ,"Triada","Mobile Malware"));
      // detectionList2.add(new Detection(UUID.randomUUID(), new Timestamp(System.currentTimeMillis()) ,"log4j","logger"));
      // detectionList.forEach(d -> d.setDevice(device) );
      // detectionRepository.saveAll(detectionList);
      // detectionList2.forEach(d -> d.setDevice(device) );
      // detectionRepository.saveAll(detectionList);
      log.info("finished ");
	  };
	}
}
