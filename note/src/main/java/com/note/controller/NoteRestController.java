package com.note.controller;


import com.note.model.Note;
import com.note.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/admin")
public class NoteRestController {

    @Autowired
    private NoteService noteService;

    @RequestMapping(value = "/notes", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Note>> list() {
        List<Note> staffs = noteService.findAllList();
        if (staffs.isEmpty()) {
            return new ResponseEntity<List<Note>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Note>>(staffs, HttpStatus.OK);
    }

    @RequestMapping(value = "/notes/{id}", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Note>> get(@PathVariable("id") Long id) {
        Optional<Note> staff = noteService.findById(id);
        if (staff == null) {
            return new ResponseEntity<Optional<Note>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Optional<Note>>(staff,HttpStatus.OK);
    }

    @RequestMapping(value = "/notes", method = RequestMethod.POST)
    public ResponseEntity<Void> create(@RequestBody Note note, UriComponentsBuilder ucBuilder){
        noteService.save(note);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/notes/{id}").buildAndExpand(note.getId()).toUri());
        return new ResponseEntity<Void>(headers,HttpStatus.CREATED);
    }

    @RequestMapping(value = "/notes/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Optional<Note>> update(@PathVariable("id") Long id, @RequestBody Note note){
        Optional<Note> currentNote = noteService.findById(id);
        if(currentNote == null){
            return new ResponseEntity<Optional<Note>>(HttpStatus.NOT_FOUND);
        }
        currentNote.get().setTilte(note.getTilte());
        currentNote.get().setContent(note.getContent());
        currentNote.get().setCategory(note.getCategory());

        currentNote.get().setId(note.getId());


        noteService.save(currentNote.get());
        return new ResponseEntity<Optional<Note>>(currentNote, HttpStatus.OK);
    }

    @RequestMapping(value = "/notes/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Optional<Note>> delete(@PathVariable("id") Long id){
        Optional<Note> staff = noteService.findById(id);
        if(staff == null){
            return new ResponseEntity<Optional<Note>>(HttpStatus.NOT_FOUND);

        }
        noteService.delete(id);
        return new ResponseEntity<Optional<Note>>(HttpStatus.NO_CONTENT);
    }
}
