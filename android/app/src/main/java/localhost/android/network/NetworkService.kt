package localhost.android.network

import localhost.android.config.Network
import localhost.android.model.DataClassBase
import localhost.android.model.GetResponseData
import localhost.android.model.PostResponseData
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.HttpException
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.io.Serializable
import java.util.*

/**
 * Httpリクエストに関する機能を提供するクラス
 * @author Ryo Natori (ryo.natori0809@gmail.com)
 */
public class NetworkService {
    companion object {
        const val SUCCESS = "success"
        const val ERROR = "error"

        /**
         * Getリクエストを行う関数
         * @param apiName リクエスト対象Apiの名
         * @param callback レスポンスに対する処理を行うコールバック関数
         *     @param status 成功ならtrue失敗ならfalse
         *     @param response レスポンスデータ
         * @author Ryo Natori (ryo.natori0809@gmail.com)
         */
        fun sendHttpGetRequest(apiName: String,
                               callback: (status: Boolean, response: List<Serializable?>?) -> Unit
        ) {
            val retrofit = getRetrofit()
            val subscribe = retrofit.create(NetworkInterface::class.java)
                    .get(apiName, "")    // TODO: 第二引数で if(sId == null) "" else sId こんな関数を呼ぶ
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Subscriber<List<Serializable?>>() {

                        /**
                         * 完了
                         */
                        override fun onCompleted() {
                            println("onCompleted!!")
                        }

                        /**
                         * 失敗
                         */
                        override fun onError(e: Throwable?) {
                            if (e is HttpException) {
                                val a = object : Annotation {}
                                callback(false, retrofit.responseBodyConverter<List<Serializable?>>(Serializable::class.java, arrayOf(a))
                                        .convert(e.response().errorBody()))
                            } else callback(false, listOf(DataClassBase()))
                        }

                        /**
                         * 成功
                         */
                        override fun onNext(t: List<Serializable?>?) {
                                callback(true, t)
                        }
                    })
        }

        /**
         * Postリクエストを行う関数
         * @param apiName リクエスト対象Apiの名
         * @param body Request body
         * @param callback レスポンスに対する処理を行うコールバック関数
         *     @param status 成功ならtrue失敗ならfalse
         *     @param response レスポンスデータ
         * @author Ryo Natori (ryo.natori0809@gmail.com)
         */
        fun sendHttpPostRequest(apiName: String,
                                body: HashMap<String, String>?,
                                callback: (status: Boolean, response: List<Serializable?>?) -> Unit
        ) {
            val retrofit = getRetrofit()
            retrofit.create(NetworkInterface::class.java)
                    .post(apiName, "", body)    // TODO: 第二引数で if(sId == null) "" else sId こんな関数を呼ぶ
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Subscriber<List<Serializable?>>() {
                        /**
                         * 完了
                         */
                        override fun onCompleted() {
                            println("OK")
                        }

                        /**
                         * 失敗
                         */
                        override fun onError(e: Throwable?) {
                            e!!.printStackTrace()
                            if (e is HttpException) {
                                val a = object : Annotation {}
                                callback(false, retrofit.responseBodyConverter<List<Serializable?>>(Serializable::class.java, arrayOf(a))
                                        .convert(e.response().errorBody()))
                            } else callback(false, listOf(DataClassBase()))
                        }

                        /**
                         * 成功
                         */
                        override fun onNext(t: List<Serializable?>?) {
                            callback(true, t)
                        }
                    })
        }

        /**
         * Retrofitオブジェクトを返却する関数
         * @return Retrofitオブジェクト
         * @author Ryo Natori (ryo.natori0809@gmail.com)
         */
        private fun getRetrofit(): Retrofit = Retrofit.Builder()
                .client(OkHttpClient.Builder().build())
                .baseUrl(Network.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
}
