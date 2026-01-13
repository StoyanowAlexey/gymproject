package org.example.telegramBot.telegramBot;

import lombok.AllArgsConstructor;
import org.example.entities.GymVisitor;
import org.example.repositories.people_repo.GymVisitorRepository;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

@Component
@AllArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {
    private final TelegramBotConfig botConfig;
    private final GymVisitorRepository gymVisitorRepository;
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
            String response = gymVisitorRepository.existsByPhoneNumber(phone) ?  formatGymPersonProfileForTelegram(gymVisitorRepository.findGymPersonByPhoneNumber(phone)) : "Хибний номер телефону!";
            sendMessage(chatId, response);
            return;
        }
            switch (text){
                case "/start" -> sendMenu(chatId);
                //case "Показати список юзерів" -> sendMessage(chatId, "Список : \n" + getAllPeople());
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


    // Метод для звичайних повідомлень
    private void sendMessage(String chatId, String text) {
        SendMessage message = new SendMessage(chatId, text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private String formatGymPersonProfileForTelegram(GymVisitor person) {
        StringBuilder sb = new StringBuilder();
        sb.append("👤 Профіль користувача\n\n");
        sb.append("ID: ").append(person.getId()).append("\n");
        sb.append("Ім'я: ").append(person.getName() == null ? "Не вказано" : person.getName()).append("\n");
        sb.append("Вік: ").append(person.getAge()).append("\n");
        sb.append("Стать: ").append(person.getGender() == null ? "Не вказано" : person.getGender()).append("\n");
        sb.append("Telegram: @").append(person.getTelegramAccount() == null || person.getTelegramAccount().isEmpty()
                ? "Не вказано" : person.getTelegramAccount()).append("\n");
        sb.append("Телефон: ").append(person.getPhoneNumber() == null || person.getPhoneNumber().isEmpty()
                ? "Не вказано" : "+380" + person.getPhoneNumber()).append("\n");
        sb.append("Email: ").append(person.getEmail() == null || person.getEmail().isEmpty()
                ? "Не вказано" : person.getEmail()).append("\n");
        sb.append("💳 Абонемент: ").append(person.getSeasonTicket() == null ? "Не вказано"
                : person.getSeasonTicket().getTicketType());
        return sb.toString();
    }
}
