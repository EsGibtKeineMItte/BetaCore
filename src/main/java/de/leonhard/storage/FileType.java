package de.leonhard.storage;

public enum FileType {
    JSON(".json"),
    YAML(".yml");


    private String extension;

    private FileType(String extension){
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }
}
