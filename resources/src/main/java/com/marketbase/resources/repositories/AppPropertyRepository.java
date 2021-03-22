package com.marketbase.resources.repositories;

import com.marketbase.resources.models.AppProperty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AppPropertyRepository extends JpaRepository<AppProperty, Long> {
	Optional<AppProperty> getByKey(String key);
}
