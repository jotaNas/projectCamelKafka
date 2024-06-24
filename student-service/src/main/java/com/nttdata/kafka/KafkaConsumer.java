package com.nttdata.kafka;

import com.nttdata.dto.StudentDto;
import io.smallrye.common.annotation.Blocking;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import com.nttdata.service.StudentService;

@ApplicationScoped
public class KafkaConsumer {

    @Inject
    private StudentService service;

    @Incoming("studentsTopic")
    @Blocking
    public void consume(ConsumerRecord<String, String> record) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            StudentDto student = mapper.readValue(record.value(), StudentDto.class);
            service.addStudent(student);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


