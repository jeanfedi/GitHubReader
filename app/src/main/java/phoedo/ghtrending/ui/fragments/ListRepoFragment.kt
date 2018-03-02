package phoedo.ghtrending.ui.fragments

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.util.Pair
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_list_repo.*
import kotlinx.android.synthetic.main.fragment_list_repo.view.*
import kotlinx.android.synthetic.main.list_item_ghrepo.view.*
import phoedo.ghtrending.R
import phoedo.ghtrending.model.GHRepoItem
import phoedo.ghtrending.networking.NetworkManager
import phoedo.ghtrending.ui.activities.BaseActivity
import phoedo.ghtrending.ui.activities.DetailActivity
import phoedo.ghtrending.ui.activities.MainActivity
import phoedo.ghtrending.ui.adapters.GHRecycleListAdapter

/**
 * A placeholder fragment containing a simple view.
 */
class ListRepoFragment : Fragment(), GHRecycleListAdapter.GHRecyclerViewListener, NetworkManager.ReposListListener, MainActivity.SearchListener {

    var networkManager = NetworkManager()
    var adapter: GHRecycleListAdapter? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_list_repo, container, false)
        v.progressbar.indeterminateDrawable.setColorFilter(ContextCompat.getColor(context, R.color.textDarkGray), PorterDuff.Mode.SRC_IN)
        adapter = GHRecycleListAdapter(context,this)
        v.recyclerList.layoutManager = LinearLayoutManager(context);
        v.recyclerList.adapter = adapter;
        v.progressbar.visibility = View.VISIBLE
        networkManager.getReposList(this);
        if (activity is MainActivity) {
            (activity as MainActivity).searchListener = this
        }

            return v
    }

    override fun onItemSelected(item: GHRepoItem, holder: GHRecycleListAdapter.ViewHolder) {
        val goToDetail = Intent(activity, DetailActivity::class.java)
        goToDetail.putExtra(BaseActivity.EXTRAOBJECTKEY, item)

        val transition1 = Pair.create(holder.itemView.nameLabel as View, BaseActivity.NAMETRANSITION)
        val transition2 = Pair.create(holder.itemView.starsLabel as View, BaseActivity.STARSTRANSITION)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transition1, transition2)
        startActivity(goToDetail,options.toBundle())
    }

    override fun onEndScroll() {
        if (networkManager.hasNext) {
            progressbar.visibility = View.VISIBLE
            networkManager.getNextPage(this)
        }
    }

    override fun onReposReceived(repos: List<GHRepoItem>?) {
        progressbar.visibility = View.GONE
        if (repos != null) {
            adapter?.addNewItems(repos)
        }
    }

    override fun onSearch(query: String?) {
        networkManager.setQuery(query)
        adapter?.clearData()
        progressbar.visibility=View.VISIBLE
        networkManager.getReposList(this)
    }
}

