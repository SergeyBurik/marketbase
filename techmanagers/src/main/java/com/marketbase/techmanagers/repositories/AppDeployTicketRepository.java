package com.marketbase.techmanagers.repositories;

import com.marketbase.techmanagers.models.AppDeployTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppDeployTicketRepository extends JpaRepository<AppDeployTicket, Long> {

	AppDeployTicket findByOrderId(Long id);

}
