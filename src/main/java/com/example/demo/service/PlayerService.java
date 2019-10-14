package com.example.demo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.model.Player;
import com.example.demo.repository.PlayerRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PlayerService {

	private final PlayerRepository playerRepository;

	public Player getById(Integer id) {
		Optional<Player> optional = playerRepository.findById(id);
		return optional.orElseGet(null);
	}

}
