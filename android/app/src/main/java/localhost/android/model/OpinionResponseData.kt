package localhost.android.model

import java.io.Serializable

/**
 *Getリクエストのレスポンスをラップするデータクラス
 * @author Ryo Natori (ryo.natori0809@gmail.com)
 */
data class OpinionResponseData(
        val id: String = "",
        val created_at: String = "",
        val event_id: String? = "",
        val lat: String = "",
        val lon: String = "",
        val opinion_message: String = "",
        val user_id: String = ""
) : Serializable