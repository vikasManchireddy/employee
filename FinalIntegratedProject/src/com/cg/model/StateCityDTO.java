package com.cg.model;

import java.io.Serializable;

public class StateCityDTO implements Serializable{
	
	private Integer slNo;
	public Integer getSlNo() {
		return slNo;
	}
	public void setSlNo(Integer slNo) {
		this.slNo = slNo;
	}
	private Integer stateCityId;
	private int cityParentId;
	private String stateCityName;
	private String stateCityStatus;
	
	
	public Integer getStateCityId() {
		return stateCityId;
	}
	public void setStateCityId(int stateCityId) {
		this.stateCityId = stateCityId;
	}
	public int getCityParentId() {
		return cityParentId;
	}
	public void setCityParentId(int cityParentId) {
		this.cityParentId = cityParentId;
	}
	public String getStateCityName() {
		return stateCityName;
	}
	public void setStateCityName(String stateCityName) {
		this.stateCityName = stateCityName;
	}
	public String getStateCityStatus() {
		return stateCityStatus;
	}
	public void setStateCityStatus(String stateCityStatus) {
		this.stateCityStatus = stateCityStatus;
	}

}
