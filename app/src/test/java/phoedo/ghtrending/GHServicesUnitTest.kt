package phoedo.ghtrending

import junit.framework.Assert
import org.junit.Test
import phoedo.ghtrending.model.GHRepoItem
import phoedo.ghtrending.networking.NetworkManager
import java.util.concurrent.CountDownLatch

/**
 *
 * Unit tests for services communication
 * https://api.github.com/
 *
 */
class GHServicesUnitTest {

    var listRequestLatch = CountDownLatch(1)
    var listResponse: MutableList<GHRepoItem> = ArrayList<GHRepoItem>()

    var readMeRequestLatch = CountDownLatch(1)
    var readMe: String? = null;
    var networkManager: NetworkManager = NetworkManager();

    var listRequestListener: NetworkManager.ReposListListener? = null
    var readMeListener: NetworkManager.RepoReadMeListener? = null

    @Test
    fun listRequestTest() {

        listRequestListener = object : NetworkManager.ReposListListener {
            override fun onReposReceived(repos: List<GHRepoItem>?) {
                if (repos != null) {
                    listResponse.addAll(repos)
                    if (networkManager.hasNext && networkManager.page < 4) {
                        networkManager.getNextPage(listRequestListener);
                    } else {
                        listRequestLatch.countDown();
                    }
                }
            }
        }

        readMeListener = object : NetworkManager.RepoReadMeListener {
            override fun onRepoReadMeReceved(readme: String?) {
                readMe = readme
                readMeRequestLatch.countDown()
            }
        }

        networkManager.getReposList(listRequestListener);
        listRequestLatch.await()
        if (listResponse.isEmpty()) {
            Assert.assertTrue(false)
        }
        Assert.assertTrue(listResponse?.size == 4 * 30)
        networkManager.getRepoReadme(listResponse?.get(0), readMeListener)
        readMeRequestLatch.await()
        Assert.assertTrue(readMe?.isEmpty()!!)
    }
}
