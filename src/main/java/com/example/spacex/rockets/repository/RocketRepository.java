package com.example.spacex.rockets.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.spacex.rockets.model.Rocket;

@Repository
public interface RocketRepository extends JpaRepository<Rocket, String>, JpaSpecificationExecutor<Rocket> {

    Optional<Rocket> findByRocketId(String rocketId);

    boolean existsByRocketId(String rocketId);

    boolean existsByRocketName(String rocketName);

    boolean existsById(int id);

    @Transactional
    void deleteByRocketId(String rocketId);
}
