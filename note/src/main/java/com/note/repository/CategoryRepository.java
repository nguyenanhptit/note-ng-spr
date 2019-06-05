package com.note.repository;

import com.note.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("categoryrepo")
public interface CategoryRepository extends JpaRepository<Category,Long> {
    Page<Category> findAllByNameContaining(String name, Pageable pageable);
}
