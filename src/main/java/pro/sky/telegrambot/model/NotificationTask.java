package pro.sky.telegrambot.model;

import javax.persistence.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class NotificationTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private LocalDateTime notificationDataTime;
    private String task;

    public NotificationTask() {
    }

    public NotificationTask(Long id, Long userId, LocalDateTime dateTime, String task) {
        this.id = id;
        this.userId = userId;
        this.notificationDataTime = dateTime;
        this.task = task;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getNotificationDataTime() {
        return notificationDataTime;
    }

    public void setNotificationDataTime(LocalDateTime notificationDataTime) {
        this.notificationDataTime = notificationDataTime;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationTask that = (NotificationTask) o;
        return Objects.equals(id, that.id) && Objects.equals(userId, that.userId) && Objects.equals(notificationDataTime, that.notificationDataTime) && Objects.equals(task, that.task);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, notificationDataTime, task);
    }
}
