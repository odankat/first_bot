package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.apache.naming.factory.SendMailFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;
import pro.sky.telegrambot.service.ReturnMassage;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);


    private final TelegramBot telegramBot;
    private final ReturnMassage returnMassage;
    private final NotificationTaskRepository notificationTaskRepository;
    private final Pattern NOTIFICATION_MASSAGE_FORMAT = Pattern.compile("(\\d{2}\\.\\d{2}\\.\\d{4}\\s\\d{2}:\\d{2})(\\s+)(.+)");
    private final DateTimeFormatter NOTIFICATION_DATA_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public TelegramBotUpdatesListener(TelegramBot telegramBot, ReturnMassage returnMassage, NotificationTaskRepository notificationTaskRepository) {
        this.telegramBot = telegramBot;
        this.returnMassage = returnMassage;
        this.notificationTaskRepository = notificationTaskRepository;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            Long userId = update.message().chat().id();
            String massageUser = update.message().text();
            // Process your updates here
            try {
                if (massageUser.equals("/start")) {
                    returnMassage.requestStart(userId, massageUser);
                } else {
                    Matcher massageMather = NOTIFICATION_MASSAGE_FORMAT.matcher(massageUser);
                    if (
                            massageMather.matches()) {
                        NotificationTask notificationTask = new NotificationTask();
                        notificationTask.setUserId(userId);
                        notificationTask.setTask(massageMather.group(3));
                        notificationTask.setDateTime(LocalDateTime.parse(
                                massageMather.group(1),
                                NOTIFICATION_DATA_TIME_FORMATTER
                        ));
                        notificationTaskRepository.save(notificationTask);
                    }
                }
            } catch (NullPointerException e) {
                System.out.println("пытался");
            }


        });

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

}
