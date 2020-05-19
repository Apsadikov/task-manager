package ru.itis.taskmanager.service;

import ru.itis.taskmanager.dto.MailDto;

public interface EmailService {
    void send(MailDto mail);
}
