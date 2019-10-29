package com.workday.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


@Data
@Entity
@Table(name = "workday")
public class EntityWorkday {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Long numberDailyHour;

    private Long numberWeekHour;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Long getNumberWeekHour() {
        return numberWeekHour;
    }

    public void setNumberWeekHour(Long numberWeekHour) {
        this.numberWeekHour = numberWeekHour;
    }

    public EntityWorkday(Long id, String name, Long numberDailyHour) {
        super();
        this.id = id;
        this.name = name;
        this.numberDailyHour = numberDailyHour;
    }

    public Long getNumberDailyHour() {
        return numberDailyHour;
    }

    public void setNumberDailyHour(Long numberDailyHour) {
        this.numberDailyHour = numberDailyHour;
    }

    public EntityWorkday(Long id, String name, Long numberDailyHour, Long numberWeekHour) {
        super();
        this.id = id;
        this.name = name;
        this.numberDailyHour = numberDailyHour;
        this.numberWeekHour = numberWeekHour;
    }

    public EntityWorkday(String name, Long numberDailyHour, Long numberWeekHour) {
        this.name = name;
        this.numberDailyHour = numberDailyHour;
        this.numberWeekHour = numberWeekHour;
    }

    public EntityWorkday() {
		super();
	}


}
