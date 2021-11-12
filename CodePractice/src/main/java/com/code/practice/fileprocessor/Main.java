package com.code.practice.fileprocessor;

import java.nio.file.Path;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {

    public static void main(String[] args) {

        BlockingQueue<Path> files = new ArrayBlockingQueue<>(100);

        FileConsumer consumer = FileConsumer.getInstance(files);
        DirectoryWatcher watcher = DirectoryWatcher.getDirectoryWatcher(files);

        new Thread(watcher).start();
        new Thread(consumer).start();

    }
}
