package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Player;
import com.example.demo.repository.PlayerRepository;

@RestController
@RequestMapping("/player")
public class PlayerController {

	@Autowired
	private PlayerRepository playerRepository;

	@RequestMapping(value = "/findById", method = RequestMethod.GET)
	public Object findById(Integer Id) {
		Map<String, Object> map = new HashMap<String, Object>();

		Optional<Player> optional = playerRepository.findById(Id);
		if (optional.isPresent()) {
			map.put("status", 200);
			map.put("player", optional.get());

		} else {
			map.put("status", 201);
			map.put("player", Optional.empty());
		}

		return map;

	}
}
