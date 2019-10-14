package com.example.demo.demo;

import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.demo.model.Player;
import com.example.demo.service.PlayerService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration(classes = { PlayerService.class })
public class DemoApplicationTests {

	@Autowired
	PlayerService playerService;

	@Test
	public void contextLoads() {

		Player player = playerService.getById(2);
		assertNull(player);
	}

}
