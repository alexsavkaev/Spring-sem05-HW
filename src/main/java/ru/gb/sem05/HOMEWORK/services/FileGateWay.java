package ru.gb.sem05.HOMEWORK.services;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.file.FileHeaders;
import org.springframework.messaging.handler.annotation.Header;
@MessagingGateway(defaultRequestChannel = "textInputChannel")

public interface FileGateWay {
    void writeToFile(@Header(FileHeaders.FILENAME) String fileName, String content);
}
