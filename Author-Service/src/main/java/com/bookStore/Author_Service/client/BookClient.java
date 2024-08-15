package com.bookStore.Author_Service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Service
@FeignClient(name = "${book-service.name}", url = "${book.url}")
public interface BookClient {

    //a blocker that made me think about my life   /{title}

    @GetMapping("getBookByTitle/{title}")
    ResponseEntity<Map<String, Object>> getBookByTitle(@PathVariable("title") String title);




}
