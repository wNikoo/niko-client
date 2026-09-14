package com.nikoo.nikoclient.data

import com.google.gson.*
import com.nikoo.nikoclient.inventory.model.ItemMatcher
import java.lang.reflect.Type

class ItemMatcherSerializer : JsonSerializer<ItemMatcher>, JsonDeserializer<ItemMatcher> {
    override fun serialize(src: ItemMatcher, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        val obj = JsonObject()
        src.itemId?.let { obj.addProperty("itemId", it) }
        src.customName?.let { obj.addProperty("customName", it) }
        src.nbtData?.let { obj.addProperty("nbtData", it) }
        return obj
    }

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): ItemMatcher {
        val obj = json.asJsonObject
        return ItemMatcher(
            itemId = obj.get("itemId")?.asString,
            customName = obj.get("customName")?.asString,
            nbtData = obj.get("nbtData")?.asString
        )
    }
}
