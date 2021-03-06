package com.hgicreate.rno.lte.structanls.tool;

import java.io.IOException;
import java.nio.file.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipFileHandler {
    private static final int buffer = 1024;

    public static boolean unZip(String zipFilePath, String outputDirectory) {
        if (!Files.exists(Paths.get(zipFilePath))) {
            return false;
        }
        try (ZipFile zipFile = new ZipFile(zipFilePath)) {
            for (Enumeration<? extends ZipEntry> entries = zipFile.entries(); entries.hasMoreElements(); ) {
                ZipEntry zipEntry = entries.nextElement();
                if (zipEntry.isDirectory()) {
                    Files.createDirectories(Paths.get(outputDirectory, zipEntry.getName()));
                } else {
                    Path path = Paths.get(outputDirectory, zipEntry.getName());
                    Files.createDirectories(path.getParent());
                    Files.copy(zipFile.getInputStream(zipEntry), path, StandardCopyOption.REPLACE_EXISTING);
                }
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static void zip(String targetPath, String resourcesPath) {
        Path resourcesFile = Paths.get(resourcesPath);
        Path targetFile = Paths.get(targetPath, resourcesFile.getFileName() + ".zip");
        zip(targetFile, resourcesFile);
    }

    /**
     * 将源文件/文件夹生成指定格式的压缩文件,格式zip
     */
    public static void zip(Path targetFile, Path... resources) {
        if (Files.notExists(targetFile)) {
            try {
                Files.createDirectories(targetFile.getParent());
                Files.createFile(targetFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (ZipOutputStream out = new ZipOutputStream(Files.newOutputStream(targetFile, StandardOpenOption.TRUNCATE_EXISTING))) {
            add2ZipFile(out, "", resources);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void add2ZipFile(ZipOutputStream out, String dir, Path... files) throws IOException {
        //如果当前的是文件夹，则进行进一步处理
        if (files != null) {
            for (Path file : files) {
                Path subFile = Paths.get(dir, file.getFileName().toString());
                if (Files.isDirectory(file)) {
                    String subDir = subFile.toString() + "/";
                    //将文件夹添加到下一级打包目录
                    out.putNextEntry(new ZipEntry(subDir));
                    // 递归处理,将文件夹中的文件打包,不用再做非空判断。
                    add2ZipFile(out, subDir, Files.list(file).toArray(Path[]::new));
                } else {
                    //当前的是文件，打包处理
                    out.putNextEntry(new ZipEntry(subFile.toString()));
                    //文件输入流
                    try {
                        Files.copy(file, out);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
