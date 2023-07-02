package com.example.spacex.rockets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.spacex.rockets.model.Payloads;

@Repository
public interface PayloadsRepository extends JpaRepository<Payloads, Integer> {
}
