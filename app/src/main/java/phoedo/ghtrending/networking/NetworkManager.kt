package phoedo.ghtrending.networking

import android.util.Log
import phoedo.ghtrending.model.GHRepoItem
import phoedo.ghtrending.model.GHRepoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by phoedo on 24/02/18.
 */
class NetworkManager {
    val ghServicesManager by lazy {
        GHSevices.create()
    }

    val listener: ReposListListener?
    var page = 1;
    var hasNext = true;
    private var isLoading = false;

    constructor(listener: ReposListListener?) {
        this.listener = listener;
    }


    fun getReposList() {
        this.getReposList(1);
    }

    fun getNextPage() {
        if (hasNext) {
            this.getReposList(++page)
        }
    }

    fun getReposList(page: Int) {
        if (!isLoading) {
            isLoading = true
            var call = ghServicesManager.getRepositoriesList("stars", "desc", "language:java created:>" + getRequestDate(), page)
            call.enqueue(success = { response ->
                val linkHeader = response.headers().get("Link")
                hasNext = linkHeader!!.contains("rel=\"next\"")
                isLoading = false
                listener?.onReposReceived(response.body()?.items)


            }, failure = { t ->
                hasNext = false;
                isLoading = false
                if (t != null) {
                    Log.d("NetworkManager", t.localizedMessage);
                }
                listener?.onReposReceived(null)

            })
        }


    }

    fun getRequestDate(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -7)
        val date = calendar.time;
        val format = SimpleDateFormat("yyyy-MM-dd")
        return format.format(date)
    }

    interface ReposListListener {
        fun onReposReceived(repos: List<GHRepoItem>?)
    }

}