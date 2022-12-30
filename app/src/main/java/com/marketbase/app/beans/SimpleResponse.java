package com.marketbase.app.beans;

public class SimpleResponse {
	private Integer code;
	private String message;
	private Long objId;

	public SimpleResponse(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public SimpleResponse(Integer code, String message, Long objId) {
		this.code = code;
		this.message = message;
		this.objId = objId;
	}

	public Long getObjId() {
		return objId;
	}

	public void setObjId(Long objId) {
		this.objId = objId;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
