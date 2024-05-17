package com.pagsestagio.movieapi.model;

public class FilmeDTOV1 {
    private Integer idLegado;
    private String nomeFilme;

    public FilmeDTOV1(Integer idLegado, String nomeFilme) {
        this.idLegado = idLegado;
        this.nomeFilme = nomeFilme;
    }

    public Integer getIdLegado() {
        return idLegado;
    }

    public void setIdLegado(Integer idLegado) {
        this.idLegado = idLegado;
    }

    public String getNomeFilme() {
        return nomeFilme;
    }

    public void setNomeFilme(String nomeFilme) {
        this.nomeFilme = nomeFilme;
    }
}