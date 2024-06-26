package com.nttdata;

import com.nttdata.model.Student;
import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CamelRouter extends RouteBuilder {
    private static final Logger logger = LoggerFactory.getLogger(CamelRouter.class);
    ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @Override
    public void configure() throws Exception {
        String kafkaBootstrap = "kafka:9092";
        String kafkaTopic = "studentsTopic";

        from("file:{{students.in.folder}}?antInclude=*.csv&move={{students.out.folder}}")
                .routeId("studentsRoute")
                .unmarshal().csv()
                .split(body())
                .streaming()
                .parallelProcessing().streaming().executorService(threadPool)
                .process(exchange -> {
                    String[] row = exchange.getIn().getBody(String[].class);

                    // Verificação para ignorar o cabeçalho
                    if ("nome".equalsIgnoreCase(row[0]) && "dataNascimento".equalsIgnoreCase(row[1]) && "CPF".equalsIgnoreCase(row[2])) {
                        logger.info("Ignoring header row: {}", (Object) row);
                        return;
                    }

                    if (row.length < 3) {
                        logger.error("Row does not have enough columns: {}", (Object) row);
                        return;
                    }

                    Student student = new Student();
                    student.setNome(row[0].trim());

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date dataNascimento = sdf.parse(row[1].trim());
                        student.setDataNascimento(dataNascimento);
                    } catch (ParseException e) {
                        logger.error("Failed to parse date: {}", row[1], e);
                        return;
                    }

                    student.setCpf(row[2].trim());
                    exchange.getIn().setBody(student);
                })
                .choice()
                .when(exchangeProperty("isHeader").isEqualTo(true))
                .log("Header row skipped.")
                .otherwise()
                .to(ExchangePattern.InOnly, "seda:processarEstudante")
                .log("Header row skipped.")
                .marshal().json()
                .to("kafka:" + kafkaTopic + "?brokers=" + kafkaBootstrap);

    }
}