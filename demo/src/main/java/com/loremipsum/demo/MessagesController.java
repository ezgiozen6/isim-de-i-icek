package com.loremipsum.demo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class MessagesController {

    private JdbcTemplate jdbcTemplate;

    public MessagesController(JdbcTemplate aTemplate){
        this.jdbcTemplate=aTemplate;
    }

    @GetMapping ("/api/messages")
    public List<Messages> getAll(){

        String sql = " SELECT * FROM messages ";

        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            Messages message=new Messages();
            message.setId(resultSet.getLong("id"));
            message.setSenderId(resultSet.getLong("sender_id"));
            message.setReceiverId(resultSet.getLong("receiver_id"));
            message.setContent(resultSet.getString("content"));
            message.setDate(resultSet.getString("date"));
            return message;
        });
    }

    @GetMapping ("/api/messages/{id}")
    public Messages getById(@PathVariable Long id){

        String sql = "SELECT * FROM messages WHERE id = ?";

        List<Messages> messageList = jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            Messages message = new Messages();
            message.setId(resultSet.getLong("id"));
            message.setSenderId(resultSet.getLong("sender_id"));
            message.setReceiverId(resultSet.getLong("receiver_id"));
            message.setContent(resultSet.getString("content"));
            message.setDate(resultSet.getString("date"));
            return message;
        });

        if(messageList.isEmpty()){
            return null;
        }
        else{
            return messageList.get(0);
        }
    }

    @PostMapping ("/api/messages")
    public String createMessage(@RequestBody Messages content){

        String sql = "INSERT INTO messages (sender_id, receiver_id, content, date ) VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(sql, content.getSenderId(), content.getReceiverId(), content.getContent(), content.getDate());

        return "Message created successfully";
    }

    @DeleteMapping ("/api/messages/{id}")
    public String deleteMessage(@PathVariable Long id){

        String sql = "DELETE FROM messages WHERE id = ?";

        jdbcTemplate.update(sql, id);

        return "Message deleted successfully";
    }
}
