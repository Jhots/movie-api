package com.pagsestagio.movieapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "MOVIE_STATISTIC")
public class FilmeEstatistica {

    @Id
    @Column(name = "PUBLIC_ID")
    @JdbcTypeCode(Types.VARCHAR)
    private UUID idPublico;

    @Column(name = "MOVIE_NAME")
    private String nomeFilme;

    @Column(name = "SEARCH_COUNTER")
    private Integer contadorBuscas;

    public FilmeEstatistica(UUID idPublico, String nomeFilme, Integer contadorBuscas) {
        this.idPublico = idPublico;
        this.nomeFilme = nomeFilme;
        this.contadorBuscas = contadorBuscas;
    }

    public FilmeEstatistica() {

    }

    public UUID getIdPublico() {
        return idPublico;
    }

    public void setIdPublico(UUID idPublico) {
        this.idPublico = idPublico;
    }

    public String getNomeFilme() {
        return nomeFilme;
    }

    public void setNomeFilme(String nomeFilme) {
        this.nomeFilme = nomeFilme;
    }

    public Integer getContadorBuscas() {
        return contadorBuscas;
    }

    public void setContadorBuscas(Integer contadorBuscas) {
        this.contadorBuscas = contadorBuscas;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        FilmeEstatistica that = (FilmeEstatistica) o;
        return Objects.equals(idPublico, that.idPublico) && Objects.equals(nomeFilme, that.nomeFilme) && Objects.equals(contadorBuscas, that.contadorBuscas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPublico, nomeFilme, contadorBuscas);
    }
}
