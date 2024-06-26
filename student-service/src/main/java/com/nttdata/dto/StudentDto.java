package com.nttdata.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;
import java.util.Date;

@Getter
@Setter
public class StudentDto {
    @CPF(message = "CPF inválido")
    private String cpf;
    @NotBlank(message = "Nome não pode estar em branco")
    private String nome;
    @Past(message = "Data de nascimento precisa ser no passado")
    private Date dataNascimento;
}
