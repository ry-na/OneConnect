package localhost.android.network

import localhost.android.model.GetResponseData
import localhost.android.model.PostResponseData
import retrofit2.http.*
import rx.Observable
import java.io.Serializable
import java.util.*

/**
 * Httpリクエストを行うためのインターフェース
 * @see http://square.github.io/retrofit/
 * @author Ryo Natori (ryo.natori0809@gmail.com)
 */
public interface NetworkInterface {
    @Headers("Content-type: application/json")
    @GET("{apiName}")
    public fun get(
            @Path("apiName") apiName: String,
            @Header("Session-ID") sId: String = ""
    ): Observable<List<GetResponseData>>

    @Headers("Content-type: application/json")
    @POST("{apiName}")
    public fun post(
            @Path("apiName") apiName: String,
            @Header("Session-ID") sId: String = "",
            @Body body: HashMap<String, String>?
    ): Observable<List<PostResponseData>>
}