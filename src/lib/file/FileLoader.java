package lib.file;

import java.io.File;
import java.util.ArrayList;

public final class FileLoader {
    private FileLoader() {}
    private String suffix;
    private ArrayList<File> files;

    public static ArrayList<File> getFileList(File path, String suffix) throws NullPointerException {
        FileLoader fileLoader = new FileLoader();
        fileLoader.suffix = suffix;
        fileLoader.files = new ArrayList<>();
        return fileLoader.getFileList(path);
    }

    private ArrayList<File> getFileList(File path) throws NullPointerException {
        reader(path);
        return files;
    }
    private void reader(File path) throws NullPointerException {
        if (path.isDirectory()) {
            for (File f : path.listFiles()) {
                if (f.isFile()) {
                    String str = f.getName();
                    if (str.substring(str.lastIndexOf('.')).equals(suffix)) {
                        files.add(f);
                    }
                } else {
                    reader(f);
                }
            }
        }
    }
}
