package ru.gb.sem05.HOMEWORK.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.GenericTransformer;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.messaging.MessageChannel;

import java.io.File;

@Configuration
public class IntegrationConfig {
    @Bean
    public MessageChannel textInputChannel() {
        return new DirectChannel();
    }
    @Bean
    public MessageChannel fileOutputChannel() {
        return new DirectChannel();
    }
    @Bean
    @Transformer(inputChannel = "textInputChannel", outputChannel = "fileOutputChannel")
    public GenericTransformer<String, String> fileTransformer() {
        return text -> {
            // do some transformation
            return text;
        };
    }

    @Bean
    @ServiceActivator(inputChannel = "fileOutputChannel")
    public FileWritingMessageHandler fileWriter() {
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File(
                "src/main/java/ru/gb/sem05/HOMEWORK/testOutput"));
        handler.setExpectReply(false);;
        handler.setFileExistsMode(FileExistsMode.APPEND);
        handler.setAppendNewLine(true);

        return handler;
    }


}
