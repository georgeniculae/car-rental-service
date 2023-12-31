package com.carrental.cloudgateway.config.kafka.consumer;

import com.carrental.cloudgateway.service.UserService;
import com.carrental.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.ReceiverRecord;
import reactor.util.retry.Retry;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class KafkaSavedUserConsumerConfig {

    private final ReactiveKafkaConsumerTemplate<String, UserDto> savedUserReactiveKafkaConsumerTemplate;
    private final UserService userService;

    @EventListener(ApplicationStartedEvent.class)
    public Disposable startKafkaSavedUserConsumer() {
        return savedUserReactiveKafkaConsumerTemplate.receive()
                .doOnError(error -> log.error("Error receiving event, will retry", error))
                .retryWhen(Retry.fixedDelay(Long.MAX_VALUE, Duration.ofMinutes(1)))
                .doOnNext(record -> log.debug("Received event: key {}", record.key()))
                .concatMap(this::handleEvent)
                .subscribe(record -> record.receiverOffset().acknowledge());
    }

    private Mono<ReceiverRecord<String, UserDto>> handleEvent(ReceiverRecord<String, UserDto> record) {
        return Mono.just(record)
                .doOnNext(this::logReceivedRecordValue)
                .map(ConsumerRecord::value)
                .flatMap(userService::saveUser)
                .doOnError(e -> log.warn("Error processing event: key {}", record.key(), e))
                .onErrorResume(e -> {
                    log.error("Error while processing message: {}", e.getMessage());

                    return Mono.empty();
                })
                .then(Mono.just(record));
    }

    private void logReceivedRecordValue(ReceiverRecord<String, UserDto> record) {
        log.info("UserDto received: " + record.value());
    }

}
