package com.energicube.eno.monitor.repository;

import com.energicube.eno.monitor.model.Doctypes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctypesRepository extends JpaRepository<Doctypes, Integer> {
}