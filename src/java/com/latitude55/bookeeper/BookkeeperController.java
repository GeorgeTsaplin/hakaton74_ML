package com.latitude55.bookkeeper;

import com.latitude55.bookkeeper.service.BookkeeperService;
import org.apache.avro.SchemaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import scala.Array;
import scala.Int;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class BookkeeperController {
    /*
    @Autowired
    private BookkeeperService service;
    */

    @RequestMapping("/predict")
    public Map<Integer,Integer> Predict() throws IOException {
        HashMap<Integer,Integer> result = new HashMap<>();
        /*
        scala.Tuple2<java.lang.Object, java.lang.Object>[] predictions = service.getPredictions();
        for (scala.Tuple2<java.lang.Object,java.lang.Object> pair:
             predictions) {
            result.put((int) pair._1(), (int) pair._2());
        }
        return result;
        */

        String row;
        BufferedReader csvReader = new BufferedReader(new FileReader("/Users/mikek/Downloads/dataset2.csv"));
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",");
            // do something with the data
            result.put(Integer.parseInt(data[1]),Integer.parseInt(data[2]));
        }
        csvReader.close();

        return result;
    }
}
