package com.pagsestagio.movieapi.service;

import com.pagsestagio.movieapi.model.FilmeOutbox;
import com.pagsestagio.movieapi.repository.FilmeOutboxRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OutboxConnector {

    private final FilmeOutboxRepository filmeOutboxRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public OutboxConnector(FilmeOutboxRepository filmeOutboxRepository, KafkaTemplate<String, String> kafkaTemplate) {
        this.filmeOutboxRepository = filmeOutboxRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedRate = 5000)
    public void processOutboxEvents() {
        List<FilmeOutbox> outboxEvents = filmeOutboxRepository.findAllByProcessadoFalse();
        for (FilmeOutbox event : outboxEvents) {
            try {
                kafkaTemplate.send(event.getTopicoDestino(), event.getKey(), event.getPayload());
                event.setProcessado(true);
                filmeOutboxRepository.save(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}