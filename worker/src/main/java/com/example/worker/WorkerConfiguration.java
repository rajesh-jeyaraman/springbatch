package com.example.worker;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.integration.config.annotation.EnableBatchIntegration;
import org.springframework.batch.integration.partition.RemotePartitioningWorkerStepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.amqp.rabbit.core.RabbitTemplate;


import lombok.extern.slf4j.Slf4j;


@Configuration
@Slf4j
@Profile("!master")
@EnableBatchIntegration
public class WorkerConfiguration {

    @Autowired
    private RemotePartitioningWorkerStepBuilderFactory workerStepBuilderFactory;


    @Bean
    public DirectChannel requests() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow inboundFlow(ConnectionFactory connectionFactory) {
    	log.info("CREATING INBOUNDFLOW");
        return IntegrationFlows.from(Amqp.inboundAdapter(connectionFactory, "requests")).channel(requests()).get();
    }

    @Bean
    public DirectChannel replies() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow outboundFlow(AmqpTemplate amqpTemplate) {
    	log.info("CREATING OUTBOUNDFLOW");
        return IntegrationFlows.from(replies()).handle(Amqp.outboundAdapter(amqpTemplate).routingKey("replies")).get();
    }


    @Bean
    public Step workerStep() {
        System.out.println("INSIDE WORKER STEP");
        return workerStepBuilderFactory.get("workerStep").inputChannel(requests()).outputChannel(replies()).chunk(5)
                .reader(getReader()).writer(getWriter()).build();
    }


    @Bean
    @StepScope
    public TestReader getReader() {
        return new TestReader();
    }

    @Bean
    @StepScope
    public TestWriter getWriter() {
        return new TestWriter();
    }

}
