package localhost.android.model

import java.io.Serializable

data class ParticipantResponseData (

        val created_at: String? = "",
        val is_participant : Boolean?=false
) : Serializable
