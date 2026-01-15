package org.example.telegramBot.telegramBot;

import org.example.entities.GymVisitor;
import org.springframework.stereotype.Component;

@Component
public class TelegramMessageFormatter {

    public String formatGymVisitor(GymVisitor person) {
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
