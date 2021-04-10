package com.marketbase.techmanagers.beans;

import java.util.Set;

public class Template {
	private Long id;

	private String name;

	private String preview;

	private String previewGif;

	private String projectName;

	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPreview() {
		return preview;
	}

	public void setPreview(String preview) {
		this.preview = preview;
	}

	public String getPreviewGif() {
		return previewGif;
	}

	public void setPreviewGif(String previewGif) {
		this.previewGif = previewGif;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
