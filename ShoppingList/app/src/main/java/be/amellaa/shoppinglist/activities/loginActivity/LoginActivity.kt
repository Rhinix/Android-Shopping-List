package be.amellaa.shoppinglist.activities.loginActivity

import android.accounts.Account
import android.accounts.AccountManager
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import be.amellaa.shoppinglist.ProgressDialog
import be.amellaa.shoppinglist.R
import be.amellaa.shoppinglist.activities.shoppingListActivity.ShoppingListActivity
import be.amellaa.shoppinglist.dto.DataFetcher
import be.amellaa.shoppinglist.dto.ICommunicateCode
import be.amellaa.shoppinglist.dto.ICommunicateData
import be.amellaa.shoppinglist.dto.ShoppingListDTO
import be.amellaa.shoppinglist.models.User

class LoginActivity : Activity(), ICommunicateData<User> {


    lateinit var mLinkSignup: TextView
    lateinit var mLoginButton: Button
    lateinit var mEmailEditText: EditText
    lateinit var mPasswordEditText: EditText
    lateinit var mProgressDialog: ProgressDialog
    lateinit var mDataFetcher: DataFetcher


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkForLoggedAccount()
        setContentView(R.layout.login_layout)
        initializeComponent()
        setListeners()
    }

    private fun checkForLoggedAccount() {
        var am = AccountManager.get(this)
        var accounts = am.getAccountsByType("ShoppingList")

        if (accounts.isNotEmpty()) {
            ShoppingListDTO.instance.TOKEN = am.peekAuthToken(accounts[0], "Token")
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
        val account = Account(data.email, "ShoppingList")
        val am = AccountManager.get(this)
        am.addAccountExplicitly(account, data.password, null)
        am.setAuthToken(account, "Token", data.Token)
        ShoppingListDTO.instance.TOKEN = data.Token
    }

}