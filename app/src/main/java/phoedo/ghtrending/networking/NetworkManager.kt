package phoedo.ghtrending.networking

import android.util.Base64
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
import java.nio.charset.Charset
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by phoedo on 24/02/18.
 */

class NetworkManager {
    val ghServicesManager by lazy {
        GHSevices.create()
    }

    var page = 1;
    var hasNext = true;
    private var isLoading = false;


    fun getReposList(listener: ReposListListener?) {
        this.getReposList(1, listener);
    }

    fun getNextPage(listener: ReposListListener?) {
        if (hasNext) {
            this.getReposList(++page, listener)
        }
    }

    fun getReposList(page: Int, listener: ReposListListener?) {
        if (!isLoading) {
            isLoading = true
            val call = ghServicesManager.getRepositoriesList("stars", "desc", "language:java created:>" + getRequestDate(), page)
            call.enqueue(success = { response ->
                val linkHeader = response.headers().get("Link")
                if (linkHeader!=null) {
                    hasNext = linkHeader.contains("rel=\"next\"")
                }
                isLoading = false
                listener?.onReposReceived(response.body()?.items)


            }, failure = { t ->
                hasNext = false;
                isLoading = false
                Log.d(NetworkManager::class.simpleName, t.localizedMessage);
                listener?.onReposReceived(null)

            })
        }
    }

    fun getRepoReadme(repo: GHRepoItem, listener: RepoReadMeListener?) {
        if (!isLoading) {
            isLoading = true
            val call = ghServicesManager.getRepoReadme(repo.owner.login, repo.name);
            call.enqueue(success = { response ->
                isLoading = false
                val readMeString = Base64.decode(response.body()?.content, Base64.DEFAULT).toString(Charset.defaultCharset());
                listener?.onRepoReadMeReceved(readMeString)
            }, failure = { t ->
                isLoading = false
                Log.d(NetworkManager::class.simpleName, t.localizedMessage);
                listener?.onRepoReadMeReceved(null)
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

    interface RepoReadMeListener {
        fun onRepoReadMeReceved(readme: String?)
    }

}