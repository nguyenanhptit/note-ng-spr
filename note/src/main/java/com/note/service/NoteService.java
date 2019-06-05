package com.note.service;

import com.note.model.Category;
import com.note.model.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface NoteService {
    Page<Note> findAll (Pageable pageable);

    List<Note> findAllList();

    Optional<Note> findById(Long id);

    void save (Note note);

    void delete(Long id);

    Page<Note> findByTitleOrContent(String title, String content, Pageable pageable);

    Page<Note> findAllByCategory(Optional<Category> category, Pageable pageable);
}
