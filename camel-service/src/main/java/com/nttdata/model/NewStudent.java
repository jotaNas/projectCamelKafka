package com.nttdata.model;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.io.Serializable;
import java.time.LocalDate;

@RegisterForReflection
public class NewStudent implements Serializable {
    private String cpf;
    private String nome;
    private LocalDate dataNascimento;

    public NewStudent() {
    }

    public NewStudent(String cpf, String nome, LocalDate dataNascimento) {
        this.cpf = cpf;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }


}
