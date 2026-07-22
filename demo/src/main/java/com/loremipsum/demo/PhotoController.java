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
public class PhotoController {
    
    private final JdbcTemplate jdbcTemplate; 

    public PhotoController(JdbcTemplate aTemplate){
        this.jdbcTemplate = aTemplate;
    }

    @GetMapping("/api/photos")
    public List<Photo> getAll(){
        String sql = "SELECT * FROM photos";

        return jdbcTemplate.query(sql, (resultset, rownum) -> {
            Photo photo = new Photo();
            photo.setId(resultset.getLong("id"));
            photo.setUserId(resultset.getLong("user_id"));
            photo.setUrl(resultset.getString("url"));
            photo.setDate(resultset.getString("date"));
            return photo;
        });
    }

    @GetMapping("/api/photos/{id}")
    public Photo getById(@PathVariable Long id){
        String sql = "SELECT * FROM photos WHERE id = ?";

        List<Photo> photoList = jdbcTemplate.query(sql, (resultset, rownum) -> {
            Photo photo = new Photo();
            photo.setId(resultset.getLong("id"));
            photo.setUserId(resultset.getLong("user_id"));
            photo.setUrl(resultset.getString("url"));
            photo.setDate(resultset.getString("date"));
            return photo;
        }, id);

        if(photoList.isEmpty()){
            return null;
        }

        else{
            return photoList.get(0);
        }
    }

    @PostMapping("/api/photos")
    public String createPhoto(@RequestBody Photo thePhoto){
        String sql = "INSERT INTO photos (user_id, url, date) VALUES (?, ?, ?)";

        jdbcTemplate.update(sql, thePhoto.getUserId(), thePhoto.getUrl(), thePhoto.getDate());
        return "yay u created photo";
    }

    @DeleteMapping("/api/photos/{id}")
    public String deletePhoto(@PathVariable Long id){
        String sql = "DELETE FROM photos WHERE id = ?";

        jdbcTemplate.update(sql, id);
        return "deleted photo";
    }
    
}
