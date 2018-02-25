package phoedo.ghtrending.ui.fragments

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import butterknife.BindView
import butterknife.ButterKnife
import phoedo.ghtrending.R
import phoedo.ghtrending.model.GHRepoItem
import phoedo.ghtrending.networking.NetworkManager
import phoedo.ghtrending.ui.activities.BaseActivity
import phoedo.ghtrending.ui.activities.DetailActivity
import phoedo.ghtrending.ui.adapters.GHRecycleListAdapter

/**
 * A placeholder fragment containing a simple view.
 */
class ListRepoFragment : Fragment(), GHRecycleListAdapter.GHRecyclerViewListener, NetworkManager.ReposListListener {

    var networkManager = NetworkManager()
    var adapter: GHRecycleListAdapter? = null

    @BindView(R.id.recyclerList)
    lateinit var listView: RecyclerView

    @BindView(R.id.progressbar)
    lateinit var progressBar: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_list_repo, container, false)
        ButterKnife.bind(this, v);
        progressBar.indeterminateDrawable.setColorFilter(ContextCompat.getColor(context, R.color.textDarkGray), PorterDuff.Mode.SRC_IN)
        adapter = GHRecycleListAdapter(context,this)
        listView.layoutManager = LinearLayoutManager(context);
        listView.adapter = adapter;
        progressBar.visibility = View.VISIBLE
        networkManager.getReposList(this);
        return v
    }

    override fun onItemSelected(item: GHRepoItem) {
        val goToDetail = Intent(activity, DetailActivity::class.java)
        goToDetail.putExtra(BaseActivity.EXTRAOBJECTKEY, item)
        startActivity(goToDetail)
    }

    override fun onEndScroll() {
        if (networkManager.hasNext) {
            progressBar.visibility = View.VISIBLE
            networkManager.getNextPage(this)
        }
    }

    override fun onReposReceived(repos: List<GHRepoItem>?) {
        progressBar.visibility = View.GONE
        if (repos != null) {
            adapter?.addNewItems(repos)
        }
    }
}

