package com.ameda.kevin.news_letter.functions;

import com.ameda.kevin.news_letter.model.Subscriber;
import com.ameda.kevin.news_letter.service.SubscriberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Author: kev.Ameda
 */

@Configuration
public class Subscribers {

    private final SubscriberService subscriberService;

    public Subscribers(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    @Bean
    public Supplier<List<Subscriber>> findAll(){
        return subscriberService::findAll;
    }

    @Bean
    public Consumer<String> create(){
        return subscriberService::create;
    }
}
