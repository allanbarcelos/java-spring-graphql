package dev.barcelos.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import dev.barcelos.models.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}