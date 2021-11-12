package com.code.practice.fileprocessor;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class FileConsumer implements Runnable {

    private static FileConsumer instance;
    private BlockingQueue<Path> queue;

    private FileConsumer() {
    }

    private FileConsumer(BlockingQueue<Path> queue) {
        this.queue = queue;
    }

    public static FileConsumer getInstance(BlockingQueue<Path> queue) {
        if (instance == null)
            instance = new FileConsumer(queue);
        return instance;
    }

    BlockingQueue<Path> files = new ArrayBlockingQueue<>(100);

    Path processedPath = FileSystems.getDefault().getPath("D:\\work\\workspace\\CodePractice\\src\\main\\resources\\data\\processed");

    private void process(Path path) {

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            System.out.println("The data for file " + path.getFileName() + ": ");
            while ((line = reader.readLine()) != null) {

                System.out.println(line);
            }
        } catch (IOException ioException) {
            System.out.println("IOException (File read)" + this.getClass().getName() + ": " + ioException);
        }

        try {
            Files.copy(path, processedPath.resolve(path.getFileName()), StandardCopyOption.COPY_ATTRIBUTES);
            System.out.println("File is processed!!");
        } catch (IOException ioe) {
            System.out.println("IOException (File copy)" + this.getClass().getName() + ": " + ioe);
        }

    }

    @Override
    public void run() {
        try {
            process(queue.take());
        } catch (InterruptedException ie) {
            System.out.println("InterruptedException in " + this.getClass().getName() + ": " + ie);
        }
    }
}
