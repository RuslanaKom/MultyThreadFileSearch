package test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import application.Searcher;
import application.Searcher2;

public class Search2Test {
    
    @Test
    public void shouldFindFile() {
        long startTime = System.currentTimeMillis();
        Searcher2 searcher = new Searcher2();
        
        List<String> resultList = new ArrayList<>();
        searcher.searchInDirectory("C:/Users/rkomaristova/Desktop", "target.txt", resultList);
        
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        
        resultList.stream().forEach(s->System.out.println(s)); 
        System.out.println(elapsedTime);
        assertThat(resultList.size(), is(not(0)));
    }

}
