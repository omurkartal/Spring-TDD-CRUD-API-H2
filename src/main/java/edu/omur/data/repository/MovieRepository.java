package edu.omur.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<MovieEntity, Long> {
}