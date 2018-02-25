package phoedo.ghtrending.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import phoedo.ghtrending.R
import phoedo.ghtrending.ui.activities.BaseActivity
import phoedo.ghtrending.ui.activities.DetailActivity
import phoedo.ghtrending.ui.adapters.GHRecycleListAdapter
import phoedo.ghtrending.model.GHRepoItem
import phoedo.ghtrending.networking.NetworkManager

/**
 * A placeholder fragment containing a simple view.
 */
class ListRepoFragment : Fragment(), GHRecycleListAdapter.GHItemSelectedListener {

    var networkManager: NetworkManager = NetworkManager(null);

    @BindView(R.id.recyclerList)
    lateinit var listView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_list_repo, container, false)
        ButterKnife.bind(this,v);
        val adapter = GHRecycleListAdapter(context,this);
        listView.layoutManager = LinearLayoutManager(context);
        listView.adapter = adapter;

        networkManager = NetworkManager(object :NetworkManager.ReposListListener {
            override fun onReposReceived(repos: List<GHRepoItem>?) {
                if (repos!=null) {
                    adapter.addNewItems(repos)
                }
            }
        })
        networkManager.getReposList();

        return v;
    }

    override fun onItemSelected(item: GHRepoItem) {
        Toast.makeText(context,item.name,Toast.LENGTH_LONG).show()
        val goToDetail = Intent(activity,DetailActivity::class.java)
        goToDetail.putExtra(BaseActivity.EXTRAOBJECTKEY,item)
        startActivity(goToDetail)
    }
}

