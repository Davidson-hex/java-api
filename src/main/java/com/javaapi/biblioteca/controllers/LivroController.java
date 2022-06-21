package com.javaapi.biblioteca.controllers;

import com.javaapi.biblioteca.dtos.LivroDto;
import com.javaapi.biblioteca.dtos.LocarDto;
import com.javaapi.biblioteca.dtos.views.LivroView;
import com.javaapi.biblioteca.exceptions.ObjectNotFoundException;
import com.javaapi.biblioteca.models.LivroModel;
import com.javaapi.biblioteca.models.MovimentosModel;
import com.javaapi.biblioteca.services.LivroService;
import com.javaapi.biblioteca.services.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/livros")
public class LivroController {

    final LivroService livroService;
    final TokenService tokenService;


    public LivroController(LivroService livroService, TokenService tokenService) {
        this.livroService = livroService;
        this.tokenService = tokenService;
    }

    @PostMapping
    public Object postLivro(@RequestBody @Valid LivroDto livroDto) {
        var livro = livroService.criarLivro(livroDto);
        return livro;
    }
    @GetMapping("/{id}")
    public LivroView getUmLivro(@PathVariable(value = "id") Integer id) {
        var livro = livroService.umLivro(id);
        return livro;
    }

    @GetMapping("/todos")
    public ResponseEntity<List<LivroDto>> getLivros() {
        var livros = livroService.getLivros();
        return ResponseEntity.status(HttpStatus.OK).body(livros);
    }

    @GetMapping("/inativar/{id}")
    public Object inativarLivro(@PathVariable(value = "id") Integer id) {
        var inativar = livroService.inativarLivro(id);
        return inativar;
    }
    @GetMapping("/ativar/{id}")
    public Object ativarLivro(@PathVariable(value = "id") Integer id) {
        var ativar = livroService.ativarLivro(id);
        return ativar;
    }
    @GetMapping("/locado")
    public ResponseEntity<Object> livroAlugado() {
        Optional livroAlugado = livroService.livroAlugado();
        var livro = livroAlugado.get();
        if (livroAlugado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum livro");
        }
        return ResponseEntity.status(HttpStatus.OK).body(livro);
    }

    @GetMapping("/getLocado")
    public ResponseEntity<Boolean> getLivroAlugado() {
        Optional livroAlugado = livroService.livroAlugado();
        var livro = livroAlugado.get();
        if (livroAlugado.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }

    @PostMapping("/locar/{id}")
    public Object locarLivro(@PathVariable(name = "id") Integer id, @RequestBody @Valid LocarDto locarDto) throws ParseException {
        var locar = livroService.locarLivro(id, locarDto);
        return locar;
    }

    @GetMapping("/devolver/{id}")
    public Object devolver(@PathVariable(name = "id") Integer id) {
        var devolver = livroService.devolverLivro(id);
        return devolver;
    }

    @GetMapping("/umhistorico")
    public  ResponseEntity<Object> umHistorico() {
        Integer idUsuario = Math.toIntExact(tokenService.getClaim());
        Object movimentosModels = livroService.umHistorico(idUsuario);
        return ResponseEntity.status(HttpStatus.OK).body(movimentosModels);
    }

    @GetMapping("/historico")
    public ResponseEntity<List<MovimentosModel>> historico() {
        return ResponseEntity.status(HttpStatus.OK).body(livroService.historico());
    }
}
