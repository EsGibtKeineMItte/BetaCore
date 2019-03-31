package de.leonhard.storage;

import java.io.FileNotFoundException;
import java.util.List;

public interface StorageBase {

    /**
     * Get a String from a JSON-File
     *
     * @param key Path to String in JSON-File
     * @return Returns the value
     */

     String getString(final String key);

    /**
     * Gets a long from a JSON-File
     *
     * @param key Path to long in JSON-FILE
     * @return String from JSON
     */

    long getLong(final String key);

    /**
     * Gets a int from a JSON-File
     *
     * @param key Path to int in JSON-File
     * @return Int from JSON
     */

    int getInt(final String key);

    /**
     * Get a byte from a JSON-File
     *
     * @param key Path to byte in JSON-File
     * @return Byte from JSON
     */

    byte getByte(final String key);

    /**
     * Get a boolean from a JSON-File
     * @param key Path to boolean in JSON-File
     * @return Boolean from JSON
     */

    boolean getBoolean(final String key);

    /**
     * Get a float from a JSON-File
     *
     * @param key Path to float in JSON-File
     * @return Float from JSON
     */

    float getFloat(final String key);

    /**
     * Get a double from a JSON-File
     *
     * @param key Path to double in JSON-File
     * @return Double from JSON
     */

    double getDouble(final String key);

    /**
     * Get a StringList from a JSON-File
     *
     * @param key Path to StringList in JSON-File
     * @return String-List
     */

    List<?> getList(final String key);

    /**
     * Get String List
     * @param key Path to String List in JSON-FIle
     * @return List
     */

    List<String> getStringList(final String key);

    /**
     * Get a IntegerList from a JSON-File
     *
     * @param key Path to Integer-List in JSON-File
     * @return Integer-List
     */


    List<Integer> getIntegerList(final String key);

    /**
     * Get a Byte-List from a JSON-File
     *
     * @param key Path to Byte-List from JSON-File
     * @return Byte-List
     */

    List<Byte> getByteList(final String key);

    /**
     * Get a Long-List from a JSON-File
     *
     * @param key Path to Long-List to JSON-File
     * @return Long-List
     */

    List<Long> getLongList(final String key);

     void set(String key, Object value) throws FileNotFoundException;






}
