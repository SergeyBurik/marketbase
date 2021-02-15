package com.marketbase.resources.repositories;

import com.marketbase.resources.models.DeployDebugMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeployDebugMessageRepository extends JpaRepository<DeployDebugMessage, Long> {
}
