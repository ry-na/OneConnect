package localhost.android.model

import java.io.Serializable

data class ReplyResponseData(
        val created_at: String = "",
        val opinion_id: String = "",
        val reply_message: String = "",
        val user_id: String = ""
) : Serializable