package phoedo.ghtrending.networking

import phoedo.ghtrending.model.GHReadMeItem
import phoedo.ghtrending.model.GHRepoItem
import phoedo.ghtrending.model.GHRepoResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by phoedo on 24/02/18.
 */
interface GHSevices {
    @GET("/repositories")
    fun getRepositoriesList(@Query("sort") sort: String, //stars
                            @Query("order") order: String //asc
    ): Call<List<GHRepoItem>>

    @GET("search/repositories")
    fun getRepositoriesList(@Query("sort") sort: String, //stars
                            @Query("order") order: String, //desc
                            @Query("q") query: String,
                            @Query("page") page: Int
    ): Call<GHRepoResponse>

    @GET("repos/{user}/{repo}/readme")
    fun getRepoReadme(@Path("user") user: String,
                      @Path("repo") repo: String
    ): Call<GHReadMeItem>

    companion object {
        fun create(): GHSevices {
            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://api.github.com/")
                    .build()

            return retrofit.create(GHSevices::class.java)
        }
    }


}