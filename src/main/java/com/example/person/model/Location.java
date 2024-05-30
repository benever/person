package com.example.person.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Location {
    @Id
    @GeneratedValue
    @JsonIgnore
    private int id;

    private String latitude;
    private String longitude;
    private String city;

    @OneToOne
    @JsonIgnore
    private Person person;


    public Location(String latitude,String longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public Location(String city){
        this.city=city;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Location{" +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
