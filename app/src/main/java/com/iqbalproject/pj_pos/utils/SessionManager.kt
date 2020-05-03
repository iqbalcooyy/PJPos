package com.iqbalproject.pj_pos.utils

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.iqbalproject.pj_pos.ui.LoginActivity

class SessionManager(var context: Context) {
    private var PRIVATE_MODE: Int = 0
    var prefLogin: SharedPreferences = context.getSharedPreferences(Constants.PREF_LOGIN, PRIVATE_MODE)
    var editorLogin: SharedPreferences.Editor = prefLogin.edit()

    fun createLoginSession(userId: String, name: String, position: String) {
        editorLogin.putBoolean(Constants.IS_LOGIN, true)
        editorLogin.putString(Constants.KEY_ID, userId)
        editorLogin.putString(Constants.KEY_NAME, name)
        editorLogin.putString(Constants.KEY_POSITION, position)
        editorLogin.commit()
    }

    fun checkLogin() {
        if (!this.isLoggedIn()) {
            val i = Intent(context, LoginActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(i)
        }
    }

    fun getUserDetails(): HashMap<String, String> {
        val user: Map<String, String> = HashMap<String, String>()
        (user as HashMap)[Constants.KEY_ID] = prefLogin.getString(Constants.KEY_ID, null).toString()
        (user as HashMap)[Constants.KEY_NAME] = prefLogin.getString(Constants.KEY_NAME, null).toString()
        (user as HashMap)[Constants.KEY_POSITION] = prefLogin.getString(Constants.KEY_POSITION, null).toString()

        return user
    }

    fun logoutUser() {
        editorLogin.clear()
        editorLogin.commit()

        val i = Intent(context, LoginActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(i)
    }

    fun isLoggedIn(): Boolean {
        return prefLogin.getBoolean(Constants.IS_LOGIN, false)
    }
}