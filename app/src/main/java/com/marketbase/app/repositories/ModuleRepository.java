package com.marketbase.app.repositories;

import com.marketbase.app.models.Module;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleRepository extends JpaRepository<Module, Long> {
}
