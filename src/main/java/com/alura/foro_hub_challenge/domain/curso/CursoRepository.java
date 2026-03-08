package com.alura.foro_hub_challenge.domain.curso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    // - findAll()
    // - findById(Long id)
    // - save(Curso curso)
    // - deleteById(Long id)

}