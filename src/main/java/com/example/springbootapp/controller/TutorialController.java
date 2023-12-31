package com.example.springbootapp.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import com.example.springbootapp.model.Tutorial;
import com.example.springbootapp.model.Type;
import com.example.springbootapp.repository.TutorialRepository;
import com.example.springbootapp.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.swing.*;

@CrossOrigin(origins = "http://localhost:8081") // port du frontend (Angular)
@RestController
@RequestMapping("/api")
public class TutorialController {

    @Autowired
    TutorialRepository tutorialRepository;
    @Autowired
    TypeRepository typeRepository;

    @GetMapping("/tutorials")
    public ResponseEntity<List<Tutorial>> getAllTutorials(@RequestParam(required = false) String title) {
        try {
            List<Tutorial> tutorials = new ArrayList<Tutorial>();

            if (title == null)
                tutorialRepository.findAll().forEach(tutorials::add);
            else
                tutorialRepository.findByTitleContaining(title).forEach(tutorials::add);

            if (tutorials.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(tutorials, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/tutorials/{id}")
    public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") long id) {
        Optional<Tutorial> tutorialData = tutorialRepository.findById(id);

        if (tutorialData.isPresent()) {
            return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/tutorials/{id}")
    public ResponseEntity<Tutorial>  createTutorial(@RequestBody Tutorial tutorial ,@PathVariable("id") Long id) {
        Type type= typeRepository.findById(id).get();
       // Tutorial _tutorial = tutorialRepository.save(Tutorial.builder().title(tutorial.getTitle()).description(tutorial.getDescription()).
            //    types(tutorial.getTypes()).build());
        tutorial.setTypes(type);
        tutorialRepository.save(tutorial);
        return new ResponseEntity<>(tutorial, HttpStatus.CREATED);
    }





        @PutMapping("/tutorials/{id}")
        public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") long id, @RequestBody Tutorial tutorial) {
            Optional<Tutorial> tutorialData = tutorialRepository.findById(id);

            if (tutorialData.isPresent()) {
                Tutorial _tutorial = tutorialData.get();
                _tutorial.setTitle(tutorial.getTitle());
                _tutorial.setDescription(tutorial.getDescription());
                return new ResponseEntity<>(tutorialRepository.save(_tutorial), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

        @DeleteMapping("/tutorials/{id}")
        public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
            try {
                tutorialRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @DeleteMapping("/tutorials")
        public ResponseEntity<HttpStatus> deleteAllTutorials() {
            try {
                tutorialRepository.deleteAll();
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }

    }


   /* @PostMapping("/tutorials")
    public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial) {
        Tutorial _tutorial = tutorialRepository.save(Tutorial.builder().title(tutorial.getTitle()).description(tutorial.getDescription()).
                types(tutorial.getTypes()).build());
        return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
    }
*/