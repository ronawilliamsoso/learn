package com.example.learn.repository;

import com.example.learn.model.HibernateStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HibernateStatusRepository extends JpaRepository<HibernateStatus,Long>{
}
