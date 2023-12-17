package com.sudo248.sudoo.ui.activity.auth.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sudo248.sudoo.ui.activity.auth.fragment.sign_in.SignInFragment
import com.sudo248.sudoo.ui.activity.auth.fragment.sign_up.SignUpFragment


/**
 * **Created by**
 *
 * @author *Sudo248*
 * @since 21:45 - 07/03/2023
 */
class PageAuthAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> SignInFragment.newInstance()
        1 -> SignUpFragment.newInstance()
        else -> throw IndexOutOfBoundsException("Tab has size 2")
    }

}