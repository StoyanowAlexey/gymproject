package org.example.controllers;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.repositories.GymSeasonTicketRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final GymSeasonTicketRepository gymSeasonTicketRepository;

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleDuplicateDataExceptionSync(DataIntegrityViolationException ex, Model model) {
        // перевіряємо текст помилки, щоб відобразити правильне повідомлення
        if (ex.getMessage().contains("email_unique")) {
            model.addAttribute("errorMessage", "❌ Person with this email already exists!");
        } else if (ex.getMessage().contains("phone_number_unique")) {
            model.addAttribute("errorMessage", "❌ Person with this phone number already exists!");
        } else {
            model.addAttribute("errorMessage", "❌ Person with this telegram already exists!");
        }

        model.addAttribute("seasonTickets", gymSeasonTicketRepository.findAll());

        return "add-person";
    }

}