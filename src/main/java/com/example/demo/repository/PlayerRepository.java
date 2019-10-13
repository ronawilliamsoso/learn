package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {

	List<Player> findByHeightLessThan(Integer height);

}
