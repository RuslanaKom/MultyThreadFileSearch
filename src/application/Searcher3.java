package application;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.scene.control.ProgressBar;

public class Searcher3 {

    private double totalDirectoriesFound=0;
    private double directoriesSearched = 0;
    private double progressPercents=0;
    private int numberOfTheads = 4;

    private static final String NOT_FOUND_MESSAGE = "No file %s found";

    private static final String FILE_FOUND_MESSAGE = "File found in %s";
    private ExecutorService executor;

    public void searchInDirectory(String directoryName, String fileName, List<String> results) throws InterruptedException {
    	results.clear();
    	executor = Executors.newFixedThreadPool(numberOfTheads);
        Stack<File> stack = new Stack<File>();
        File dir = new File(directoryName);
        stack.push(dir);
        Iterator<File> iterator = stack.iterator();
        while (!stack.isEmpty()) {
            directoriesSearched += stack.size();
            CountDownLatch latch = new CountDownLatch(stack.size());
            List<Stack> listOfstacks = new ArrayList<>();
            while (iterator.hasNext()) {
                File file = stack.pop();
                if (file.list() != null) {
                    Runnable run1 = () -> {
                       // System.out.println(Thread.currentThread());
                        Stack<File> stack1 = new Stack<File>();
                        Stack<File> stackResult = new Stack<File>();
                        stack1.addAll(Arrays.asList(file.listFiles()));
                        Iterator<File> iterator1 = stack1.iterator();
                        while (iterator1.hasNext()) {
                            File child = stack1.pop();
                            if (getFileNameWithoutExtension(child).equals(fileName)) {
                                    results.add(String.format(FILE_FOUND_MESSAGE, child.getAbsolutePath()));
                                }
                                else if (child.list() != null) {
                                    stackResult.push(child);
                                    }
                                }
                        listOfstacks.add(stackResult);
                        latch.countDown();
                    };
                    executor.execute(run1);
                }
            }
            latch.await();
            stack.clear();
            for(Stack stack1:listOfstacks) {
               stack.addAll(stack1); 
            }
            totalDirectoriesFound = directoriesSearched + stack.size();
            double previousProgress = progressPercents;
            progressPercents=directoriesSearched/totalDirectoriesFound;
            progressPercents=progressPercents>previousProgress? progressPercents : previousProgress;
            System.out.println("Searched: " + directoriesSearched);
            System.out.println("Total found: " + totalDirectoriesFound);
            System.out.println(progressPercents);
           // pb.setProgress(progressPercents*100);
        }
        executor.shutdown();
        if(results.isEmpty()) {
            results.add(String.format(NOT_FOUND_MESSAGE, fileName));
        }
    }
    
    private String getFileNameWithoutExtension(File file) {
        String originalName = file.getName();
        if(!originalName.contains(".")) {
            return originalName;
        }
        int indexOfDot = originalName.indexOf('.');
        String nameWithoutExtension = originalName.substring(0, indexOfDot);
        return nameWithoutExtension;
    }

    public double getProgressPercents() {
        return progressPercents;
    }

    public void setProgressPercents(double progressPercents) {
        this.progressPercents = progressPercents;
    }
    
}

