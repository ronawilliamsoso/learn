package com.example.learn.controller;

import com.example.learn.repository.PlayerRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.learn.model.Player;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/player")
public class PlayerController {

	@Autowired
	private PlayerRepository playerRepository;

	@ApiOperation(value = "find by id")
	@RequestMapping(value = "/findById", method = RequestMethod.GET)
	public Object findById(Integer Id) {
		Map<String, Object> map = new HashMap<String, Object>();

		Optional<Player> optional = playerRepository.findById(Id);

		if (optional.isPresent()) {
			Player player = optional.get();
			map.put("status", 200);
			map.put("player", player.getName());

		} else {
			map.put("status", 201);
			map.put("player", Optional.empty());
		}

		return map;

	}
}
