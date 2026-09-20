package com.loremipsum.demo;

import org.springframework.jdbc.core.JdbcTemplate;
import java.time.Instant;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


@Controller
public class MessageController {

    private JdbcTemplate jdbcTemplate;
    private final SimpMessagingTemplate messagingTemplate;

    public MessageController(JdbcTemplate aTemplate, SimpMessagingTemplate aMessagingTemplate){
        this.jdbcTemplate=aTemplate;
        this.messagingTemplate = aMessagingTemplate;
    }

    // @GetMapping ("/api/messages")
    // public List<Messages> getAll(){

    //     String sql = " SELECT * FROM messages ";

    //     return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
    //         Messages message=new Messages();
    //         message.setId(resultSet.getLong("id"));
    //         message.setSenderId(resultSet.getLong("sender_id"));
    //         message.setReceiverId(resultSet.getLong("receiver_id"));
    //         message.setContent(resultSet.getString("content"));
    //         message.setDate(resultSet.getString("date"));
    //         return message;
    //     });
    // }

    // @GetMapping ("/api/messages/{id}")
    // public Messages getById(@PathVariable Long id){

    //     String sql = "SELECT * FROM messages WHERE id = ?";

    //     List<Messages> messageList = jdbcTemplate.query(sql, (resultSet, rowNum) -> {
    //         Messages message = new Messages();
    //         message.setId(resultSet.getLong("id"));
    //         message.setSenderId(resultSet.getLong("sender_id"));
    //         message.setReceiverId(resultSet.getLong("receiver_id"));
    //         message.setContent(resultSet.getString("content"));
    //         message.setDate(resultSet.getString("date"));
    //         return message;
    //     }, id);

    //     if(messageList.isEmpty()){
    //         return null;
    //     }
    //     else{
    //         return messageList.get(0);
    //     }
    // }

    // @PostMapping ("/api/messages")
    // public String createMessage(@RequestBody Messages content){

    //     String sql = "INSERT INTO messages (sender_id, receiver_id, content, date ) VALUES (?, ?, ?, ?)";

    //     jdbcTemplate.update(sql, content.getSenderId(), content.getReceiverId(), content.getContent(), content.getDate());

    //     return "Message created successfully";
    // }

    // @DeleteMapping ("/api/messages/{id}")
    // public String deleteMessage(@PathVariable Long id){

    //     String sql = "DELETE FROM messages WHERE id = ?";

    //     jdbcTemplate.update(sql, id);

    //     return "Message deleted successfully";
    // }

    @MessageMapping("/chat.send")
    public void sendMessage(@Payload Message aMessage){
        String sql = "INSERT INTO messages (sender_id, receiver_id, content, date) VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(sql, aMessage.getSenderId(), aMessage.getReceiverId(), aMessage.getContent(), Instant.now().toString());
        messagingTemplate.convertAndSendToUser(
            String.valueOf(aMessage.getReceiverId()),
            "/queue/messages",
            aMessage
        );

    }
}
