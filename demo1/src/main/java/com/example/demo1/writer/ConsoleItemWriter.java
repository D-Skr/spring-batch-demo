package com.example.demo1.writer;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.support.AbstractItemStreamItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;


public class ConsoleItemWriter extends AbstractItemStreamItemWriter {
    @Override
    public void write(List<Integer> items) throws Exception {
        items.stream().forEach(System.out::println);
        System.out.println("******** writing each chunk ********");
    }



}
