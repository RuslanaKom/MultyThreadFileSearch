package test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import application.Searcher3;
import javafx.scene.control.ProgressBar;

public class Search3Test {
    
    @Test
    public void shouldFindFile() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        Searcher3 searcher = new Searcher3();
        
        List<String> resultList = new ArrayList<>();
        searcher.searchInDirectory("C:/Users/Rusla/Desktop/F1", "file1", resultList);
        
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        
        resultList.stream().forEach(s->System.out.println(s)); 
        System.out.println(elapsedTime);
        assertThat(resultList.size(), is(not(0)));
    }

}
