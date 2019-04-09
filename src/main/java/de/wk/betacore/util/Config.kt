package de.wk.betacore.util


import java.io.File
import java.io.IOException
import java.io.InputStream
import java.util.ArrayList
import java.util.Arrays
import java.util.UUID

import org.apache.commons.io.FileUtils
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin

import net.md_5.bungee.api.ChatColor

//

/**
 * @author Jonas Braun, Zeanon
 */
class Config {
    private var configFile: File? = null
    private var datafolder: File? = null
    var config: FileConfiguration? = null
        private set
    var isLoaded = false
        private set
    private var blankConfig = true
    private val resourcefile: File = null
    var inputStream: InputStream? = null
        private set





    var configname: String

    /*
     * Constructor for Config class
     *
     * @param String      configname is the path, where the configfile will be (relative to the plugin Folder)
     * @param String      resourcefile is the filename in the resources folder(src/resources) that should be used
     * @param InputStream inputstream is a inputstream from another Config, which should be used to create the Config with instead of getting a resourcefile
     * @param JavaPlugin  plugin Instance of JavaPlugin, used to get plugins datafolder
     */
    constructor(configname: String, plugin: Plugin) {

        this.configname = configname
        this.datafolder = null
        if (!configname.contains("/")) {
            Config.plugin = plugin
            val datafolder = plugin.dataFolder
            val configfile = File(datafolder.path, configname)
            this.datafolder = datafolder
            this.configFile = configfile
        }
        if (configname.contains("/")) {
            Config.plugin = plugin
            val parts = ArrayList(Arrays.asList(*configname.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()))
            var datafolderpath = parts[0]
            var i = 1
            while (i < parts.size - 1) {
                datafolderpath = datafolderpath + "/" + parts[i]
                i++
            }
            val plugindatafolder = plugin.dataFolder
            val datafolder = File(plugindatafolder.absolutePath + "/" + datafolderpath)
            val configfile = File(datafolder.path, parts[parts.size - 1])
            this.datafolder = datafolder
            this.configFile = configfile
        }
        this.blankConfig = true

        if (!this.configFile!!.exists()) {
            this.loadDefault()
        }
        val cfg = YamlConfiguration()
        try {
            cfg.load(this.configFile!!)
        } catch (e: Exception) {
        }

        this.config = cfg

        if (this.load()) {
            this.isLoaded = true
        }
        this.isLoaded = true
        return
    }

    constructor(configname: String, resourcefile: String, plugin: Plugin) {
        this.configname = configname
        this.datafolder = null
        this.resourcefile = File(resourcefile)
        if (!configname.contains("/")) {
            Config.plugin = plugin
            val datafolder = plugin.dataFolder
            val configfile = File(datafolder.path, configname)
            this.datafolder = datafolder
            this.configFile = configfile
        }

        if (configname.contains("/")) {
            Config.plugin = plugin
            val parts = ArrayList(Arrays.asList(*configname.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()))
            var datafolderpath = parts[0]
            var i = 1
            while (i < parts.size - 1) {
                datafolderpath = datafolderpath + "/" + parts[i]
                i++
            }
            val plugindatafolder = plugin.dataFolder
            val datafolder = File(plugindatafolder.absolutePath + "/" + datafolderpath)
            val configfile = File(datafolder.path, parts[parts.size - 1])
            this.datafolder = datafolder
            this.configFile = configfile
        }
        this.blankConfig = false

        this.inputStream = Config::class.java.classLoader
                .getResourceAsStream("resources/" + this.resourcefile.name)

        if (!this.configFile!!.exists()) {
            this.loadDefault()
        }
        val cfg = YamlConfiguration()
        try {
            cfg.load(this.configFile!!)
        } catch (e: Exception) {
        }

        this.config = cfg

        if (this.load()) {
            this.isLoaded = true
        }
        this.isLoaded = true
        return
    }

    constructor(configname: String, inputstream: InputStream, plugin: Plugin) {
        this.configname = configname
        this.datafolder = null
        if (!configname.contains("/")) {
            Config.plugin = plugin
            val datafolder = plugin.dataFolder
            val configfile = File(datafolder.path, configname)
            this.datafolder = datafolder
            this.configFile = configfile
        }

        if (configname.contains("/")) {
            Config.plugin = plugin
            val parts = ArrayList(Arrays.asList(*configname.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()))
            var datafolderpath = parts[0]
            var i = 1
            while (i < parts.size - 1) {
                datafolderpath = datafolderpath + "/" + parts[i]
                i++
            }
            val plugindatafolder = plugin.dataFolder
            val datafolder = File(plugindatafolder.absolutePath + "/" + datafolderpath)
            val configfile = File(datafolder.path, parts[parts.size - 1])
            this.datafolder = datafolder
            this.configFile = configfile
        }
        this.blankConfig = false

        this.inputStream = inputstream

        if (!this.configFile!!.exists()) {
            this.loadDefault()
        }
        val cfg = YamlConfiguration()
        try {
            cfg.load(this.configFile!!)
        } catch (e: Exception) {
        }

        this.config = cfg

        if (this.load()) {
            this.isLoaded = true
        }
        this.isLoaded = true
        return
    }

    fun fileExists(configname: String, plugin: Plugin): Boolean {
        var exists = true
        if (!configname.contains("/")) {
            Config.plugin = plugin
            val datafolder = plugin.dataFolder
            val configfile = File(datafolder.path, configname)
            this.datafolder = datafolder
            this.configFile = configfile
        }

        if (configname.contains("/")) {
            Config.plugin = plugin
            val parts = ArrayList(Arrays.asList(*configname.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()))
            var datafolderpath = parts[0]
            var i = 1
            while (i < parts.size - 1) {
                datafolderpath = datafolderpath + "/" + parts[i]
                i++
            }
            val plugindatafolder = plugin.dataFolder
            val datafolder = File(plugindatafolder.absolutePath + "/" + datafolderpath)
            val configfile = File(datafolder.path, parts[parts.size - 1])
            this.datafolder = datafolder
            this.configFile = configfile
        }

        if (!this.datafolder!!.exists()) {
            exists = false
        }
        if (!this.configFile!!.exists()) {
            exists = false
        }
        return exists
    }

    private fun loadDefault(): Boolean {
        try {
            if (!this.datafolder!!.exists()) {
                this.datafolder!!.mkdirs()
            }
            if (!this.configFile!!.exists()) {
                this.configFile!!.createNewFile()
            }
            if (!this.blankConfig) {
                FileUtils.copyInputStreamToFile(this.inputStream!!, this.configFile!!)
                return true
            }
            return if (this.blankConfig) {
                true
            } else true
        } catch (e: IOException) {
            return false
        }

    }

    fun hasKey(path: String): Boolean {
        return if (this.getString(path) != null) {
            true
        } else false
    }

    private fun load(): Boolean {
        if (!this.configFile!!.exists()) {
            if (!this.loadDefault()) {
                println("[" + plugin!!.name + "] >> [Configs] >> " + this.configFile!!.name + " could not be loaded")
                return false
            }
        }
        val cfg = YamlConfiguration()
        try {
            cfg.load(this.configFile!!)
        } catch (e: Exception) {
            println("[" + plugin!!.name + "] >> [Configs] >> " + this.configFile!!.name + " could not be loaded")
            return false
        }

        this.config = cfg
        //   System.out.println("[" + plugin.getName() + "] >> [Configs] >> " + this.configname + " loaded");
        this.isLoaded = true
        return true
    }

    fun reload(): Boolean {
        return this.load()
    }

    fun setLocation(path: String, loc: Location) {
        this.config!!.set("$path.world", loc.world.name)
        this.config!!.set("$path.x", loc.x)
        this.config!!.set("$path.y", loc.y)
        this.config!!.set("$path.z", loc.z)
        this.config!!.set("$path.yaw", loc.yaw)
        this.config!!.set("$path.pitch", loc.pitch)
        try {
            this.config!!.save(this.configFile!!)
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    fun getLocation(path: String): Location {
        var world = Bukkit.getWorld("world")
        val x = this.config!!.getDouble("$path.x")
        val y = this.config!!.getDouble("$path.y")
        val z = this.config!!.getDouble("$path.z")
        var yaw: Float? = 0f
        var pitch: Float? = 0f
        if (this.config!!.getString("$path.yaw") != null) {
            yaw = this.config!!.getDouble("$path.yaw").toFloat()
        }
        if (this.config!!.getString("$path.pitch") != null) {
            pitch = this.config!!.getDouble("$path.pitch").toFloat()
        }
        if (this.config!!.getString("$path.world") != null) {
            world = Bukkit.getWorld(this.config!!.getString("$path.world"))
        }
        val location = Location(world, x, y, z, yaw!!, pitch!!)
        return location
    }

    fun getReplaced3(path: String, placeholder: String, replacement: String, placeholder2: String, replacement2: String): String {
        val rawmessage = ChatColor.translateAlternateColorCodes('&', this.config!!.getString(path))
        return rawmessage.replace(placeholder.toRegex(), replacement).replace(placeholder2.toRegex(), replacement2)
    }

    fun getReplaced2(path: String, placeholder: String, replacement: String, placeholder2: String, replacement2: String, placeholder3: String, replacement3: String): String {
        val rawmessage = ChatColor.translateAlternateColorCodes('&', this.config!!.getString(path))
        return rawmessage.replace(placeholder.toRegex(), replacement).replace(placeholder2.toRegex(), replacement2).replace(placeholder3.toRegex(), replacement3)
    }

    fun getReplaced1(path: String, placeholder: String, replacement: String): String {
        val rawname = ChatColor.translateAlternateColorCodes('&', this.config!!.getString(path))
        return rawname.replace(placeholder.toRegex(), replacement)
    }

    fun getMaterial(path: String): Material {
        val material = this.config!!.getString(path).toUpperCase()
        val block = Material.getMaterial(material)
        return block
    }

    fun setMaterial(path: String, mat: String) {
        this.config!!.set(path, mat.toUpperCase())
    }

    fun createSection(path: String): ConfigurationSection {
        this.config!!.createSection(path)
        try {
            this.config!!.save(this.configFile!!)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return this.config!!.getConfigurationSection(path)
    }

    fun getSection(path: String): ConfigurationSection {
        return this.config!!.getConfigurationSection(path)
    }

    fun deletePath(path: String) {
        this.config!!.set(path, null)
        try {
            this.config!!.save(this.configFile!!)
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    fun save() {
        try {
            this.config!!.save(this.configFile!!)
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    fun setString(path: String, value: String) {
        this.config!!.set(path, value)
        try {
            this.config!!.save(this.configFile!!)
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    fun getString(path: String): String? {
        return this.config!!.getString(path)
    }

    fun setInt(path: String, value: Int) {
        this.config!!.set(path, value)
        try {
            this.config!!.save(this.configFile!!)
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    fun getInt(path: String): Int {
        return this.config!!.getInt(path)
    }

    fun setDouble(path: String, value: Double) {
        this.config!!.set(path, value)
        try {
            this.config!!.save(this.configFile!!)
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    fun getDouble(path: String): Double {
        return this.config!!.getDouble(path)
    }

    fun setBoolean(path: String, value: Boolean) {
        this.config!!.set(path, value)
        try {
            this.config!!.save(this.configFile!!)
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    fun getBoolean(path: String): Boolean {
        return this.config!!.getBoolean(path)
    }

    fun setList(path: String, list: List<*>) {
        this.config!!.set(path, list)
        try {
            this.config!!.save(this.configFile!!)
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    fun getList(path: String): List<*> {
        return this.config!!.getList(path)
    }

    fun setHeader(value: String) {
        this.config!!.options().header(value)
        try {
            this.config!!.save(this.configFile!!)
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    fun setFloat(path: String, value: Float?) {
        this.config!!.set(path, value)
        try {
            this.config!!.save(this.configFile!!)
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    fun getFloat(path: String): Float? {
        return this.config!!.getDouble(path).toFloat()
    }

    fun getKeys(path: String, deep: Boolean): Set<String> {
        return this.config!!.getConfigurationSection(path).getKeys(deep)
    }

    fun getUUID(path: String): UUID {
        return UUID.fromString(this.config!!.getString(path))
    }

    fun clear(): Boolean {
        if (this.configFile!!.exists()) {
            this.configFile!!.delete()
        }
        try {
            if (!this.datafolder!!.exists()) {
                this.datafolder!!.mkdirs()
            }
            if (!this.configFile!!.exists()) {
                this.configFile!!.createNewFile()
            }
            if (!this.blankConfig) {
                FileUtils.copyInputStreamToFile(this.inputStream!!, this.configFile!!)
                return true
            }
            return if (this.blankConfig) {
                true
            } else true
        } catch (e: IOException) {
            return false
        }

    }

    fun deleteConfig() {
        this.configFile!!.delete()
    }

    companion object {
        private var plugin: Plugin? = null
    }
}

