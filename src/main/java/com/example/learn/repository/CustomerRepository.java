package com.example.learn.repository;

import com.example.learn.model.Customer;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	List<Customer> findByAgeLessThanEqual(Integer age, Pageable page);

}
