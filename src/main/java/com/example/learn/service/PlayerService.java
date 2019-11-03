package com.example.learn.service;

import com.example.learn.repository.PlayerRepository;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.learn.model.Player;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PlayerService {

  @Autowired
  private PlayerRepository playerRepository;

  public Player getById(Integer id) {
    Optional<Player> optional = playerRepository.findById(id);
    return optional.orElseGet(null);
  }

}
