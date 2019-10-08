package localhost.android.model

import java.io.Serializable

data class ParticipantResponseData (
        val id: String = "",
        val created_at: String = "",
        val event_id: String? = "",
        val lat: String = "",
        val lon: String = "",
        val opinion_message: String = "",
        val user_id: String = "",
        val error: String =""

) : Serializable
