package phoedo.ghtrending.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item_ghrepo.view.*
import phoedo.ghtrending.R
import phoedo.ghtrending.model.GHRepoItem

/**
 * Created by phoedo on 24/02/18.
 */
class GHRecycleListAdapter(private val context: Context, private val listener: GHRecyclerViewListener) : RecyclerView.Adapter<GHRecycleListAdapter.ViewHolder>() {
    var items = mutableListOf<GHRepoItem>();

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val container = LayoutInflater.from(context).inflate(R.layout.list_item_ghrepo, null)
        return ViewHolder(container)

    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.populate(items[position])
        holder?.itemView?.setOnClickListener(View.OnClickListener { listener.onItemSelected(items[position], holder) })
        if (position == items.size - 1) {
            listener.onEndScroll();
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


    fun addNewItems(list: List<GHRepoItem>) {
        items.addAll(list)
        notifyDataSetChanged()
    }

    fun clearData() {
        items = ArrayList<GHRepoItem>();
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun populate(item: GHRepoItem) {
            itemView.nameLabel.setText(item.name)
            itemView.starsLabel.setText(context.resources.getString(R.string.stars_label, item.stargazers_count))
            itemView.descriptionLabel.setText(item.description)
        }
    }

    interface GHRecyclerViewListener {
        fun onItemSelected(item: GHRepoItem, holder: ViewHolder)
        fun onEndScroll()
    }


}