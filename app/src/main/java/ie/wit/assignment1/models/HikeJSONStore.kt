package ie.wit.assignment1.hikes.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*
import ie.wit.assignment1.hikes.models.helpers.

const val JSON_FILE = "placemarks.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<HikeModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class HikeJSONStore(private val context: Context) : HikeStore {

    var hikes = mutableListOf<HikeModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<HikeModel> {
        logAll()
        return hikes
    }

    override fun create(placemark: HikeModel) {
        hike.id = generateRandomId()
        hikes.add(placemark)
        serialize()
    }


    override fun update(hike: HikeModel) {
        // todo
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(hikes, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        hikes = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun logAll() {
        hikes.forEach { Timber.i("$it") }
    }
}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}