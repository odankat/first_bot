package pro.sky.telegrambot.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.service.ReturnMassage;
@Component
public class ReturnMassageImpl implements ReturnMassage {
    private final TelegramBot telegramBot;

    public ReturnMassageImpl(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public void requestStart(Long userId, String text) {
        SendMessage massage = new SendMessage(userId, "курлык");
        telegramBot.execute(massage);

    }
}
