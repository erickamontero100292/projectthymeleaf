package com.workday.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = "com.workday")
@PropertySource("classpath:/propertiesConfiguration.properties")
public class PropertiesConfiguration {
	
	@Value("${days.week}")
	private int daysWeek;

	@Value("${days.year}")
	private int daysYear;

	@Value("${max.hourWorkpermited}")
	private int allowedHours;

	public int getDaysWeek() {
		return daysWeek;
	}

	public void setDaysWeek(int daysWeek) {
		this.daysWeek = daysWeek;
	}

	public int getDaysYear() {
		return daysYear;
	}

	public void setDaysYear(int daysYear) {
		this.daysYear = daysYear;
	}

	public int getAllowedHours() {
		return allowedHours;
	}

	public void setAllowedHours(int allowedHours) {
		this.allowedHours = allowedHours;
	}
}
