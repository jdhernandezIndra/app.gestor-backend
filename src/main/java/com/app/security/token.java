package com.app.security;

import java.util.Date;

public class token {

	private String token;
	private String token_type;
	private String acces_token;
	private Date time_expire;
	private Date timeAt;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAcces_token() {
		return acces_token;
	}

	public void setAcces_token(String acces_token) {
		this.acces_token = acces_token;
	}

	public Date getTime_expire() {
		return time_expire;
	}

	public void setTime_expire(Date time_expire) {
		this.time_expire = time_expire;
	}

	public Date getTimeAt() {
		return timeAt;
	}

	public void setTimeAt(Date timeAt) {
		this.timeAt = timeAt;
	}

	public String getToken_type() {
		return token_type;
	}

	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}
	
	

}
