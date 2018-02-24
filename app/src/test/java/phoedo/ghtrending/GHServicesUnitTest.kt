package phoedo.ghtrending

import android.util.Log
import junit.framework.Assert
import org.junit.Test

import phoedo.ghtrending.model.GHRepoItem
import phoedo.ghtrending.networking.GHSevices
import phoedo.ghtrending.networking.NetworkManager
import phoedo.ghtrending.networking.enqueue
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.CountDownLatch
import kotlin.collections.ArrayList

/**
 *
 * Unit tests for services communication
 * https://api.github.com/
 *
 */
class GHServicesUnitTest {

    var listRequestLatch = CountDownLatch(1)
    var listResponse: MutableList<GHRepoItem> = ArrayList<GHRepoItem>()
    var networkManager: NetworkManager = NetworkManager(null);

    @Test
    fun listRequestTest() {

       networkManager = NetworkManager(object :NetworkManager.ReposListListener {
            override fun onReposReceived(repos: List<GHRepoItem>?) {
                if (repos!=null) {
                    listResponse.addAll(repos)
                    if (networkManager.hasNext && networkManager.page < 4){
                        networkManager.getNextPage();
                    } else{
                        listRequestLatch.countDown();
                    }

                }
            }
        })

        networkManager.getReposList();
        listRequestLatch.await();
        if (listResponse.isEmpty()) {
            Assert.assertTrue(false)
        }
        Assert.assertTrue(listResponse?.size == 4*30)
    }
}
