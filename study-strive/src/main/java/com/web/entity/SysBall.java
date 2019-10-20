package com.web.entity;

public class SysBall extends BaseEntity{
	
	private String num;

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	@Override
	public String toString() {
		return "SysBall [num=" + num + ", getId()=" + getId() + "]";
	}
}
