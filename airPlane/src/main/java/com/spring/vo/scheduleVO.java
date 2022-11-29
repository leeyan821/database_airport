package com.spring.vo;

import java.time.LocalTime;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;

public class scheduleVO {
	int num;
	String airline;
	String name;
	String departure;
	String des;
	String de_time;
	String des_time;
	String day;
	String de_day;
	String des_day;
	String part;
	String price;
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getAirline() {
		return airline;
	}
	public void setAirline(String airline) {
		this.airline = airline;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDeparture() {
		return departure;
	}
	public void setDeparture(String departure) {
		this.departure = departure;
	}
	public String getDe_time() {
		return de_time;
	}
	public void setDe_time(String de_time) {
		this.de_time = de_time;
	}
	public String getDes_time() {
		return des_time;
	}
	public void setDes_time(String des_time) {
		this.des_time = des_time;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getDe_day() {
		return de_day;
	}
	public void setDe_day(String de_day) {
		this.de_day = de_day;
	}
	public String getDes_day() {
		return des_day;
	}
	public void setDes_day(String des_day) {
		this.des_day = des_day;
	}
	public String getPart() {
		return part;
	}
	public void setPart(String part) {
		this.part = part;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
}
