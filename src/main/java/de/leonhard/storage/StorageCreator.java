package de.leonhard.storage;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class StorageCreator {
    private File json;
    private FileType fileType;

    /**
     * Creates an empty .yml or .json file.
     * @param path Absolute path where the file should be created
     * @param name Name of the file
     * @param fileType .yml/.json
     * @throws IOException Exception thrown if file could not be created.
     */

    synchronized void create(String path, String name, FileType fileType) throws IOException {
        this.fileType = fileType;
        if (path == null) {
            json = new File(name + fileType.getExtension());
        } else {
            path = path.replace("\\", "/");
            ArrayList<String> parts = new ArrayList<>(Arrays.asList(path.split("/")));
            StringBuilder datafolder = new StringBuilder();

            for (String part : parts) {
                datafolder.append(part + "/");//TODO Test for Windows Compatibility
                /*
                Instead of \ \\.
                 */
            }
            File folders = new File(datafolder.toString());
            if (!folders.exists()) {
                folders.mkdirs();
            }

            json = new File(path + File.separator + name + fileType.getExtension());
        }

        if (!json.exists()) {
            json.createNewFile();
        }
    }

    public File getJson() {
        return json;
    }

    public FileType getFileType() {
        return fileType;
    }
}
