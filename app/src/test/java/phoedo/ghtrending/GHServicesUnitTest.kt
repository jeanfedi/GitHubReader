package phoedo.ghtrending

import android.util.Log
import org.junit.Test

import phoedo.ghtrending.model.GHRepoItem
import phoedo.ghtrending.networking.GHSevices
import phoedo.ghtrending.networking.enqueue
import java.util.concurrent.CountDownLatch

/**
 *
 * Unit tests for services communication
 * https://api.github.com/
 *
 */
class GHServicesUnitTest {

    var listRequestLatch = CountDownLatch(1)
    var listResponse: List<GHRepoItem>? = null;

    @Test
    fun listRequestTest() {

        val ghServicesManager by lazy {
            GHSevices.create()
        }
        val call = ghServicesManager.getRepositoriesList("stars", "desc")
        call.enqueue(success = { response ->
            listResponse = response.body();
            listRequestLatch.countDown();
            if (response.isSuccessful) {

            }
        }, failure = { t ->
            listResponse = null;
            listRequestLatch.countDown();
            if (t != null) {
                Log.d("ERROR", t.localizedMessage)
            }
        })
        listRequestLatch.await();
        if (listResponse?.isEmpty()!!) {
            assert(false)
        } else {
            assert(true)
        }
    }
}
