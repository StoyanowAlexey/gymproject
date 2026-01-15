package org.example.telegramBot.telegramBot;

import lombok.RequiredArgsConstructor;
import org.example.repositories.people_repo.GymVisitorRepository;
import org.example.service.gym_people.information_senders.EmailService;

import org.springframework.mail.MailException;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;


@Component
@RequiredArgsConstructor
public class TelegramUpdateHandler {
    private final TelegramMessageFormatter telegramMessageFormatter;
    private final GymVisitorRepository gymVisitorRepository;
    private final UserStateService userStateService;
    private final EmailService emailService;
    private final TelegramMessageService telegramMessageService;


    public void handler(Update update, TelegramBot telegramBot) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String chatId = update.getMessage().getChatId().toString();
            String text = update.getMessage().getText();
            Long longChatId = Long.valueOf(chatId);


            if("WAIT_PHONE".equals(userStateService.getState(longChatId))){
                String phone = text;
                userStateService.setState(Long.valueOf(chatId), "IDLE");
                String response = gymVisitorRepository.existsByPhoneNumber(phone) ?  telegramMessageFormatter.formatGymVisitor(gymVisitorRepository.findGymPersonByPhoneNumber(phone)) : "Хибний номер телефону!";
                telegramMessageService.sendMessage(telegramBot, longChatId, response);
                return;
            }
            if("WAIT_QUESTION".equals(userStateService.getState(longChatId))) {
                String messageWithMeta =
                        "ChatId: " + chatId + "\n\n" + text;

                userStateService.setState(longChatId, "IDLE");
                try{
                    emailService.sendEmail("rojbels@gmail.com", messageWithMeta, "TELEGRAM_QUESTION");
                    telegramMessageService.sendMessage(telegramBot, longChatId, "Повідомлення Відправленно✅");
                }
                catch (MailException mailException){
                    telegramMessageService.sendMessage(telegramBot, longChatId, mailException.getMessage());
                }
                text="/start";
            }

            switch (text){
                case "/start" -> telegramBot.sendMenu(chatId);
                //case "Показати список юзерів" -> sendMessage(chatId, "Список : \n" + getAllPeople());
                case "Інформація за номером телефона" -> {
                    telegramMessageService.sendMessage(telegramBot, longChatId, "Введіть номер телефону без (+380) ");
                    userStateService.setState(longChatId, "WAIT_PHONE");
                }
                case "Відправити питання" -> {
                    telegramMessageService.sendMessage(telegramBot, longChatId, "Введіть ваше питання, а ми його доставимо адміністратору😉");
                    userStateService.setState(Long.valueOf(chatId), "WAIT_QUESTION");
                }
                default -> telegramMessageService.sendMessage(telegramBot, longChatId, "Я тебе не зрозумів 🙃");
            }
        }


    }
}
