package com.br.mentorama.cadastroalunos;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/cadastroalunos")
public class AlunosController {

    private final List<Aluno> alunos;

    public AlunosController() {
        this.alunos = new ArrayList<>();
    }

    @GetMapping
    public List<Aluno> alunosList(@RequestParam(required = false) String nome, Integer idade) {
        if (nome != null) {
            return alunos.stream()
                    .filter(alns -> alns.getNome().equals(nome))
                    .collect(Collectors.toList());
        }
        if(idade != null) {
            return alunos.stream()
                    .filter(alns -> alns.getIdade().equals(idade))
                    .collect(Collectors.toList());
        }
        return alunos;
    }

    @GetMapping("/{id}")
    public Aluno findById(@PathVariable("id") Integer id) {
        return this.alunos.stream()
                .filter(alns -> alns.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    public ResponseEntity<Integer> addAluno(@RequestBody final Aluno aluno) {
        if(aluno.getId() == null) {
            aluno.setId(alunos.size() + 1);
        }
        alunos.add(aluno);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity updateAluno(@RequestBody final Aluno aluno) {
        alunos.stream()
                .filter(alns -> alns.getId(). equals(aluno.getId()))
                .forEach(alns -> alns.setNome(aluno.getNome()));
        alunos.stream()
                .filter(alns -> alns.getId().equals(aluno.getId()))
                .forEach(alns -> alns.setIdade(aluno.getIdade()));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity removeAluno(@PathVariable("id") Integer id) {
        alunos.removeIf(alns -> alns.getId().equals(id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
