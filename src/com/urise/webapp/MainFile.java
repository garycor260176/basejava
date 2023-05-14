package com.urise.webapp;

import java.io.File;

public class MainFile {
    public static void main(String[] args) {
        File catalog = new File("C:/java/basejava/src/com/urise/webapp");
        outputListDir(catalog, 1);
    }

    public static void outputListDir(File catalog, int level) {
        File[] files = catalog.listFiles();
        if (files == null) return;
        String off = "";
        for (int i = 0; i < level; i++) off += " ";
        for (File file : files) {
            if (file.isDirectory()) {
                System.out.println(off + "Dir:" + file.getName());
                outputListDir(file, level + 2);
            } else if (file.isFile()) {
                System.out.println(off + "File:" + file.getName());
            }
        }
    }
}
