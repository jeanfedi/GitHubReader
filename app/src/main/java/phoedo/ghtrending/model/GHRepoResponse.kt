package phoedo.ghtrending.model

import phoedo.ghtrending.networking.GHSevices
import java.io.Serializable

/**
 * Created by phoedo on 24/02/18.
 */

data class GHRepoResponse(
        val total_count: Int,
        val incomplete_results: Boolean,
        val items: List<GHRepoItem>
) : Serializable

