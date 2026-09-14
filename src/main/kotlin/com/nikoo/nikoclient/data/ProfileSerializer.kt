package com.nikoo.nikoclient.data

import com.google.gson.*
import com.nikoo.nikoclient.inventory.model.Profile
import java.lang.reflect.Type

class ProfileSerializer : JsonSerializer<Profile>, JsonDeserializer<Profile> {
    private val itemMatcherSerializer = ItemMatcherSerializer()

    override fun serialize(src: Profile, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        val obj = JsonObject()
        obj.addProperty("name", src.name)
        src.keybind?.let { obj.addProperty("keybind", it) }

        val slotsObj = JsonObject()
        for ((slotIndex, matcher) in src.slots) {
            if (matcher != null) {
                slotsObj.add(slotIndex.toString(), itemMatcherSerializer.serialize(matcher, ItemMatcher::class.java, context))
            }
        }
        obj.add("slots", slotsObj)
        return obj
    }

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Profile {
        val obj = json.asJsonObject
        val name = obj.get("name").asString
        val profile = Profile(name)

        obj.get("keybind")?.let {
            profile.keybind = it.asString
        }

        val slotsObj = obj.getAsJsonObject("slots")
        for ((slotIndex, matcherJson) in slotsObj.entrySet()) {
            val matcher = itemMatcherSerializer.deserialize(matcherJson, ItemMatcher::class.java, context)
            profile.configureSlot(slotIndex.toInt(), matcher)
        }

        return profile
    }
}
