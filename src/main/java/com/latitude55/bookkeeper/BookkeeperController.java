package com.latitude55.bookkeeper;

import com.latitude55.bookkeeper.service.BookkeeperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookkeeperController {

    @Autowired
    private BookkeeperService service;

    @RequestMapping("/predict")
    public scala.Tuple2<java.lang.Object,java.lang.Object>[] Predict() {
        return service.getPredictions();
    }
}
