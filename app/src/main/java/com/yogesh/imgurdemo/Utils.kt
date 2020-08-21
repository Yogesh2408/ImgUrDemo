package com.yogesh.imgurdemo

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast

object Utils {

    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }

    @JvmStatic
    fun showToast(context: Context, string: String) {
        Toast.makeText(
            context, string,
            Toast.LENGTH_SHORT
        ).show()
    }
}