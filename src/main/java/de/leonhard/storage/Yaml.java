package de.leonhard.storage;

import java.io.IOException;
import java.util.List;

public class Yaml extends StorageCreator implements StorageBase {

    public Yaml(String name, String path){
        try {
            create(path, name, FileType.YAML);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String getString(String key) {
        return null;
    }

    @Override
    public long getLong(String key) {
        return 0;
    }

    @Override
    public int getInt(String key) {
        return 0;
    }

    @Override
    public byte getByte(String key) {
        return 0;
    }

    @Override
    public boolean getBoolean(String key) {
        return false;
    }

    @Override
    public float getFloat(String key) {
        return 0;
    }

    @Override
    public double getDouble(String key) {
        return 0;
    }

    @Override
    public List<?> getList(String key) {
        return null;
    }

    @Override
    public List<String> getStringList(String key) {
        return null;
    }

    @Override
    public List<Integer> getIntegerList(String key) {
        return null;
    }

    @Override
    public List<Byte> getByteList(String key) {
        return null;
    }

    @Override
    public List<Long> getLongList(String key) {
        return null;
    }

    @Override
    public void set(String key, Object value) {
//TODO SETTER Einfügen.
    }
}
