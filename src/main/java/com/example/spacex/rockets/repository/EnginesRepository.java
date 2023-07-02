package com.example.spacex.rockets.repository;

import com.example.spacex.rockets.model.Engines;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnginesRepository extends JpaRepository<Engines, Integer> {
}
