package pro.sky.telegrambot.spam;

import org.apache.logging.log4j.message.Message;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;
import pro.sky.telegrambot.service.ReturnMassage;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

@Component
public class Spam {
    private final NotificationTaskRepository notificationTaskRepository;
    private final ReturnMassage returnMassage;

    public Spam(NotificationTaskRepository notificationTaskRepository, ReturnMassage returnMassage) {
        this.notificationTaskRepository = notificationTaskRepository;
        this.returnMassage = returnMassage;
    }

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
    public void startSpam() {
        LocalDateTime localDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        var notificationTasks = notificationTaskRepository.findAllByNotificationDataTime(localDateTime);
        for (NotificationTask notificationTask : notificationTasks) {
            returnMassage.requestStart(
                    notificationTask.getUserId(),
                    "привет" + notificationTask.getTask());
        }

    }
}
