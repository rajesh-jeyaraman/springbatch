package com.example.demo;

import org.springframework.batch.core.*;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.batch.integration.partition.RemotePartitioningManagerStepBuilderFactory;
import org.springframework.batch.integration.partition.RemotePartitioningMasterStepBuilderFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.batch.core.partition.support.MultiResourcePartitioner;
import org.springframework.core.io.Resource;


@Configuration
@Slf4j
@Profile("master")
public class MasterConfigurations {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    
    @Autowired
    private RemotePartitioningManagerStepBuilderFactory managerStepBuilderFactory;

//    public MasterConfigurations(JobBuilderFactory jobBuilderFactory, RemotePartitioningManagerStepBuilderFactory masterStepBuilderFactory,JobRepository jobRepository) {
//        this.jobBuilderFactory = jobBuilderFactory;
//        this.managerStepBuilderFactory = masterStepBuilderFactory;
//        this.jobRepository=jobRepository;
//    }

    // outbound

    @Bean
    public Job remotePartitioningJob() {
        return this.jobBuilderFactory.get("remotePartitioningJob")
                .start(masterStep())
                .build();
    }

    @Bean
    public DirectChannel requests() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow outboundFlow(AmqpTemplate amqpTemplate) {
        return IntegrationFlows.from(requests()).handle(Amqp.outboundAdapter(amqpTemplate).routingKey("requests"))
                .get();
    }

    /*
     * Configure inbound flow (replies coming from workers)
     */
    @Bean
    public DirectChannel replies() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow inboundFlow(ConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Amqp.inboundAdapter(connectionFactory, "replies")).channel(replies()).get();
    }

//    @Bean
//    @StepScope
//    public ArraySplitPartitioner partitioner() {
//        log.info("Inside Partition");
//        ArraySplitPartitioner arraySplitPartitioner = new ArraySplitPartitioner();
//        return arraySplitPartitioner;
//    }
    @Bean
    @StepScope
    public MultiResourcePartitioner partitioner(
            @Value("#{jobParameters['inputFiles']}") Resource[] resources) {
    	
    	final String dir = System.getProperty("user.dir");
        System.out.println("CURRENTDIR = " + dir);
        
        MultiResourcePartitioner partitioner = new MultiResourcePartitioner();
        partitioner.setKeyName("file");
        partitioner.setResources(resources);
        return partitioner;
    }

//    @Bean
//    public Step masterStep() {
//        log.info("Inside masterStep()");
//        return this.managerStepBuilderFactory.get("masterStep").partitioner("workerStep", partitioner(null))
//                .outputChannel(requests()).inputChannel(replies()).build();
//    }
//    
    @Bean
    public Step masterStep() {
        return this.managerStepBuilderFactory.get("masterStep")
                .partitioner("workerStep", new BasicPartitioner())
                .gridSize(3)
                .outputChannel(requests())
                .inputChannel(replies())
                .build();
    }

}
