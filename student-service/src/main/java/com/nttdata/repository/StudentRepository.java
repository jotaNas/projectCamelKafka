package com.nttdata.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import com.nttdata.model.Student;
import java.util.List;

@ApplicationScoped
public class StudentRepository implements PanacheRepository<Student> {
    public List<Student> findByName(String nome) {
        return find("LOWER(nome) like LOWER(?1)", "%" + nome.toLowerCase() + "%").list();
    }

    public Student findByCpf(String cpf) {
        return find("cpf", cpf).firstResult();
    }

    public boolean cpfExists(String cpf) {
        return find("cpf", cpf).firstResultOptional().isPresent();
    }
}

