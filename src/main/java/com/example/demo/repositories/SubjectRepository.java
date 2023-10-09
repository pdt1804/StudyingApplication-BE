package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {

}
