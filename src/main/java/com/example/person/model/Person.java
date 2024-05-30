package com.example.person.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Map;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Person {
    @Id
    @GeneratedValue
    private int id;

    @NonNull
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private Location location;
    public Person(@NonNull String name, Location location) {
        this.name = name;
        this.location = location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

}