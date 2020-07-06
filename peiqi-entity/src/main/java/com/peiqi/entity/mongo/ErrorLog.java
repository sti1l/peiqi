package com.peiqi.entity.mongo;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 系统日志
 * 
 * @author STILL
 *
 */
@SuppressWarnings("serial")
@Document(collection = "error_log")
public class ErrorLog implements Serializable {
	
	@Id
	private String id;
	private String username;
	private String method;
	private String params;
	private String ip;
	private Date createTime;
	private String operation;
	private String errorMsg;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String error) {
		this.errorMsg = error;
	}

	@Override
	public String toString() {
		return "ErrorLog [id=" + id + ", username=" + username + ", method=" + method + ", params=" + params + ", ip="
				+ ip + ", createTime=" + createTime + ", operation=" + operation + ", errorMsg=" + errorMsg + "]";
	}
	
	

}
