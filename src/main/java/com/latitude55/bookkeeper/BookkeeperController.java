package com.latitude55.bookkeeper;

import com.latitude55.bookkeeper.service.BookkeeperService;
import org.apache.avro.SchemaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import scala.Array;
import scala.Int;

import java.util.HashMap;
import java.util.Map;

@RestController
public class BookkeeperController {

    @Autowired
    private BookkeeperService service;

    @RequestMapping("/predict")
    public Map<Integer,Integer> Predict() {
        HashMap<Integer,Integer> result = new HashMap<>();
        scala.Tuple2<java.lang.Object, java.lang.Object>[] predictions = service.getPredictions();
        for (scala.Tuple2<java.lang.Object,java.lang.Object> pair:
             predictions) {
            result.put((int) pair._1(), (int) pair._2());
        }
        return result;
    }
}
