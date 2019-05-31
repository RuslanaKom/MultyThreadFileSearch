package application;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Searcher {

    private static final String NOT_FOUND_MESSAGE = "No file found in selected directory ";

    private static final String FILE_FOUND_MESSAGE = "File %s found in directory %s";


    public void searchInDirectory(String directoryName, String fileName, List<String> results) {
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
            searchInDirectory(folder.getAbsolutePath(), fileName, results);
        }
    }
}
