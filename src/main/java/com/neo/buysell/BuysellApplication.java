package com.neo.buysell;

import com.neo.buysell.model.dto.CommentDTO;
import com.neo.buysell.model.entity.Comment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BuysellApplication {

    public static void main(String[] args) {
        SpringApplication.run(BuysellApplication.class, args);
    }

}
