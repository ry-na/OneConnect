package localhost.android.model

import java.io.Serializable

data class DataClassBase(
        val error: List<String>? = null
) : Serializable
/**
 * Postリクエストのレスポンスをラップするデータクラス
 * @author Ryo Natori (ryo.natori0809@gmail.com)
 */
data class PostResponseData(
        val sid: String? = "",
        val error: List<String>? = null,
        val created_at: String = "",
        val lat: String = "",
        val lon: String = "",
        val opinion_id: String = "",
        val reply_message: String = "",
        val user_id: String = ""
) : Serializable

/**
 *Getリクエストのレスポンスをラップするデータクラス
 * @author Ryo Natori (ryo.natori0809@gmail.com)
 */
data class GetResponseData(
        val error: String? = "",
        val created_at: String = "",
        val event_id: String? = "",
        val lat: String = "",
        val lon: String = "",
        val opinion_id: String = "",
        val opinion_message: String = "",
        val user_id: String = ""
) : Serializable

data class ReplyResponseData(
        val created_at: String = "",
        val lat: String = "",
        val lon: String = "",
        val opinion_id: String = "",
        val reply_message: String = "",
        val user_id: String = ""
) : Serializable
