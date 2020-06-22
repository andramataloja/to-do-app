package com.todoapp.todoapp.service;

import com.todoapp.todoapp.domain.twilio.TwilioClient;
import com.todoapp.todoapp.repository.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MessageService {

    @Value("${twilio.phone_number.to}")
    private String phone_number_to;

    @Autowired
    private TodoRepository todoRepository;

    public void executeSendMessage() {

        if (getMessageText().size() > 0) {
            new TwilioClient().sendMessage(phone_number_to, customMessage());
        } else {
            return;
        }
    }

    String customMessage() {
        String messagePrefix = "Hey, you have uncompleted tasks to complete this week: ";
        String result = getMessageText().stream()
                .map(n -> String.valueOf(n))
                .collect(Collectors.joining(", "));
        return messagePrefix + result;
    }

    public List<String> getMessageText() {
        return todoRepository.findByUncompleted();
    }
}
