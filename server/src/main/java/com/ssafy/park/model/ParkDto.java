package com.ssafy.park.model;

import com.ssafy.apartment.model.HouseDealDto;

import lombok.Data;

@Data
public class ParkDto {
	private String parkName;
	private String gugunName;
	private String address;
	private String phoneNum;
	private String lng;
	private String lnt;
	private String distance;

	public ParkDto(String parkName, String gugunName, String address, String phoneNum, String lng, String lnt,String distance) {
		super();
		this.parkName = parkName;
		this.gugunName = gugunName;
		this.address = address;
		this.phoneNum = phoneNum;
		this.lng = lng;
		this.lnt = lnt;
		this.distance = distance;
	}
}
