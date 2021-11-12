package com.code.practice.fileprocessor;

import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class DirectoryWatcher implements Runnable {

    static private DirectoryWatcher instance;
    private BlockingQueue<Path> queue;


    private DirectoryWatcher() {
    }

    private DirectoryWatcher(BlockingQueue<Path> queue) {
        this.queue = queue;
    }

    public static DirectoryWatcher getDirectoryWatcher(BlockingQueue<Path> queue) {
        if (instance == null)
            instance = new DirectoryWatcher(queue);

        return instance;
    }

    public void watch() {
        Path path = Paths.get("D:\\work\\workspace\\CodePractice\\src\\main\\resources\\data");


        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY);
            WatchKey w;
            while ((w = watchService.take()) != null) {
                for (WatchEvent<?> event : w.pollEvents()) {
//                    System.out.println("Event kind " + event.kind()
//                            + "Event context " + event.context());
                    Path filePath = (Path) event.context();
                    queue.put(path.resolve(filePath));

                }
                boolean isValid = w.reset();
                if (!isValid)
                    throw new RuntimeException("this object is no longer registered");
            }
        } catch (InterruptedException | IOException ie) {
            System.out.println("Exception in "+this.getClass().getName()+": " + ie);
        }
    }


    @Override
    public void run() {
        watch();
    }
}
