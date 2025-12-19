package com.bunary.vocab.wordset.event;

import org.springframework.context.ApplicationEvent;

import com.bunary.vocab.wordset.dto.event.ActorEventDTO;
import com.bunary.vocab.wordset.dto.event.WordSetEventDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WordsetCreatedEvent extends ApplicationEvent {
    private final ActorEventDTO actor;
    private final WordSetEventDTO wordSet;

    public WordsetCreatedEvent(Object source, WordSetEventDTO wordSet, ActorEventDTO actor) {
        super(source);
        this.wordSet = wordSet;
        this.actor = actor;
    }

}
