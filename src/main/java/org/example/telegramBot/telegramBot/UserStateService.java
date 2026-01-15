package org.example.telegramBot.telegramBot;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserStateService {
    private final Map<Long, String> states = new ConcurrentHashMap<>();

    public String getState(Long chatId) {
        return states.getOrDefault(chatId, "IDLE");
    }

    public void setState(Long chatId, String state) {
        states.put(chatId, state);
    }

    public void clear(Long chatId) {
        states.remove(chatId);
    }
}
