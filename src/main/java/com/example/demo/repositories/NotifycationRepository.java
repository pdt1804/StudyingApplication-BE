package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Notifycation;

@Repository
public interface NotifycationRepository extends JpaRepository<Notifycation, Integer>{

}
