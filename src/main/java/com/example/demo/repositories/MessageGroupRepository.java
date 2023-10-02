package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.MessageGroup;

@Repository
public interface MessageGroupRepository extends JpaRepository<MessageGroup, Long> {

}
