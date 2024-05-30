package com.example.person.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Weather {
    private double temp;
    private double windSpeed;
    private String main;
    private String description;

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String  toString() {
        return "Weather{" +
                "temp=" + temp +
                ", windSpeed=" + windSpeed +
                ", main='" + main + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
