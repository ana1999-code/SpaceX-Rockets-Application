package com.example.spacex.rockets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.spacex.rockets.model.FirstStage;

@Repository
public interface FirstStageRepository extends JpaRepository<FirstStage, Integer> {
}
