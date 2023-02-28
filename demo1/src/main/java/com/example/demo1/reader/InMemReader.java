package com.example.demo1.reader;

import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.support.AbstractItemStreamItemReader;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class InMemReader  extends AbstractItemStreamItemReader {
    Integer[] arr = {1,2,3,4,5,6,7,8};
    List<Integer> list = Arrays.asList(arr);
    int index = 0;
    @Override
    public Object read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        Integer nextItem = null;
        if(index < list.size()){
            nextItem = list.get(index);
            index++;
        } else {
            return nextItem;
        }
        return nextItem;
    }
}
