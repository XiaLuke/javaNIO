package cn.xf.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MyFile {
    public static void main(String[] args) {
        Path path = Paths.get("C:\\Users\\XF\\Desktop\\ss");
        try {
            Path directories = Files.createDirectories(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
