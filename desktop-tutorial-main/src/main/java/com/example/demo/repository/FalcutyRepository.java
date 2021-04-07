package com.example.demo.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.*;


public interface FalcutyRepository extends JpaRepository<Falcuty, Long> {

	Optional<Falcuty> findByName(EFalcuty falcuty);
}
