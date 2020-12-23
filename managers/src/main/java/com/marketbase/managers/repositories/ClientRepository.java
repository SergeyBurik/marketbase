package com.marketbase.managers.repositories;

import com.marketbase.managers.models.Client;
import com.marketbase.managers.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {

	List<Client> findByManager(User manager);
	List<Client> findByStatus(String s);

}
