package ru.mihena.VKBot.core;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EnableScheduling
@Component
@Slf4j
public class ScheduleEngine {

    private List<Path> list;
    @Autowired
    private ResponseManager responseManager;

    @Scheduled(cron = "0 0 8-17 * * 3")
    @Async
    public void doWednesday() {
        if(this.list == null) initList();
        responseManager.forceSendById("It is Wednesday. mY dudes",new File(String.valueOf(list.get((int)(1+Math.random()*list.size()-1)))),2000000001);
    }

    public List<Path> getList() {
        return this.list;
    }


    public void initList() {
        try (Stream<Path> paths = Files.walk(Paths.get("wednesday"))) {
            this.list = paths
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            log.error(e.toString());
        }
    }
}
