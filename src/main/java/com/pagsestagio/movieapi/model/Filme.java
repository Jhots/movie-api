package com.pagsestagio.movieapi.model;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;


@Entity
public class Filme {
    public Filme(Integer id, UUID idPublico, String nome) {
        this.id = id;
        this.idPublico = UUID.randomUUID();
        this.nome = nome;
    }

    public Filme() {
        this.idPublico = UUID.randomUUID();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private UUID idPublico;

    @Column(nullable = false)
    private String nome;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UUID getIdPublico() {
        return idPublico;
    }

    public void setIdPublico(UUID idPublico) {
        this.idPublico = idPublico;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Filme filme = (Filme) o;
        return Objects.equals(id, filme.id) && Objects.equals(idPublico, filme.idPublico) && Objects.equals(nome, filme.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idPublico, nome);
    }
}