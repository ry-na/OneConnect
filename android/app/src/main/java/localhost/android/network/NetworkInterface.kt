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
            @Header("sid") sId: String = "",
            @Body body: HashMap<String, String>?
    ): Observable<List<LoginResponseData>>

    @Headers("Content-type: application/json")
    @POST(Network.USER_API_URL + Network.REGISTER)
    fun register(
            @Header("sid") sId: String = "",
            @Body body: HashMap<String, String>?
    ): Observable<List<RegisterResponseData>>

    @Headers("Content-type: application/json")
    @GET(Network.OPINION_API_URL + Network.GET)
    fun opinion(
            @Header("sid") sId: String = ""
    ): Observable<List<OpinionResponseData>>

    @Headers("Content-type: application/json")
    @GET(Network.OPINION_API_URL + Network.GET_REPLY)
    fun get_reply(
            @Header("sid") sId: String = "",
            @Query("opinion_id") oId: String
    ): Observable<List<ReplyResponseData>>

    @Headers("Content-type: application/json")
    @POST(Network.OPINION_API_URL + Network.REPLY)
    fun reply(
            @Header("sid") sId: String = "",
            @Body body: HashMap<String, String>?
    ): Observable<List<ReplyResponseData>>

    @Headers("Content-type: application/json")
    @POST(Network.OPINION_API_URL + Network.NEW_OPINION)
    fun newOpinion(
            @Header("sid") sId: String = "",
            @Body body: HashMap<String, String>?
    ): Observable<List<ReplyResponseData>>//TODO:リファクタ
    //TODO:0304の次 replyとかの名前どうにかする
}