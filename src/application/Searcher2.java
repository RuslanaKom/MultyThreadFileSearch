package application;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Searcher2 {

    private String startDirectory;

    private String fileName;

    private List<String> results;

    private static final String NOT_FOUND_MESSAGE = "No file found in selected directory ";

    private static final String FILE_FOUND_MESSAGE = "File found in %s";
    private ExecutorService executor = Executors.newFixedThreadPool(3);

    public void searchInDirectory(String directoryName, String fileName, List<String> results) {
        Stack<File> stack = new Stack<File>();
        File dir = new File(directoryName);
        stack.push(dir);

        while (!stack.isEmpty()) {
            if (stack.size()>1) {
                Stack<File> stack1=(Stack<File>) stack.subList(0, stack.size()/2);
                Stack<File> stack2=(Stack<File>) stack.subList(stack.size()/2, stack.size());
                Runnable run1 = () -> {
                System.out.println(Thread.currentThread());
                Iterator<File> iterator1 = stack1.iterator();
                    while (iterator1.hasNext()) {
                        File parent = stack1.pop();
                        for (File child : parent.listFiles()) {
                            if (child.getName().equals(fileName)) {
                                results.add(String.format(FILE_FOUND_MESSAGE, child.getAbsolutePath()));
                            }
                            else {
                                if (child.list() != null) {
                                    stack1.push(child);
                                }
                            }
                        }
                    }
                };
            
                Runnable run2 = () -> {
                    System.out.println(Thread.currentThread());
                    Iterator<File> iterator2 = stack2.iterator();
                        while (iterator2.hasNext()) {
                            File parent = stack2.pop();
                            for (File child : parent.listFiles()) {
                                if (child.getName().equals(fileName)) {
                                    results.add(String.format(FILE_FOUND_MESSAGE, child.getAbsolutePath()));
                                }
                                if (child.list() != null) {
                                    stack2.push(child);
                                }
                            }
                        }
                    };
            
                executor.execute(run1);
                executor.execute(run2);
                stack.clear();
                stack.addAll(stack1);
                stack.addAll(stack2);
            }
            else {
                Iterator<File> iterator1 = stack.iterator();
                while (iterator1.hasNext()) {
                    File parent = stack.pop();
                    for (File child : parent.listFiles()) {
                        if (child.getName().equals(fileName)) {
                            results.add(String.format(FILE_FOUND_MESSAGE, child.getAbsolutePath()));
                        }
                        else {
                            if (child.list() != null) {
                                stack.push(child);
                            }
                        }
                    }
                } 
            }

        }
    }
}

/*Runnable run = () -> {
                    System.out.println(Thread.currentThread());
                    for (File f : child.listFiles()) {
                        if (f.getName().equals(fileName)) {
                            results.add(String.format(FILE_FOUND_MESSAGE, f.getAbsolutePath()));
                        }
                        else {
                            stack.push(f);
                        }
                    }
                };*/
