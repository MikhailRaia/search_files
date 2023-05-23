package com.test.search;

import com.test.search.component.FileSearcher;
import com.test.search.controller.FileController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class SearchApplication {


    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
        SpringApplication.run(SearchApplication.class, args);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter file name: ");
            String fileName = scanner.nextLine();
            if (fileName.equals("exit")){
                break;
            }
            System.out.print("Enter directory path: ");
            String directoryPath = scanner.nextLine();

            FileSearcher fileSearcher = new FileSearcher();
            List<File> foundFiles = fileSearcher.searchFiles(directoryPath, fileName);
            System.out.println("Found files: " + foundFiles);
            FileController fileController = new FileController();
            fileController.uploadFiles(foundFiles);
        }
    }

}
