package com.nttdata.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Student {
    private String nome;
    private Date dataNascimento;
    private String cpf;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dataFormatada = (dataNascimento != null) ? sdf.format(dataNascimento) : "null";
        return "Student{" +
                "nome='" + nome + '\'' +
                ", dataNascimento=" + dataFormatada +
                ", cpf='" + cpf + '\'' +
                '}';
    }

}

