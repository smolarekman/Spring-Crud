package com.example.simplecrud.controller;

import com.example.simplecrud.exception.ResourceNotFoundException;
import com.example.simplecrud.model.Person;
import com.example.simplecrud.respository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/people")
public class PersonController {

    @Autowired
    PersonRepository personRepository;

    @PostMapping("/addNewPerson")
    public Person addPerson(@Valid @RequestBody Person person) {
        return personRepository.save(person);
    }

    @GetMapping("/showAllPeople")
    public List<Person> showPeople() {
        return personRepository.findAll();
    }

    @GetMapping("/showPerson/{personId}")
    public Person findById(@PathVariable(value = "personId") Long personId) {
        return personRepository.findById(personId)
                .orElseThrow(() -> new ResourceNotFoundException("Person", "id", personId));
    }

    @DeleteMapping("/deletePerson/{personId}")
    public ResponseEntity<?> deletePerson(@PathVariable(value = "personId") Long personId) {

        Person person=personRepository.findById(personId)
                .orElseThrow(() -> new ResourceNotFoundException("Person", "id", personId));
        personRepository.delete(person);
        return ResponseEntity.ok("Succes");
    }

    @PutMapping("/editPerson/{personId}")
    public Person updatePerson(@Valid @RequestBody Person personDetails,
                               @PathVariable (value = "personId") Long personId){
        Person person=personRepository.findById(personId)
                .orElseThrow(() -> new ResourceNotFoundException("Person", "id", personId));

        person.setFirstName(personDetails.getFirstName());
        person.setLastName(personDetails.getLastName());
        person.setPhoneNumber(personDetails.getPhoneNumber());

        return personRepository.save(person);
    }
}
