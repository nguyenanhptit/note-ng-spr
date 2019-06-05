package com.note.repository;

import com.note.model.Category;
import com.note.model.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("noterepo")
public interface NoteRepository extends JpaRepository<Note, Long> {
    Page<Note> findAllByTilteContainingOrContentContaining(String title, String content, Pageable pageable);

    Page<Note> findAllByCategory(Optional<Category> category , Pageable pageable);
}
