package be.amellaa.shoppinglist.activities.loginActivity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import be.amellaa.shoppinglist.ProgressDialog
import be.amellaa.shoppinglist.dao.ShoppingListDAO
import be.amellaa.shoppinglist.R
import be.amellaa.shoppinglist.activities.ProcessResponseCode
import be.amellaa.shoppinglist.activities.shoppingListActivity.ShoppingListActivity
import be.amellaa.shoppinglist.dao.CommunicationInterface
import be.amellaa.shoppinglist.models.User

class LoginActivity : Activity(), ProcessResponseCode {


    lateinit var mLinkSignup: TextView
    lateinit var mLoginButton: Button
    lateinit var mEmailEditText: EditText
    lateinit var mPasswordEditText: EditText
    lateinit var mProgressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)
        initializeComponent()
        setListeners()
    }

    private fun initializeComponent() {
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

            ShoppingListDAO.instance.login(user, object : CommunicationInterface {
                override fun communicateACode(code: Int) {
                    runOnUiThread {
                        processCode(code)
                    }
                }
            })
        } else {
            Toast.makeText(this, "Fields are not valid", Toast.LENGTH_SHORT).show()
        }
    }


    override fun processCode(code: Int) {
        mProgressDialog.dismiss()
        when (code) {
            200 -> changeActivity(applicationContext, ShoppingListActivity::class.java)
            401 -> Toast.makeText(applicationContext, "Authentication failed", Toast.LENGTH_LONG)
        }
    }

    private fun changeActivity(context: Context, cls: Class<*>) {
        val intent = Intent(context, cls)
        startActivity(intent)
        finish()
    }

    private fun areFieldsValid(): Boolean {
        return this.mEmailEditText.length() > 0 && this.mPasswordEditText.length() > 1
    }

}