package com.javaapi.biblioteca.repositories;

import com.javaapi.biblioteca.models.LocarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocarRepository extends JpaRepository<LocarModel, Integer> {

    public Optional<LocarModel> findByIdLivro(int idLivro);
    public Optional<LocarModel> findByIdUsuario(int id);

    public boolean existsByIdLivro(int idLivro);

    public boolean existsByIdUsuario(int id);

    public void deleteByIdUsuario(int id);
}
