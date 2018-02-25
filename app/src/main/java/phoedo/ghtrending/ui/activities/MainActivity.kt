package phoedo.ghtrending.ui.activities

import android.os.Bundle
import android.view.Menu
import phoedo.ghtrending.R
import android.view.MenuInflater
import android.support.v4.widget.SearchViewCompat.setSearchableInfo
import android.content.Context.SEARCH_SERVICE
import android.app.SearchManager
import android.view.View
import android.widget.SearchView


class MainActivity : BaseActivity() {
    var searchListener:SearchListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchListener?.onSearch(query)
                return true
            }
            override fun onQueryTextChange(query: String?): Boolean {
                return true
            }
        })
       searchView.setOnQueryTextFocusChangeListener(object : View.OnFocusChangeListener{
           override fun onFocusChange(v: View?, hasFocus: Boolean) {
               if (!hasFocus){
                   searchView.setQuery(null,true)
                   searchListener?.onSearch(null)

               }
           }
       })
        return true
    }

    interface SearchListener{
        fun onSearch(query:String?)
    }


}
