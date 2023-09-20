package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Downside;

@Repository
public interface DownsideRepository extends JpaRepository<Downside, Integer> {

}
