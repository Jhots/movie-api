package com.pagsestagio.movieapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "MOVIE_OUTBOX")
public class FilmeOutbox {

    public FilmeOutbox(Long id, String key, String payload, LocalDateTime criadoEm, String topicoDestino, boolean processado) {
        this.id = id;
        this.key = key;
        this.payload = payload;
        this.criadoEm = criadoEm;
        this.topicoDestino = topicoDestino;
        this.processado = processado;
    }

    public FilmeOutbox() {

    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OUTBOX_ID")
    private Long id;

    @Column(name = "OUTBOX_KEY")
    private String key;

    @Column(name = "OUTBOX_PAYLOAD")
    private String payload;

    @Column(name = "OUTBOX_CREATED_AT")
    private LocalDateTime criadoEm;

    @Column(name = "OUTBOX_TARGET_TOPIC")
    private String topicoDestino;

    @Column(name = "OUTBOX_PROCESSED")
    private boolean processado;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public String getTopicoDestino() {
        return topicoDestino;
    }

    public void setTopicoDestino(String topicoDestino) {
        this.topicoDestino = topicoDestino;
    }

    public boolean isProcessado() {
        return processado;
    }

    public void setProcessado(boolean processado) {
        this.processado = processado;
    }
}
