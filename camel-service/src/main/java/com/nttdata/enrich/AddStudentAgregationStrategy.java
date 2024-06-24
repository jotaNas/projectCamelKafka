package com.nttdata.enrich;

import com.nttdata.model.Student;
import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.apache.camel.RuntimeCamelException;


import java.util.ArrayList;
import java.util.List;

public class AddStudentAgregationStrategy implements AggregationStrategy {

    public static final String LISTA_ESTUDANTES = "listaEstudantes";

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        try {
            // Inicializa a lista de estudantes a ser agregada
            List<Student> listaEstudantes = getListaEstudantes(oldExchange);

            // Processa o novo estudante do exchange atual
            Student novoEstudante = newExchange.getIn().getBody(Student.class);
            listaEstudantes.add(novoEstudante);

            // Atualiza a lista de estudantes no exchange original
            oldExchange.getIn().setHeader(LISTA_ESTUDANTES, listaEstudantes);
        } catch (Exception e) {
            throw new RuntimeCamelException(e);
        }

        return oldExchange;
    }

    private List<Student> getListaEstudantes(Exchange exchange) {
        List<Student> listaEstudantes = exchange.getIn().getHeader(LISTA_ESTUDANTES, List.class);
        if (listaEstudantes == null) {
            listaEstudantes = new ArrayList<>();
        }
        return listaEstudantes;
    }
}