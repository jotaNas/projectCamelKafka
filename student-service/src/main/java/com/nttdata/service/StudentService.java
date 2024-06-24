package com.nttdata.service;

import com.nttdata.dto.StudentDto;
import com.nttdata.repository.StudentRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import com.nttdata.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class StudentService {

    @Inject
    private StudentRepository repository;

    @Inject
    private ModelMapper modelMapper;

    private static final Logger log = LoggerFactory.getLogger(StudentService.class);

    public List<StudentDto> listAll(int page, int size) {
        PanacheQuery<Student> listOfStudents = repository.findAll();
        return listOfStudents.page(Page.of(page, size)).list().stream()
                .map(student -> modelMapper.map(student, StudentDto.class))
                .toList();
    }

    @Transactional
    public void addStudent(StudentDto createStudent) {
        Student student = modelMapper.map(createStudent, Student.class);
        repository.persist(student);
    }

    public List<StudentDto> findByName(String nome) {
        List<Student> students = repository.findByName(nome);
        return students.stream()
                .map(student -> modelMapper.map(student, StudentDto.class))
                .collect(Collectors.toList());
    }

    public StudentDto findByCpf(String cpf) {
        Student student = repository.find("cpf", cpf).firstResult();
        if (student != null) {
            return modelMapper.map(student, StudentDto.class);
        } else {
            throw new EntityNotFoundException("Não existe um estudante com o CPF: " + cpf);
        }
    }

    public StudentDto findById(Long id) {
        Student student = repository.findById(id);
        if (student != null) {
            return modelMapper.map(student, StudentDto.class);
        } else {
            throw new EntityNotFoundException("Não encontramos um estudante com o id: " + id);
        }
    }

    @Transactional
    public void updateStudent(Long id, StudentDto updateStudent) {
        Student existingStudent = repository.findById(id);
        if (existingStudent != null) {
            if (updateStudent.getNome() != null) {
                existingStudent.setNome(updateStudent.getNome());
            }
            if (updateStudent.getDataNascimento() != null) {
                existingStudent.setDataNascimento(updateStudent.getDataNascimento());
            }
            if (updateStudent.getCpf() != null) {
                existingStudent.setCpf(updateStudent.getCpf());
            }
            repository.persist(existingStudent);
        } else {
            throw new EntityNotFoundException("Não encontramos um estudante com o id: " + id);
        }
    }

    @Transactional
    public void deleteStudent(Long id) {
        Student existingStudent = repository.findById(id);
        if (existingStudent != null) {
            repository.delete(existingStudent);
        } else {
            throw new EntityNotFoundException("Não encontramos um estudante com o id: " + id);
        }
    }
}
