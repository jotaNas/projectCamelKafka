package com.nttdata.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import jakarta.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.test.junit.QuarkusTest;

import com.nttdata.dto.StudentDto;
import com.nttdata.exception.DuplicateCpfException;
import com.nttdata.model.Student;
import com.nttdata.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;

@QuarkusTest
public class StudentServiceTest {

//    @Inject
//    StudentService studentService;
//
//    @Inject
//    StudentRepository repository;
//
//    @Inject
//    ModelMapper modelMapper;
//
//    private Student student;
//    private StudentDto studentDto;
//
//    @BeforeEach
//    public void setUp() {
//        student = new Student();
//        student.setId(1L);
//        student.setNome("Test Student");
//        student.setCpf("12345678901");
//
//        studentDto = new StudentDto();
//        studentDto.setNome("Test Student");
//        studentDto.setCpf("12345678901");
//    }
//
//    @Test
//    public void testListAll() {
//        // Simula a consulta usando PanacheQuery simulado pelo Quarkus
//        PanacheQuery<Student> query = repository.findAll();
//        query.page(Page.ofSize(10));
//        query.firstResult();
//
//        // Mapeia os resultados para DTO
//        List<StudentDto> result = studentService.listAll(0, 10);
//
//        assertEquals(1, result.size());
//        assertEquals("Test Student", result.get(0).getNome());
//    }
//
//    @Test
//    public void testAddStudent() throws DuplicateCpfException {
//        when(repository.cpfExists(anyString())).thenReturn(false);
//        StudentDto addedStudentDto = studentService.addStudent(studentDto);
//        assertNotNull(addedStudentDto.getCpf());
//
//        verify(repository, times(1)).persist(any(Student.class));
//    }
//
//    @Test
//    public void testAddStudentDuplicateCpf() {
//        when(repository.cpfExists(anyString())).thenReturn(true);
//
//        assertThrows(DuplicateCpfException.class, () -> studentService.addStudent(studentDto));
//    }
//
//    @Test
//    public void testFindByName() {
//        List<Student> students = new ArrayList<>();
//        students.add(student);
//
//        when(repository.findByName(anyString())).thenReturn(students);
//
//        List<StudentDto> result = studentService.findByName("Test Student");
//
//        assertEquals(1, result.size());
//        assertEquals("Test Student", result.get(0).getNome());
//    }
//
//    @Test
//    public void testFindByCpf() {
//        when(repository.findByCpf("12345678901")).thenReturn(student);
//
//        StudentDto result = studentService.findByCpf("12345678901");
//
//        assertEquals("Test Student", result.getNome());
//    }
//
//    @Test
//    public void testFindByCpfNotFound() {
//        when(repository.findByCpf("12345678901")).thenReturn(null);
//
//        assertThrows(EntityNotFoundException.class, () -> studentService.findByCpf("12345678901"));
//    }
//
//    @Test
//    public void testFindById() {
//        when(repository.findById(1L)).thenReturn(student);
//
//        StudentDto result = studentService.findById(1L);
//
//        assertEquals("Test Student", result.getNome());
//    }
//
//    @Test
//    public void testFindByIdNotFound() {
//        when(repository.findById(1L)).thenReturn(null);
//
//        assertThrows(EntityNotFoundException.class, () -> studentService.findById(1L));
//    }
//
//    @Test
//    public void testUpdateStudent() {
//        when(repository.findById(1L)).thenReturn(student);
//
//        studentService.updateStudent(1L, studentDto);
//
//        verify(repository, times(1)).persist(any(Student.class));
//    }
//
//    @Test
//    public void testUpdateStudentNotFound() {
//        when(repository.findById(1L)).thenReturn(null);
//
//        assertThrows(EntityNotFoundException.class, () -> studentService.updateStudent(1L, studentDto));
//    }
//
//    @Test
//    public void testDeleteStudent() {
//        when(repository.findById(1L)).thenReturn(student);
//
//        studentService.deleteStudent(1L);
//
//        verify(repository, times(1)).delete(any(Student.class));
//    }
//
//    @Test
//    public void testDeleteStudentNotFound() {
//        when(repository.findById(1L)).thenReturn(null);
//
//        assertThrows(EntityNotFoundException.class, () -> studentService.deleteStudent(1L));
//    }
}

