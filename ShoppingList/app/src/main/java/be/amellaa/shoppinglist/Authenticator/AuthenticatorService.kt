package be.amellaa.shoppinglist.Authenticator

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 *  Service for authentication
 */
class AuthenticatorService : Service() {
    private lateinit var mAuthenticator: AccountAuthenticator

    override fun onCreate() {
        mAuthenticator = AccountAuthenticator(this)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return mAuthenticator.iBinder
    }
}