package de.leonhard.storage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import de.leonhard.util.JsonUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Json extends StorageCreator implements StorageBase {
    private final String path, name;
    private JSONObject object;
    private File file;


    /**
     * Creates a .json file where you can put your data in.
     *
     * @param name Name of the .json file
     * @param path Absolute path, where the .json file should be created.
     */

    public Json(String name, String path) {
        file = new File(path + File.separator + name + ".json");

        this.name = name;
        this.path = path;
        if (!file.exists()) {
            try {
                create(path, name, FileType.JSON);
                object = new JSONObject();
                Writer writer = new PrintWriter(new FileWriter(path + File.separator + name + ".json"));
                writer.write(object.toString(2));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException | NullPointerException e) {
            e.printStackTrace();
        }
        JSONTokener tokener = new JSONTokener(fis);
        object = new JSONObject(tokener);
    }

    private <T> T getNestedObject(String key) throws JSONException, NullPointerException {
        String[] parts = key.split("\\.");
        Map<?, ?> map = getMap(parts[0]);
        return (T) map.get(parts[1]);
    }


    @Override
    public String getString(String key) {
        try {
            if (key.contains(".")) {
                if(getNestedObject(key) != null){
                   return getNestedObject(key);
                }
                return "";
            }
            return object.getString(key);
        } catch (JSONException | NullPointerException e) {
            return ""; //TODO CHANGE?!
        }
    }


    @Override
    public long getLong(String key) {
        try {
            if (key.contains(".")) {
                return getNestedObject(key);
            }
            return object.getLong(key);
        } catch (JSONException | NullPointerException e) {
            return 0;
        }
    }

    @Override
    public int getInt(String key) {
        try {
            if (key.contains(".")) {
                return getNestedObject(key);
            }
            return object.getInt(key);
        } catch (JSONException | NullPointerException e) {
            return 0;
        }

    }

    @Override
    public byte getByte(String key) {
        try {
            if (key.contains(".")) {
                return getNestedObject(key);
            }

            return (byte) object.get(key);
        } catch (JSONException | NullPointerException e) {
            return 0;
        }
    }

    @Override
    public boolean getBoolean(String key) {

        try {
            if (key.contains(".")) {
                return getNestedObject(key);
            }
            return object.getBoolean(key);
        } catch (JSONException | NullPointerException e) {
            return false;
        }
    }

    @Override
    public float getFloat(String key) {
        if (key.contains(".")) {
            return getNestedObject(key);
        }
        try {
            return (float) object.get(key);
        } catch (JSONException e) {
            return 0;
        }
    }

    @Override
    public double getDouble(String key) {
        try {
            if (key.contains(".")) {
                return getNestedObject(key);
            }

            return object.getDouble(key);
        } catch (JSONException | NullPointerException e) {
            return 0;
        }
    }

    @Override
    public List<?> getList(String key) {
        try {
            if (key.contains(".")) {
                return getNestedObject(key);
            }

            Object object = this.object.get(key);
            JSONArray ja = new JSONArray(object.toString());
            List<Object> list = new ArrayList<>();
            for (int i = 0; i < ja.length(); i++) {
                list.add(ja.get(i));
            }
            return list;
        } catch (JSONException | NullPointerException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<String> getStringList(String key) {
        try {
            if (key.contains(".")) {
                return getNestedObject(key);
            }
            List<?> temp = getList(key);
            List<String> list = new ArrayList<>();
            for (Object o : temp) {
                if (o instanceof String) {
                    list.add((String) o);
                }
            }
            return list;
        } catch (JSONException | NullPointerException e) {
            return new ArrayList<String>();
        }

    }

    @Override
    public List<Integer> getIntegerList(String key) {
        try {
            if (key.contains(".")) {
                return getNestedObject(key);
            }

            List<?> temp = getList(key);
            List<Integer> list = new ArrayList<>();
            for (Object o : temp) {
                if (o instanceof Integer) {
                    list.add((Integer) o);
                } else if (o instanceof String) {
                    list.add(Integer.valueOf((String) o));
                }
            }
            return list;
        } catch (JSONException | NullPointerException e) {
            return new ArrayList<Integer>();
        }
    }

    @Override
    public List<Byte> getByteList(String key) {
        try {

            if (key.contains(".")) {
                return getNestedObject(key);
            }

            List<?> temp = getList(key);
            List<Byte> list = new ArrayList<>();
            for (Object o : temp) {
                if (o instanceof Byte) {
                    list.add((Byte) o);
                } else if (o instanceof String) {
                    list.add(Byte.valueOf((String) o));
                }
            }
            return list;
        } catch (JSONException | NullPointerException e) {
            return new ArrayList<Byte>();
        }
    }

    @Override
    public List<Long> getLongList(String key) {
        try {
            if (key.contains(".")) {
                return getNestedObject(key);
            }

            List<?> temp = getList(key);
            List<Long> list = new ArrayList<>();
            for (Object o : temp) {
                if (o instanceof Long) {
                    list.add((Long) o);
                } else if (o instanceof String) {
                    list.add(Long.valueOf((String) o));
                }
            }
            return list;
        } catch (JSONException | NullPointerException e) {
            return new ArrayList<Long>();
        }
    }

    public Map<?, ?> getMap(String key) {
        Object map;
        try {
            map = object.get(key);
        } catch (JSONException e) {
            return new HashMap<>();
        }
        if (map instanceof Map) {
            return (Map<?, ?>) object.get(key);
        } else if (map instanceof JSONObject) {
            Map<String, Object> hash = JsonUtil.jsonToMap((JSONObject) map);
            return hash;
        }
        throw new IllegalArgumentException("Value associated with key '" + key + "' is not an instance of Map");
    }


    @Override
    public void set(String key, Object value) {

        if (key.contains(".")) {
            String[] parts = key.split("\\.");
            HashMap<String, Object> map = (HashMap<String, Object>) getMap(parts[0]);
            if (parts.length == 2) {
                map.put(parts[1], value);
            }
            object.put(parts[0], map);
            try {
                Writer writer = new PrintWriter(new FileWriter(path + File.separator + name + ".json"));
                writer.write(object.toString(2));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        object.put(key, value);
        try {
            Writer writer = new PrintWriter(new FileWriter(path + File.separator + name + ".json"));
            writer.write(object.toString(2));
            writer.close();
        } catch (IOException e) {
            System.err.println("Couldn' t set " + key + " " + value + e.getMessage());
        }
    }
}
