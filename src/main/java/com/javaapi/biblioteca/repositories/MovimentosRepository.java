package com.javaapi.biblioteca.repositories;

import com.javaapi.biblioteca.models.MovimentosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovimentosRepository extends JpaRepository<MovimentosModel, Integer> {

    public Optional<MovimentosModel> findByIdUsuario(int id);

    public MovimentosModel findByIdLivro(int id);

    public MovimentosModel findByStatusAndIdUsuario(String status, int id);

    public boolean existsByIdUsuario(int id);

    public List<MovimentosModel> findAllByIdUsuario(int id);
}
