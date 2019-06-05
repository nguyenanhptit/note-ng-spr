package com.note.service.impl;

import com.note.model.Category;
import com.note.model.Note;
import com.note.repository.NoteRepository;
import com.note.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("noteservice")
public class NoteServiceImpl implements NoteService {
    @Autowired
    private NoteRepository noteRepository;

    @Override
    public Page<Note> findAll(Pageable pageable) {
        return noteRepository.findAll(pageable);
    }

    @Override
    public List<Note> findAllList() {
        return noteRepository.findAll();
    }

    public Optional<Note> findById(Long id) {
        return noteRepository.findById(id);
    }

    @Override
    public void save(Note note) {
        noteRepository.save(note);
    }

    @Override
    public void delete(Long id) {
        noteRepository.deleteById(id);
    }

    @Override
    public Page<Note> findByTitleOrContent(String title, String content, Pageable pageable) {
        return noteRepository.findAllByTilteContainingOrContentContaining(title, content, pageable);
    }

    @Override
    public Page<Note> findAllByCategory(Optional<Category> category, Pageable pageable) {
        return noteRepository.findAllByCategory(category, pageable);
    }


}
