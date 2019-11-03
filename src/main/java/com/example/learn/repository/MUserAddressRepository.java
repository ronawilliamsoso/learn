package com.example.learn.repository;


import com.example.learn.model.MUserAddress;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MUserAddressRepository extends JpaRepository<MUserAddress,Integer>{

  Optional<String> forCity(final Integer userId);


}
