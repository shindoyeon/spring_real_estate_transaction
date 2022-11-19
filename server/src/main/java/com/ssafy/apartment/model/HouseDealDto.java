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
    private String lat;
    private String lng;
}
