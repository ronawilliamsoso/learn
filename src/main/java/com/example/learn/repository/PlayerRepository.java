package com.example.learn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.learn.model.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {

	List<Player> findByHeightLessThan(Integer height);

}
