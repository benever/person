package com.example.person.service;

import com.example.person.model.Person;
import com.example.person.model.Weather;
import com.example.person.repository.PersonRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class PersonService {
    private final String API_KEY = "0c072284079a302d6d11ad45aaddc75c";
    @Autowired
    private PersonRepository repository;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public Iterable<Person> findAllService() {
        return repository.findAll();
    }

    public Optional<Person> findByIdService(int id) {
        return repository.findById(id);
    }

    public Weather findByIdLocationWeatherService(int id) {
        Weather nowWeather = new Weather();
        String lat = repository.findById(id).get().getLocation().getLatitude();
        String lon = repository.findById(id).get().getLocation().getLongitude();
        String urlString = "https://api.openweathermap.org/data/2.5/weather?lon=" + lon + "&lat=" + lat
                +"&units=metric&appid=" + API_KEY;
        try {
            StringBuilder result = new StringBuilder();
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();

            Map<String, Object> respMap = jsonToMap(result.toString());

            Map<String, Object> weatherMain = (Map<String, Object>) respMap.get("main");
            ArrayList<Map<String, Object>> weathers = (ArrayList<Map<String, java.lang.Object>>) respMap.get("weather");
            LinkedHashMap<Map<String, Object>, Object> weatherWind = (LinkedHashMap<Map<String, Object>, Object>) respMap.get("wind");

            nowWeather.setTemp((Double) weatherMain.get("temp"));
            nowWeather.setWindSpeed(Double.parseDouble(weatherWind.get("speed").toString()));
            nowWeather.setDescription((String) weathers.get(0).get("description"));
            nowWeather.setMain((String) weathers.get(0).get("main"));

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return nowWeather;
    }

    public ResponseEntity<Person> saveService(Person person) {
        if(person.getLocation().getCity() != null){
            setLatLon(person);
        }
        else if(person.getLocation().getLatitude() != null && person.getLocation().getLongitude() != null){
            setCity(person);
        }
        return repository.findById(person.getId()).isPresent()
                ? new ResponseEntity(repository.findById(person.getId()), HttpStatus.BAD_REQUEST)
                : new ResponseEntity(repository.save(person), HttpStatus.CREATED);
    }

    public void deletePersonService(int id) {
        repository.deleteById(id);
    }



    public Map<String, Object> jsonToMap(String str) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<Map<String, Object>> typeReference = new TypeReference<>() {
        };
        Map<String, Object> map = objectMapper.readValue(str, typeReference);
        return map;
    }
    public void setLatLon(Person person){
        String coordLon = "", coordLat = "";
        String location = person.getLocation().getCity();
        String API_KEY = "0c072284079a302d6d11ad45aaddc75c";
        String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" + location +
                "&appid=" + API_KEY + "&units=imperial";
        try {
            StringBuilder result = new StringBuilder();
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();

            Map<String, Object> respMap = jsonToMap(result.toString());

            Map<String, Object> coord = (Map<String, Object>) respMap.get("coord");
            coordLon = coord.get("lon").toString();
            coordLat = coord.get("lat").toString();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        person.getLocation().setLongitude(coordLon);
        person.getLocation().setLatitude(coordLat);
    }

    public void setCity(Person person){
        String API_KEY = "0c072284079a302d6d11ad45aaddc75c";
        String lat = person.getLocation().getLatitude();
        String lon = person.getLocation().getLongitude();
        String urlString = "https://api.openweathermap.org/data/2.5/weather?lon=" + lon + "&lat=" + lat
                +"&units=metric&appid=" + API_KEY;
        try {
            StringBuilder result = new StringBuilder();
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();

            Map<String, Object> respMap = jsonToMap(result.toString());

            String name = respMap.get("name").toString();
            System.out.println(name);
            person.getLocation().setCity(name);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}