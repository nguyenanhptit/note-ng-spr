package com.note.service;

import com.note.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    Page<Category> findAll(Pageable pageable);

    List<Category> findAllList();

    Optional<Category> findById(Long id);

    void save(Category category);

    void delete(Long id);

    Page<Category> findByName(String name, Pageable pageable);

}
