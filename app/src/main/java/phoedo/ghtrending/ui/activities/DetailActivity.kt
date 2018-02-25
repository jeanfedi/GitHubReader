package phoedo.ghtrending.ui.activities

import android.os.Bundle
import phoedo.ghtrending.R

class DetailActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);


    }

}