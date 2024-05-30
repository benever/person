package com.example.person.controller;

import com.example.person.model.Location;
import com.example.person.model.Person;
import com.example.person.model.Weather;
import com.example.person.repository.PersonRepository;
import com.example.person.service.PersonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonRepository repository;
    private PersonService service;

    public PersonController(PersonService service) {
        this.service = service;
    }

    @GetMapping
    public Iterable<Person> findAll() {
        return service.findAllService();
    }

    @GetMapping("/{id}")
    public Optional<Person> findById(@PathVariable int id) {
        return service.findByIdService(id);
    }


    @GetMapping("/{id}/weather")
    public Weather findByIdLocationWeather(@PathVariable int id) {
        return service.findByIdLocationWeatherService(id);
    }

    @PostMapping
    public ResponseEntity<Person> save(@RequestBody Person person) {
        return service.saveService(person);
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable int id){
        service.deletePersonService(id);
    }
}