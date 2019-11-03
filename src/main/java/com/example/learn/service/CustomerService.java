package com.example.learn.service;

import com.example.learn.repository.CustomerRepository;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.learn.model.Customer;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomerService {
	private CustomerRepository customerRepository;

	public Customer findById(Integer customerId) {

		return customerRepository.findById(customerId).get();
	}

	public List<Customer> findByAgeSmallerThan(Integer age) {
		Integer size = 10;
		Integer page = 0;
		String sort1 = "firstName";
		Sort sort = new Sort(Sort.Direction.ASC, sort1);
		Pageable pageable = PageRequest.of(page, size, sort);

		List<Customer> list = customerRepository.findByAgeLessThanEqual(age, pageable);
		return list;

	}

}
