package phoedo.ghtrending.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.text.Html
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detail_repo.*
import kotlinx.android.synthetic.main.fragment_detail_repo.view.*
import phoedo.ghtrending.R
import phoedo.ghtrending.model.GHRepoItem
import phoedo.ghtrending.networking.NetworkManager
import phoedo.ghtrending.ui.activities.BaseActivity
import phoedo.ghtrending.utils.picasso.CircleTransform

class DetailRepoFragment : Fragment() {

    var repoDetails: GHRepoItem? = null;
    val networkManager = NetworkManager()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_detail_repo, container, false)
        ViewCompat.setTransitionName(v.nameLabel, BaseActivity.NAMETRANSITION)
        ViewCompat.setTransitionName(v.starsLabel, BaseActivity.STARSTRANSITION)

        if (arguments.containsKey(BaseActivity.EXTRAOBJECTKEY)) {
            var extraObject = arguments.getSerializable(BaseActivity.EXTRAOBJECTKEY);
            if (extraObject is GHRepoItem) {
                repoDetails = extraObject;
                activity.setTitle(repoDetails?.name)
            }
        }

        v.userLabel.setText(repoDetails?.owner?.login)
        v.nameLabel.setText(repoDetails?.name)
        v.starsLabel.setText(resources.getString(R.string.stars_label, repoDetails?.stargazers_count))
        v.forksLabel.setText(resources.getString(R.string.forks_label, repoDetails?.forks_count))

        Picasso.with(context)
                .load(repoDetails?.owner?.avatar_url)
                .transform(CircleTransform())
                .into(v.profileIcon)

        networkManager.getRepoReadme(repoDetails!!, object : NetworkManager.RepoReadMeListener {
            override fun onRepoReadMeReceved(readme: String?) {
                if (!TextUtils.isEmpty(readme)) {
                    v.readmeContent.setText(Html.fromHtml(readme))
                }
            }
        })

        return v;
    }
}