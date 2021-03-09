package com.marketbase.admin.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "dbhistory_")
public class DBHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "type_")
	private String type;

	@Column(name = "table_")
	private String table;

	@Column(name = "timestamp_")
	private Date timestamp;

	@ManyToOne
	@JoinColumn
	private User admin;

	public DBHistory(String type, String table, Date timestamp, User user) {
		this.type = type;
		this.table = table;
		this.timestamp = timestamp;
		this.admin = user;
	}

	public DBHistory() {
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public User getAdmin() {
		return admin;
	}

	public void setAdmin(User admin) {
		this.admin = admin;
	}
}
