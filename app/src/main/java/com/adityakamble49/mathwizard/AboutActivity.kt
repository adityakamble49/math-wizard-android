package com.adityakamble49.mathwizard

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import com.adityakamble49.mathwizard.common.ChangelogDialog
import com.adityakamble49.mathwizard.utils.Constants
import com.afollestad.materialdialogs.internal.ThemeSingleton
import com.danielstone.materialaboutlibrary.MaterialAboutActivity
import com.danielstone.materialaboutlibrary.items.MaterialAboutActionItem
import com.danielstone.materialaboutlibrary.items.MaterialAboutTitleItem
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard
import com.danielstone.materialaboutlibrary.model.MaterialAboutList
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity


/**
 * About Activity
 *
 * @author Aditya Kamble
 * @since 12/20/2019
 */
class AboutActivity : MaterialAboutActivity() {

    override fun getActivityTitle(): CharSequence? = getString(R.string.about_math_wizard)

    override fun getMaterialAboutList(p0: Context): MaterialAboutList {

        val appCardBuilder = MaterialAboutCard.Builder()

        appCardBuilder.addItem(
            MaterialAboutTitleItem.Builder()
                .text(R.string.app_name)
                .icon(R.mipmap.ic_launcher)
                .build()
        )

        appCardBuilder.addItem(
            MaterialAboutActionItem.Builder()
                .text(R.string.version)
                .subText(BuildConfig.VERSION_NAME)
                .icon(R.drawable.ic_version)
                .build()
        )

        appCardBuilder.addItem(MaterialAboutActionItem.Builder()
            .text(R.string.changelog)
            .icon(R.drawable.ic_changelog)
            .setOnClickAction {
                var accentColor = ThemeSingleton.get().widgetColor
                if (accentColor == 0)
                    accentColor = ContextCompat.getColor(
                        this@AboutActivity,
                        R.color.colorAccent
                    )
                ChangelogDialog.create(accentColor)
                    .show(supportFragmentManager, "changelog")
            }
            .build())

        appCardBuilder.addItem(MaterialAboutActionItem.Builder()
            .text(R.string.license)
            .icon(R.drawable.ic_license)
            .setOnClickAction {
                startActivity(Intent(this, OssLicensesMenuActivity::class.java))
            }
            .build())


        val developerCardBuilder = MaterialAboutCard.Builder()
        developerCardBuilder.title(R.string.developer)

        developerCardBuilder.addItem(MaterialAboutActionItem.Builder()
            .text(R.string.developer_name)
            .subText(R.string.developer_country)
            .icon(R.drawable.ic_developer)
            .setOnClickAction { openUrl(Constants.ReferenceUrls.TWITTER_PROFILE) }
            .build())

        developerCardBuilder.addItem(MaterialAboutActionItem.Builder()
            .text(R.string.view_portfolio)
            .icon(R.drawable.ic_portfolio)
            .setOnClickAction { openUrl(Constants.ReferenceUrls.PERSONAL_WEBSITE) }
            .build())

        val creditsCardBuilder = MaterialAboutCard.Builder()
        creditsCardBuilder.title(R.string.credits)

        creditsCardBuilder.addItem(MaterialAboutActionItem.Builder()
            .text(R.string.credit_name_app_icon)
            .subText(R.string.credit_description_app_icon)
            .showIcon(false)
            .setOnClickAction { openUrl(Constants.ReferenceUrls.CREDIT_APP_ICON) }
            .build())

        val listBuilder = MaterialAboutList.Builder()
        listBuilder.addCard(appCardBuilder.build())
        listBuilder.addCard(developerCardBuilder.build())
        listBuilder.addCard(creditsCardBuilder.build())

        return listBuilder.build()
    }

    private fun openUrl(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }
}