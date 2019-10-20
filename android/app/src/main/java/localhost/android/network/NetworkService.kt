package localhost.android.network

import android.content.Context
import localhost.android.config.Network
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import android.net.NetworkInfo
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager



/**
 * Httpリクエストに関する機能を提供するクラス
 * @author Ryo Natori (ryo.natori0809@gmail.com)
 */
class NetworkService {
    companion object {

        /**
         * Retrofitオブジェクトを返却する関数
         * @return Retrofitオブジェクト
         * @author Ryo Natori (ryo.natori0809@gmail.com)
         */
        fun getRetrofit(): Retrofit = Retrofit.Builder()
                .client(OkHttpClient.Builder().build())
                .baseUrl(Network.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }


}
