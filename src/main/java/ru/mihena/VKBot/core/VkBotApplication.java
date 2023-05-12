package ru.mihena.VKBot.core;

import api.longpoll.bots.exceptions.VkApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableJpaRepositories("ru.mihena.VKBot.repositories")
@EntityScan("ru.mihena.VKBot.model")

@ComponentScan("ru.mihena.VKBot")
@SpringBootApplication
@EnableAutoConfiguration
@EnableScheduling
@EnableAsync
@Slf4j
public class VkBotApplication implements ApplicationRunner {

	@Autowired
	private VKBot bot;

    public static void main(String[] args) {
		SpringApplication.run(VkBotApplication.class, args);
	}
	@Override
	public void run(ApplicationArguments args) {
			try {
				bot.startPolling();
			} catch (VkApiException e) {
				log.error(e.toString());
			}
	}
}
