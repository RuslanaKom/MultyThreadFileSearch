package test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.Test;

import application.SearcherWithThreads;

public class SearchTest2 {
    
    //"C:/Users/rkomaristova/Desktop/searchHere"
    @Test
    public void shouldFindFile() {
        long startTime = System.currentTimeMillis();

        //ExecutorService executor = Executors.newFixedThreadPool(1);
        
        ExecutorService executor = Executors.newWorkStealingPool(10);
        
        SearcherWithThreads searcher = new SearcherWithThreads();
        
        List<String> resultList = new ArrayList<>();
        searcher.setSearchParameters("C:/Users/rkomaristova/Desktop/searchHere", "target.txt", resultList, executor);
        
        executor.execute(searcher);
        //searcher.run();
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        
        resultList.stream().forEach(s->System.out.println(s)); 
        System.out.println(elapsedTime);
        assertThat(resultList.size(), is(not(0)));
    }

}
