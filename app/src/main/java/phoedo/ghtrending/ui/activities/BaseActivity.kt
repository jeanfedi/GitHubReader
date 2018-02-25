package phoedo.ghtrending.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import phoedo.ghtrending.R
import phoedo.ghtrending.ui.fragments.DetailRepoFragment
import phoedo.ghtrending.ui.fragments.ListRepoFragment
import phoedo.ghtrending.model.GHRepoItem
import java.io.Serializable


open class BaseActivity : AppCompatActivity() {

    companion object {
        const val EXTRAOBJECTKEY = "extraobject"
        const val NAMETRANSITION = "nametransition"
        const val DESCRIPTIONTRANSITION = "descriptionTransitio"
        const val STARSTRANSITION = "starstransition"
        private const val LISTFRAGMENTTAG = "listfragmenttag"
        private const val DETAILFRAGMENTTAG = "listfragmenttag"

    }

    var extraObject: Serializable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent.extras != null) {
            extraObject = intent.getSerializableExtra(EXTRAOBJECTKEY)
        }

        if (extraObject is GHRepoItem) {
            loadDetail()
        } else {
            loadList()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.getItemId()) {
            android.R.id.home -> {
                onBackPressed()
                return true;
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun loadList() {
        val fragment = ListRepoFragment();
        fragment.arguments = intent.extras;
        supportFragmentManager.beginTransaction().add(R.id.container, fragment, LISTFRAGMENTTAG).commitNow()
    }

    fun loadDetail() {
        val fragment = DetailRepoFragment();
        fragment.arguments = intent.extras;
        supportFragmentManager.beginTransaction().add(R.id.container, fragment, DETAILFRAGMENTTAG).commitNow()
    }

}