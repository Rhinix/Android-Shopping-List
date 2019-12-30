package be.amellaa.shoppinglist.activities.loginActivity

import android.accounts.Account
import android.accounts.AccountManager
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import be.amellaa.shoppinglist.Authenticator.AccountAuthenticator
import be.amellaa.shoppinglist.ProgressDialog
import be.amellaa.shoppinglist.R
import be.amellaa.shoppinglist.utils.NetworkConnectionChecker
import be.amellaa.shoppinglist.activities.shoppingListActivity.ShoppingListActivity
import be.amellaa.shoppinglist.dto.DataFetcher
import be.amellaa.shoppinglist.dto.ICommunicateData
import be.amellaa.shoppinglist.dto.ShoppingListDTO
import be.amellaa.shoppinglist.models.User

/**
 *  Activity responsible for login users
 */
class LoginActivity : Activity(), ICommunicateData<User> {


    lateinit var mLinkSignup: TextView
    lateinit var mLoginButton: Button
    lateinit var mEmailEditText: EditText
    lateinit var mPasswordEditText: EditText
    lateinit var mProgressDialog: ProgressDialog
    lateinit var mDataFetcher: DataFetcher


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!NetworkConnectionChecker.isNetworkAvailable(this)) {
            createAlertDialog()
        } else {
            checkForLoggedAccount()
        }
        setContentView(R.layout.login_layout)
        initializeComponent()
        setListeners()
    }

    private fun createAlertDialog() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.network_connection)
        builder.setCancelable(false)
        builder.setMessage(R.string.network_connection_message)
        builder.setPositiveButton(R.string.refresh) { _, _ -> }
        builder.setNegativeButton(R.string.cancel) { _, _ -> finish() }

        val dialog = builder.create()
        dialog.show()

        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            .setOnClickListener { v ->
                if (NetworkConnectionChecker.isNetworkAvailable(v.context)) {
                    checkForLoggedAccount()
                    changeActivity(v.context, LoginActivity::class.java)
                }
            }
    }


    private fun checkForLoggedAccount() {
        var am = AccountManager.get(this)
        var accounts = am.getAccountsByType(AccountAuthenticator.ACCOUNT_TYPE)

        if (accounts.isNotEmpty()) {
            ShoppingListDTO.TOKEN = am.peekAuthToken(accounts[0], AccountAuthenticator.TOKEN_TYPE)
            changeActivity(applicationContext, ShoppingListActivity::class.java)
        }
    }

    private fun initializeComponent() {
        this.mDataFetcher = DataFetcher(this)
        this.mLinkSignup = findViewById(R.id.link_signup)
        this.mLoginButton = findViewById(R.id.login_button)
        this.mEmailEditText = findViewById(R.id.input_email)
        this.mPasswordEditText = findViewById(R.id.input_password)
        this.mProgressDialog = ProgressDialog(this)
    }

    private fun setListeners() {
        mLinkSignup.setOnClickListener { changeActivity(this, SignUpActivity::class.java) }
        mLoginButton.setOnClickListener { loginButtonAction() }
    }

    private fun loginButtonAction() {
        if (areFieldsValid()) {
            mProgressDialog.show()

            val email = mEmailEditText.text.toString()
            val password = mPasswordEditText.text.toString()

            val user = User(email, password)

            mDataFetcher.login(user)
        } else {
            Toast.makeText(this, "Fields are not valid", Toast.LENGTH_SHORT).show()
        }
    }

    private fun areFieldsValid(): Boolean {
        return this.mEmailEditText.length() > 0 && this.mPasswordEditText.length() > 1
    }

    override fun communicateCode(code: Int) {
        runOnUiThread {
            mProgressDialog.dismiss()
            when (code) {
                200 -> changeActivity(applicationContext, ShoppingListActivity::class.java)
                401 -> Toast.makeText(
                    applicationContext,
                    "Authentication failed",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun changeActivity(context: Context, cls: Class<*>) {
        val intent = Intent(context, cls)
        startActivity(intent)
        finish()
    }

    override fun communicateData(data: User) {
        val account = Account(data.email, AccountAuthenticator.ACCOUNT_TYPE)
        val am = AccountManager.get(this)
        am.addAccountExplicitly(account, data.password, null)
        am.setAuthToken(account, AccountAuthenticator.TOKEN_TYPE, data.Token)
        ShoppingListDTO.TOKEN = data.Token
    }

}