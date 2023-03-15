package com.example.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FirstItemWriter implements ItemWriter<Integer> {
    @Override
    public void write(List<? extends Integer> items) throws Exception {
        System.out.println("Inside Item Writer");
        items.stream().forEach(System.out::println);
    }
}
