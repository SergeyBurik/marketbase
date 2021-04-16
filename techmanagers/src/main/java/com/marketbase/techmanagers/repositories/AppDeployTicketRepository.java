package com.marketbase.techmanagers.repositories;

import com.marketbase.techmanagers.models.AppDeployTicket;
import com.marketbase.techmanagers.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppDeployTicketRepository extends JpaRepository<AppDeployTicket, Long> {

	AppDeployTicket findByOrderId(Long id);
	List<AppDeployTicket> findByUser(User u);

}
