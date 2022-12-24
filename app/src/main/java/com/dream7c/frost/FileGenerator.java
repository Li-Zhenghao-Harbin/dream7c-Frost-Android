package com.dream7c.frost;

import java.io.File;
import java.io.RandomAccessFile;

public class FileGenerator {
    public void initData() {
        String filePath = MainActivity.filePath;
        String fileName = MainActivity.targetProjectName + MainActivity.fileSuffix;
        writeTxtToFile(filePath, fileName);
    }

    //Generate file content
    private void writeTxtToFile(String filePath, String fileName) {
        //Transfer
        String strContent = ProjectActivity.content;
        //Fill
        makeFilePath(filePath, fileName);
        String strFilePath = filePath + fileName;
        try {
            File file = new File(strFilePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();
        } catch (Exception e) {

        }
    }

    //Create file
    private File makeFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();

        } catch (Exception e) {

        }
        return file;
    }

    //Create directory
    private static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {

        }
    }
}
