package org.example.controllers;

import lombok.AllArgsConstructor;
import org.example.configs.telegram_bot.TelegramBotConfig;
import org.example.entities.GymPerson;
import org.example.repositories.GymPersonRepository;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {
    private final TelegramBotConfig botConfig;
    private final GymPersonRepository gymPersonRepository;
    private final Map<Long, String> userStates = new HashMap<>();
    private final Map<Long, String> tempPhoneNumber = new HashMap<>();

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String chatId = update.getMessage().getChatId().toString();
            String text = update.getMessage().getText();


        if("WAIT_PHONE".equals(userStates.get(Long.valueOf(chatId)))){
            String phone = text;
            tempPhoneNumber.put(Long.valueOf(chatId), phone);
            userStates.put(Long.valueOf(chatId), "IDLE");
            String response = gymPersonRepository.existsByPhoneNumber(phone) ? "нформація за номером телефона\n" + gymPersonRepository.getGymPersonByPhoneNumber(phone): "Хибний номер телефону!";
            sendMessage(chatId, response);
            return;
        }
            switch (text){
                case "/start" -> sendMenu(chatId);
                case "Показати моє ім’я" -> sendMessage(chatId, "Твоє ім’я: " + update.getMessage().getFrom().getFirstName());
                case "Показати список юзерів" -> sendMessage(chatId, "Список : \n" + getAllPeople());
                case "Інформація за номером телефона" -> {
                    sendMessage(chatId, "Введіть номер телефону без (+380) ");
                    userStates.put(Long.valueOf(chatId), "WAIT_PHONE");
                }
                default -> sendMessage(chatId, "Я тебе не зрозумів 🙃");
            }
        }
    }


    // Метод для відправки меню
    private void sendMenu(String chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Привіт 👋 Обери дію:");

        // Створюємо кнопки
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("Показати моє ім’я"));
        keyboard.add(row1);

        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton("Показати список юзерів"));
        keyboard.add(row2);

        KeyboardRow row3 = new KeyboardRow();
        row3.add(new KeyboardButton("Інформація за номером телефона"));
        keyboard.add(row3);

        keyboardMarkup.setKeyboard(keyboard);
        keyboardMarkup.setResizeKeyboard(true);

        message.setReplyMarkup(keyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private String getAllPeople(){
        List <GymPerson> gymPersonList = gymPersonRepository.findAll();
        return gymPersonList.stream()
                .map(GymPerson::toString)
                .collect(Collectors.joining("\n"));
    }

    // Метод для звичайних повідомлень
    private void sendMessage(String chatId, String text) {
        SendMessage message = new SendMessage(chatId, text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
