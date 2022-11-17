package com.ssafy.apartment.model;

import lombok.Data;

@Data
public class HouseDealDto {
    private String no;
    private String dealAmount;
    private String dealYear;
    private String dealMonth;
    private String dealDay;
    private String area;
    private String floor;
    private String aptCode;
    
    private String aptName;
    private String dongName;
    private String roadName;
    private String roadNamebonbun;
    
	public HouseDealDto(String no, String dealAmount, String dealYear, String dealMonth, String dealDay, String area,
			String floor, String aptCode, String aptName, String dongName, String roadName, String roadNamebonbun) {
		super();
		this.no = no;
		this.dealAmount = dealAmount;
		this.dealYear = dealYear;
		this.dealMonth = dealMonth;
		this.dealDay = dealDay;
		this.area = area;
		this.floor = floor;
		this.aptCode = aptCode;
		this.aptName = aptName;
		this.dongName = dongName;
		this.roadName = roadName;
		this.roadNamebonbun = roadNamebonbun;
	}

}
