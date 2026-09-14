package com.nikoo.nikoclient.inventory

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nikoo.nikoclient.inventory.model.Profile
import org.slf4j.LoggerFactory
import java.io.File

object ProfileManager {
    private val LOGGER = LoggerFactory.getLogger("ProfileManager")
    private val gson = Gson()
    private val profiles = mutableMapOf<String, Profile>()
    private lateinit var profilesDir: File
    private val PROFILES_FILE = "profiles.json"

    fun initialize(configDir: File) {
        profilesDir = File(configDir, "nikoclient")
        if (!profilesDir.exists()) {
            profilesDir.mkdirs()
        }
    }

    fun load() {
        try {
            val configDir = File(System.getProperty("user.home"), ".minecraft/config")
            initialize(configDir)

            val profilesFile = File(profilesDir, PROFILES_FILE)
            if (profilesFile.exists()) {
                val json = profilesFile.readText()
                val type = object : TypeToken<Map<String, Profile>>() {}.type
                val loadedProfiles: Map<String, Profile> = gson.fromJson(json, type)
                profiles.putAll(loadedProfiles)
                LOGGER.info("Loaded ${profiles.size} profiles")
            } else {
                LOGGER.info("No profiles file found, starting fresh")
            }
        } catch (e: Exception) {
            LOGGER.error("Failed to load profiles", e)
        }
    }

    fun save() {
        try {
            val profilesFile = File(profilesDir, PROFILES_FILE)
            val json = gson.toJson(profiles)
            profilesFile.writeText(json)
            LOGGER.info("Saved profiles")
        } catch (e: Exception) {
            LOGGER.error("Failed to save profiles", e)
        }
    }

    fun createProfile(name: String): Profile {
        val profile = Profile(name)
        profiles[name] = profile
        save()
        LOGGER.info("Created profile: $name")
        return profile
    }

    fun deleteProfile(name: String): Boolean {
        val removed = profiles.remove(name) != null
        if (removed) {
            save()
            LOGGER.info("Deleted profile: $name")
        }
        return removed
    }

    fun renameProfile(oldName: String, newName: String): Boolean {
        val profile = profiles.remove(oldName) ?: return false
        profile.name = newName
        profiles[newName] = profile
        save()
        LOGGER.info("Renamed profile: $oldName -> $newName")
        return true
    }

    fun getProfile(name: String): Profile? {
        return profiles[name]
    }

    fun getAllProfiles(): List<Profile> {
        return profiles.values.toList()
    }

    fun profileExists(name: String): Boolean {
        return profiles.containsKey(name)
    }
}
