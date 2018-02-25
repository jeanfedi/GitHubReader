package phoedo.ghtrending.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.squareup.picasso.Picasso
import phoedo.ghtrending.R
import phoedo.ghtrending.model.GHRepoItem
import phoedo.ghtrending.networking.NetworkManager
import phoedo.ghtrending.ui.activities.BaseActivity
import phoedo.ghtrending.utils.picasso.CircleTransform

/**
 * A placeholder fragment containing a simple view.
 */
class DetailRepoFragment : Fragment() {

    @BindView(R.id.userLabel)
    lateinit var userLabel: TextView

    @BindView(R.id.nameLabel)
    lateinit var nameLabel: TextView

    @BindView(R.id.starsLabel)
    lateinit var starsLabel: TextView

    @BindView(R.id.forksLabel)
    lateinit var forksLabel: TextView

    @BindView(R.id.profileIcon)
    lateinit var profileIcon: ImageView

    @BindView(R.id.readmeContent)
    lateinit var readmeContent: TextView

    var repoDetails: GHRepoItem? = null;
    val networkManager = NetworkManager()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_detail_repo, container, false)
        ButterKnife.bind(this, v)

        if (arguments.containsKey(BaseActivity.EXTRAOBJECTKEY)) {
            var extraObject = arguments.getSerializable(BaseActivity.EXTRAOBJECTKEY);
            if (extraObject is GHRepoItem) {
                repoDetails = extraObject;
                activity.setTitle(repoDetails?.name)
            }
        }

        userLabel.setText(repoDetails?.owner?.login)
        nameLabel.setText(repoDetails?.name)
        starsLabel.setText(resources.getString(R.string.stars_label, repoDetails?.stargazers_count))
        forksLabel.setText(resources.getString(R.string.forks_label, repoDetails?.forks_count))


        Picasso.with(context)
                .load(repoDetails?.owner?.avatar_url)
                .transform(CircleTransform())
                .into(profileIcon)

        networkManager.getRepoReadme(repoDetails!!, object : NetworkManager.RepoReadMeListener {
            override fun onRepoReadMeReceved(readme: String?) {
                readmeContent.setText(readme)
            }
        })

        return v;
    }
}