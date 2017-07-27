package localhost.android.network

import localhost.android.config.Network
import localhost.android.model.*
import retrofit2.http.*
import rx.Observable
import java.util.*

/**
 * Httpリクエストを行うためのインターフェース
 * @see http://square.github.io/retrofit/
 * @author Ryo Natori (ryo.natori0809@gmail.com)
 */
interface NetworkInterface {
    @Headers("Content-type: application/json")
    @POST(Network.USER_API_URL + Network.LOGIN)
    fun login(
            @Header("Session-ID") sId: String = "",
            @Body body: HashMap<String, String>?
    ): Observable<List<LoginResponseData>>

    @Headers("Content-type: application/json")
    @GET(Network.OPINION_API_URL + Network.GET)
    public fun opinion(
            @Header("Session-ID") sId: String = ""
    ): Observable<List<OpinionResponseData>>

    @Headers("Content-type: application/json")
    @POST(Network.OPINION_API_URL + Network.REPLY)
    fun reply(
            @Header("Session-ID") sId: String = "",
            @Body body: HashMap<String, String>?
    ): Observable<List<ReplyResponseData>>
}