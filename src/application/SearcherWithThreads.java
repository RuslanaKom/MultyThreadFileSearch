package application;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class SearcherWithThreads implements Runnable {
    
    
    private String startDirectory;
    private String fileName;
    private List<String> results;
    private ExecutorService executor;

    private static final String NOT_FOUND_MESSAGE = "No file found in selected directory ";

    private static final String FILE_FOUND_MESSAGE = "File %s found in directory %s";
    
    public void setSearchParameters(String directoryName, String fileName, List<String> results, ExecutorService executor) {
        this.startDirectory= directoryName;
        this.fileName=fileName;
        this.results=results;
        this.executor=executor;
    }

    public void searchInDirectory(String directoryName, String fileName, List<String> results, ExecutorService executor) {
        File dir = new File(directoryName);
        File[] childArray = dir.listFiles();

        if (childArray == null) {
            return;
        }
        List<File> children = Arrays.asList(childArray);
        
        List<File> files = children.stream().filter(f->f.list()==null).collect(Collectors.toList());
        List<File> folders = children.stream().filter(f->f.list()!=null).collect(Collectors.toList());

        for (File file : files) {
            if (file.getName().equals(fileName)) {
                results.add(String.format(FILE_FOUND_MESSAGE, fileName, dir.getAbsolutePath()));
            }
        }

        for (File folder : folders) {
            SearcherWithThreads searcher2 = new SearcherWithThreads();
            searcher2.setSearchParameters(folder.getAbsolutePath(), fileName, this.results, this.executor);
            //searcher2.run();
            executor.submit(searcher2);
        }
    }

    @Override
    public void run() {
        searchInDirectory(this.startDirectory, this.fileName, this.results, this.executor);
    }
}
