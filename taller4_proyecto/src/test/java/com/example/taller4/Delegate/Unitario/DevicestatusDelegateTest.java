package com.example.taller4.Delegate.Unitario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//import org.junit.jupiter.api.Test;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;

import com.example.taller2.Taller2Application;
import com.example.taller2.delegate.implementation.DevicestatusDelegateImp;
import com.example.taller2.model.Devicestatus;

@ContextConfiguration(classes = Taller2Application.class)
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class DevicestatusDelegateTest {

	private String PATH = "http://localhost:8082/devicestatusRest/";

	@Mock
	RestTemplate restTemplate;
	
	@InjectMocks
	@Autowired
	DevicestatusDelegateImp devstatusDele;

	@Test
	public void testSave() {
		Devicestatus status = new Devicestatus();
		status.setDevstatId(1);
		status.setDevstatName("new Status");

		Mockito.when(restTemplate.getForObject(PATH + status.getDevstatId(), Devicestatus.class)).thenReturn(status);
		Mockito.when(restTemplate.postForEntity(PATH, status, Devicestatus.class))
				.thenReturn((new ResponseEntity<Devicestatus>(status, HttpStatus.OK)));
		devstatusDele.saveDevicestatus(status);
		assertEquals(status, devstatusDele.findById(status.getDevstatId()));
		
	}

	@Test
	public void testFindAll() {
		Devicestatus status = new Devicestatus();
		status.setDevstatId(1);
		status.setDevstatName("new Status");

		Devicestatus[] list = { status };
		Mockito.when(restTemplate.getForObject(PATH, Devicestatus[].class)).thenReturn(list);

		assertTrue((devstatusDele.findAll().size()) != 0);
	}

	@Test
	public void testFindById() {

		Devicestatus status = new Devicestatus();
		status.setDevstatId(1);
		status.setDevstatName("new Status");

		Mockito.when(restTemplate.getForObject(PATH + status.getDevstatId(), Devicestatus.class)).thenReturn(status);

		assertEquals(devstatusDele.findById(status.getDevstatId()).getDevstatName(), "new Status");
	}

	@Test
	public void testUpdate() {

		Devicestatus status = new Devicestatus();
		status.setDevstatId(1);
		status.setDevstatName("new Status");
		Mockito.doNothing().when(restTemplate).put(PATH,status,Devicestatus.class);

		assertTrue(devstatusDele.updateDevicestatus(status) != null);
	}
}
