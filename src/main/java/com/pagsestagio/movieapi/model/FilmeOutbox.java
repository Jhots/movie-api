package com.pagsestagio.movieapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name="MOVIE_OUTBOX")
public class FilmeOutbox {

    public FilmeOutbox(Long id, String tipoEvento, String payload, LocalDateTime criadoEm, boolean processado) {
        this.id = id;
        this.tipoEvento = tipoEvento;
        this.payload = payload;
        this.criadoEm = criadoEm;
        this.processado = processado;
    }

    public FilmeOutbox() {

    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OUTBOX_ID")
    private Long id;

    @Column(name = "OUTBOX_EVENT_TYPE")
    private String tipoEvento;

    @Column(name = "OUTBOX_PAYLOAD")
    private String payload;

    @Column(name = "OUTBOX_CREATED_AT")
    private LocalDateTime criadoEm;

    @Column(name = "OUTBOX_PROCESSED")
    private boolean processado;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }

    public boolean isProcessado() {
        return processado;
    }

    public void setProcessado(boolean processado) {
        this.processado = processado;
    }
}
