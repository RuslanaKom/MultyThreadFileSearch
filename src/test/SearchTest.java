package test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import application.Searcher;

public class SearchTest {
    
    @Test
    public void shouldFindFile() {
        long startTime = System.currentTimeMillis();
        Searcher searcher = new Searcher();
        
        List<String> resultList = new ArrayList<>();
        searcher.searchInDirectory("C:/Users/rkomaristova/Desktop/searchHere", "target.txt", resultList);
        
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        
        resultList.stream().forEach(s->System.out.println(s)); 
        System.out.println(elapsedTime);
        assertThat(resultList.size(), is(not(0)));
    }

}
