package com.javaapi.biblioteca.services;

import com.javaapi.biblioteca.dtos.LivroDto;
import com.javaapi.biblioteca.dtos.LocarDto;
import com.javaapi.biblioteca.dtos.views.LivroView;
import com.javaapi.biblioteca.exceptions.ObjectNotFoundException;
import com.javaapi.biblioteca.models.LivroModel;
import com.javaapi.biblioteca.models.LocarModel;
import com.javaapi.biblioteca.models.MovimentosModel;
import com.javaapi.biblioteca.repositories.LivroRepository;
import com.javaapi.biblioteca.repositories.LocarRepository;
import com.javaapi.biblioteca.repositories.MovimentosRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LivroService {
    final LivroRepository livroRepository;
    final TokenService tokenService;
    final MovimentosRepository movimentosRepository;
    final LocarRepository locarRepository;

    @Autowired
    private ModelMapper modelMapper;

    final ResourceService resourceService;

    public LivroService(LivroRepository livroRepository, TokenService tokenService, MovimentosRepository movimentosRepository, LocarRepository locarRepository, ResourceService resourceService) {
        this.livroRepository = livroRepository;
        this.tokenService = tokenService;
        this.movimentosRepository = movimentosRepository;
        this.locarRepository = locarRepository;

        this.resourceService = resourceService;
    }
    public LivroModel save(LivroModel livroModel) {
        return livroRepository.save(livroModel);

    }

    public int returnIdUsuario() {
        return Math.toIntExact(tokenService.getClaim());
    }

    public LivroModel returnLivroModelOptional(int id) {
        return livroRepository.findById(id).orElseThrow(()-> new ObjectNotFoundException("Livro não encontrado"));
    }

    public MovimentosModel save(MovimentosModel movimentosModel) {
        return movimentosRepository.save(movimentosModel);
    }

    public LocarModel save(LocarModel locarModel) {
        return locarRepository.save(locarModel);
    }
    public List<LivroDto> getLivros() {

        var livros = livroRepository.findAll().stream()
                .map(this::livroModelToDto)
                .collect(Collectors.toList());
        return livros;
    }

    public LivroDto livroModelToDto(LivroModel livroModel) {
        return modelMapper.map(livroModel, LivroDto.class);
    }

    public LivroView umLivro(int id) {

        var livro = this.returnLivroModelOptional(id);
        return this.toLivroView(livro);
    }

    public LivroView toLivroView(LivroModel livroModel) {
        return modelMapper.map(livroModel, LivroView.class);
    }
    public Object inativarLivro(Integer id) {
        LivroModel livroModel = this.returnLivroModelOptional(id);
        Optional<LocarModel> locarModel = locarRepository.findByIdLivro(id);
        LocarModel locar = locarModel.orElse(new LocarModel());
        if (livroModel.getIdUsuarioCadastro() != this.returnIdUsuario()) {
            return resourceService.unauthorized("Você não possui permissão");
        }
        if (locar.getDevoluacao() != 1) {
            return resourceService.unauthorized("O livro está alugado no momento");
        }
        if (livroModel.getAtivo() != 1) {
            return resourceService.unauthorized("O livro já está inativo");
        }
        livroModel.setAtivo(0);
        this.save(livroModel);
        return resourceService.sucesso("Livro inativado com sucesso");
    }

    public Object ativarLivro(int id) {
        LivroModel livroModel = this.returnLivroModelOptional(id);
        if (livroModel.getIdUsuarioCadastro() != this.returnIdUsuario()) {
            return resourceService.unauthorized("Você não possui permissão");
        }
        livroModel.setAtivo(1);
        this.save(livroModel);
        return resourceService.sucesso("Livro ativado com sucesso");
    }

    public Optional<LocarModel> livroAlugado() {
        Long idUsuario = tokenService.getClaim();
        Optional<LocarModel> locarModelOptional = locarRepository.findByIdUsuario(Math.toIntExact(idUsuario));
        return locarModelOptional;
    }

    public Object locarLivro(int idLivro, LocarDto locarDto) throws ParseException {
        var existsLivro = livroRepository.existsById(idLivro);
        if (!existsLivro) {
            return resourceService.notFound("Livro não encontrado");
        }
        LivroModel livroModel = this.returnLivroModelOptional(idLivro);
        var movimentos = new MovimentosModel();
        var locarLivro = this.livroAlugado().orElse(new LocarModel());
        var livroLocado = locarRepository.findByIdLivro(idLivro).orElse(new LocarModel());
        if (livroModel.getAtivo() != 1) {
            return resourceService.unauthorized("O livro está inativo");
        }
        if (locarLivro.getDevoluacao() != 1) {
            return resourceService.unauthorized("Você já possui um livro alugado");
        }
        if (livroLocado.getDevoluacao() != 1) {
            return resourceService.unauthorized("O livro já está alugado");
        }
        var dataAtual = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyy/MM/dd");
        var previsaDevolucao = sdf.parse(locarDto.getPrevisao_devolucao());
        if (previsaDevolucao.before(dataAtual)) {
            return resourceService.unauthorized("A previsão de devolução está inválida");
        }
        locarLivro.setIdLivro(livroModel.getId());
        locarLivro.setPrevisaoDevolucao(previsaDevolucao);
        locarLivro.setIdUsuario(Math.toIntExact(tokenService.getClaim()));
        locarLivro.setStatus("ABERTO");
        locarLivro.setDevoluacao(0);
        locarLivro.setDataLocacao(LocalDateTime.now(ZoneId.of("UTC")));
        locarLivro.setLocacao(0);
        this.save(locarLivro);

        movimentos.setIdLivro(livroModel.getId());
        movimentos.setPrevisaoDevolucao(previsaDevolucao);
        movimentos.setIdUsuario(Math.toIntExact(tokenService.getClaim()));
        movimentos.setStatus("ABERTO");
        movimentos.setDevoluacao(0);
        movimentos.setDataLocacao(LocalDateTime.now(ZoneId.of("UTC")));
        movimentos.setLocacao(0);
        this.save(movimentos);

        return resourceService.sucesso("Livro locado com sucesso");
    }

    public Object devolverLivro(int idLivro) {
        var movimentos = movimentosRepository.findByStatusAndIdUsuario("ABERTO", this.returnIdUsuario());
        var isAlugado = locarRepository.existsByIdLivro(idLivro);
        var isAlugadoUser = locarRepository.existsByIdUsuario(this.returnIdUsuario());
        if (!isAlugado && !isAlugadoUser) {
            return resourceService.unauthorized("Você não locou este livro");
        }
        locarRepository.delete(locarRepository.findByIdLivro(idLivro).get());
        movimentos.setDataDevolucao(LocalDateTime.now(ZoneId.of("UTC")));
        movimentos.setStatus("CONCLUIDO");
        movimentos.setDevoluacao(1);
        movimentos.setLocacao(1);
        this.save(movimentos);
        return resourceService.sucesso("Livro devolvido com sucesso");
    }

    public Object umHistorico(int idUsuario) {
        return movimentosRepository.findAllByIdUsuario(idUsuario);
    }

    public List<MovimentosModel> historico() {
        return movimentosRepository.findAll();
    }

    public Object criarLivro(LivroDto livroDto) {
        var livroModel = new LivroModel();
        BeanUtils.copyProperties(livroDto, livroModel);
        livroModel.setDataCriacao(LocalDateTime.now(ZoneId.of("UTC")));
        livroModel.setAtivo(1);
        livroModel.setIdUsuarioCadastro(this.returnIdUsuario());
        this.save(livroModel);
        return resourceService.sucesso("Livro criado com sucesso");
    }
}


