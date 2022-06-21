package com.javaapi.biblioteca.repositories;

import com.javaapi.biblioteca.models.LivroModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepository extends JpaRepository<LivroModel, Integer> {

    public boolean existsById(int idLivro);
}
