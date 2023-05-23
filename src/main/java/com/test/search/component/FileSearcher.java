package com.test.search.component;

import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


@Component
public class FileSearcher {

    private final ExecutorService executorService;

    public FileSearcher() {
        this.executorService = Executors.newCachedThreadPool();
    }

    public List<File> searchFiles(String directoryPath, String fileNamePart) throws InterruptedException, ExecutionException {
        List<File> foundFiles = new ArrayList<>();
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            throw new IllegalArgumentException("Directory " + directoryPath + " does not exist");
        }
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directoryPath + " is not a directory");
        }
        File[] allFiles = directory.listFiles();
        List<Future<List<File>>> futures = new ArrayList<>();
        assert allFiles != null;
        for (File file : allFiles) {
            if (file.isFile() && file.getName().contains(fileNamePart)) {
                foundFiles.add(file);
            } else if (file.isDirectory()) {
                futures.add(executorService.submit(() -> searchFiles(file.getAbsolutePath(), fileNamePart)));
            }
        }
        for (Future<List<File>> future : futures) {
            foundFiles.addAll(future.get());
        }
        return foundFiles;
    }

}
