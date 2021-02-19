package com.marketbase.resources.repositories;

import com.marketbase.resources.models.DeployDebugMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeployDebugMessageRepository extends JpaRepository<DeployDebugMessage, Long> {
	List<DeployDebugMessage> findByOrderId(Long id);
}
