package io.github.ceceayo.activitor.schemas

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import jakarta.json.Json


@Entity
data class Post(
    @PrimaryKey val id: String,
    @ColumnInfo(name="contents") val content: String,
    @ColumnInfo(name="created_at") val created_at: String
) {
    fun toJson(user: String){
        Json.createObjectBuilder()
            .add("id", "$user/posts/${id}")
            .add("published", created_at)
            .add("to", Json.createArrayBuilder()
                .add("https://www.w3.org/ns/activitystreams#Public"))
            .add("cc", Json.createArrayBuilder())

    }
}
