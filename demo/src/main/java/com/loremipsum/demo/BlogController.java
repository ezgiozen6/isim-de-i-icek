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
public class BlogController {
    private JdbcTemplate jdbcTemplate;

    //constructor
    public BlogController(JdbcTemplate aJdbcTemplate){
        this.jdbcTemplate = aJdbcTemplate;
    }

    @GetMapping("/api/blogs")
    public List<Blog> getAll(){
        String sql = "SELECT * FROM blogs";

        return jdbcTemplate.query(sql, (resultset, rownum) -> {
            Blog blog = new Blog();
            blog.setId(resultset.getLong("id"));
            blog.setUserId(resultset.getLong("user_id"));
            blog.setContent(resultset.getString("content"));
            blog.setDate(resultset.getString("date"));
            return blog;
        });
    }

    @GetMapping("api/blogs/{id}")
    public Blog getById(@PathVariable Long id){
        String sql = "SELECT * FROM blogs WHERE id = ?";

        List<Blog> blogList = jdbcTemplate.query(sql, (resultset, rownum) -> {
            Blog blog = new Blog();
            blog.setId(resultset.getLong("id"));
            blog.setUserId(resultset.getLong("user_id"));
            blog.setContent(resultset.getString("content"));
            blog.setDate(resultset.getString("date"));
            return blog;
        }, id);

        return blogList.get(0);
    }

    @PostMapping("/api/blogs")
    public String create(@RequestBody Blog newBlog){
        String sql = "INSERT INTO blogs (user_id, content, date) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, newBlog.getUserId(), newBlog.getContent(), newBlog.getDate());
        return "blog created succesfully";
    }

    @DeleteMapping("api/blogs/{id}")
    public String delete(@PathVariable Long id){
        String sql = "DELETE FROM blogs WHERE id = ?";
        jdbcTemplate.update(sql, id);
        return "deleted photo";
    }

}
