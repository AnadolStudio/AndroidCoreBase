package com.anadolstudio.core.util.share

import com.anadolstudio.core.R

object SharedActionFactory {

    fun instance(): List<SharedAction.SharedItem> = listOf(
            Empty(),
            VK(),
            Instagram(),
            Facebook(),
            Messenger(),
            WhatsApp(),
            Twitter()
    )

    class Empty : SharedAction.SharedItem(R.drawable.ic_share, null)
    class VK : SharedAction.SharedItem(R.drawable.share_vk, AppPackages.VK)
    class Instagram : SharedAction.SharedItem(R.drawable.share_instagam, AppPackages.INSTAGRAM)
    class Facebook : SharedAction.SharedItem(R.drawable.share_facebook, AppPackages.FACEBOOK)
    class Messenger : SharedAction.SharedItem(R.drawable.share_messenger, AppPackages.MESSENGER)
    class WhatsApp : SharedAction.SharedItem(R.drawable.share_whats_app, AppPackages.WHATS_APP)
    class Twitter : SharedAction.SharedItem(R.drawable.share_twitter, AppPackages.TWITTER)
}
