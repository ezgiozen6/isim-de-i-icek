package com.loremipsum.demo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class NotificationsController {
    private JdbcTemplate jdbcTemplate;

    public NotificationsController(JdbcTemplate aTemplate){
        this.jdbcTemplate = aTemplate;
    }

    @GetMapping("/api/notifications")
    public List<Notifications> getAll(){

        String sql = "SELECT * FROM notifications";

        return jdbcTemplate.query (sql, (resultSet, rowNum) -> {
            Notifications notification= new Notifications();
            notification.setId(resultSet.getLong("id"));
            notification.setUserId(resultSet.getLong("user_id"));
            notification.setNotification(resultSet.getString("notification"));
            notification.setIsRead(resultSet.getBoolean("is_read"));
            return notification;
        });
    }

    @GetMapping("/api/notifications/{id}")
    public Notifications getById(@PathVariable Long id){

        String sql = "SELECT * FROM notifications WHERE id= ?";

        List<Notifications> notifList = jdbcTemplate.query (sql, (resultSet,rowNum) -> {
            Notifications notification= new Notifications();
            notification.setId(resultSet.getLong("id"));
            notification.setUserId(resultSet.getLong("user_id"));
            notification.setNotification(resultSet.getString("notification"));
            notification.setIsRead(resultSet.getBoolean("is_read"));
            return notification;
        }, id);

        if(notifList.isEmpty()){
            return null;
        }
        else{
            return notifList.get(0);
        }
    }

    @PutMapping("/api/notifications/{id}")
    public String updateIsRead(@PathVariable Long id, @RequestBody Notifications update){

        String sql = "UPDATE notifications SET is_read = ? WHERE id = ?";

        jdbcTemplate.update(sql, update.getIsRead(), id);

        return "Updated read situation successfully";
    }

    @PostMapping("/api/notifications")
    public String createNotification(@RequestBody Notifications notification){

        String sql = "INSERT INTO notifications (user_id, notification, is_read) VALUES (?, ?, ?)";

        jdbcTemplate.update(sql, notification.getUserId(),notification.getNotification(),notification.getIsRead());

        return "Notification created successfully";
    }

    @DeleteMapping("/api/notifications/{id}")
    public String deleteNotification(@PathVariable Long id){

        String sql = "DELETE FROM notifications WHERE id= ?";

        jdbcTemplate.update(sql, id);

        return "Notification deleted successfully";
    }
}
