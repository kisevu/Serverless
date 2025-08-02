package com.ameda.kevin.news_letter.functions;

import com.ameda.kevin.news_letter.model.Subscriber;
import com.ameda.kevin.news_letter.service.SubscriberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;

import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Author: kev.Ameda
 */

@Configuration
@Slf4j
public class Subscribers {

    private final SubscriberService subscriberService;

    public Subscribers(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    @Bean
    public Supplier<List<Subscriber>> findAll(){
        return subscriberService::findAll;
    }

    /*
    *  use of Supplier in the context of spring cloud stream,
    * then it automatically gets treated as a stream of messages ( continuous messages).
    *  The below setup will produce a message every second for that matter.
    *  We could introduce a poller functionality in the yaml file, and in my case i try to
    *  produce data after 5secs
    *
    * */

    @Bean
    public Supplier<Subscriber>  sendSubscribersInfo(){
        return () -> {
            Subscriber subscriber = Subscriber
                    .builder()
                    .email("amedakevin@gmail.com")
                    .build();
            log.info(" Sending :{}" ,subscriber.getEmail());
            return subscriber;
        };
    }

       /*
       * simulate the multiple topics scenario
       * */
        @Bean
        public Supplier<Message<String>>  sendSubscriberStatus(){
            Random random = new Random();
            return () -> {
               String status = random.nextBoolean() ? "subscription pending approval!"
                       : "subscription approved!";
               log.info(" Status : {}", status);
                return MessageBuilder.withPayload(status)
                        .setHeader(KafkaHeaders.KEY,status.getBytes())
                        .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.TEXT_PLAIN)
                        .build();
            };
        }

    /*
    * Below workings simulate the consumer scenario
    * */

    @Bean
    public Consumer<Subscriber> processSubscribersInfo(){
        return subscriber -> {
            log.info(" Received : {}", subscriber.getEmail());
        };
    }

    @Bean
    public Consumer<String> create(){
      return subscriberService::create;
    }
}
