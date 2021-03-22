package com.marketbase.app.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class AppProperty {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "key_")
	private String key;

	@Column(name = "value_")
	private String value;

	private Timestamp last_changed;

	public AppProperty() {
	}

	public AppProperty(Long id, String key, String value, Timestamp last_changed) {
		this.id = id;
		this.key = key;
		this.value = value;
		this.last_changed = last_changed;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Timestamp getLast_changed() {
		return last_changed;
	}

	public void setLast_changed(Timestamp last_changed) {
		this.last_changed = last_changed;
	}
}
