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
import be.amellaa.shoppinglist.R
import be.amellaa.shoppinglist.dto.DataFetcher
import be.amellaa.shoppinglist.dto.ICommunicateCode
import be.amellaa.shoppinglist.models.User

class SignUpActivity : Activity(), ICommunicateCode {


    lateinit var mEmailEditText: EditText
    lateinit var mPasswordEditText: EditText
    lateinit var mConfirmPasswordText: EditText
    lateinit var mSignUpButton: Button
    lateinit var mLinkLogin: TextView
    lateinit var mProgressDialog: ProgressDialog
    lateinit var mDataFetcher: DataFetcher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_layout)
        initializeComponent()
        setListeners()
    }

    private fun initializeComponent() {
        mDataFetcher = DataFetcher(this)
        mEmailEditText = findViewById(R.id.input_email)
        mPasswordEditText = findViewById(R.id.input_password)
        mConfirmPasswordText = findViewById(R.id.input_confirm_password)
        mSignUpButton = findViewById(R.id.signup_button)
        mLinkLogin = findViewById(R.id.link_login)
        this.mProgressDialog = ProgressDialog(this)
    }

    private fun setListeners() {
        mLinkLogin.setOnClickListener { changeActivity(this, LoginActivity::class.java) }
        mSignUpButton.setOnClickListener { signUpButtonAction() }
    }

    private fun changeActivity(context: Context, cls: Class<*>) {
        val intent = Intent(context, cls)
        startActivity(intent)
        finish()
    }

    private fun signUpButtonAction() {
        if (!areFieldsValid()) {
            makeToast("All fields are required", Toast.LENGTH_LONG)
            return
        }

        if (!isConfirmPasswordCorrect()) {
            makeToast("Password and confirm password must match", Toast.LENGTH_LONG)
            return
        }

        mProgressDialog.show()

        val email = mEmailEditText.text.toString()
        val password = mPasswordEditText.text.toString()

        val user = User(email, password)

        mDataFetcher.signup(user)
    }

    private fun areFieldsValid(): Boolean {
        return mEmailEditText.text.isNotEmpty()
                && mPasswordEditText.text.isNotEmpty()
                && mConfirmPasswordText.text.isNotEmpty()
    }

    private fun isConfirmPasswordCorrect(): Boolean {
        return mPasswordEditText.text.toString().equals(mConfirmPasswordText.text.toString())
    }

    override fun communicateCode(code: Int) {
        runOnUiThread {
            mProgressDialog.dismiss()
            when (code) {
                200 -> changeActivity(applicationContext, LoginActivity::class.java)
                409 -> makeToast("This email is already registered", Toast.LENGTH_LONG)
            }
        }
    }

    private fun makeToast(message: String, duration: Int) {
        Toast.makeText(this, message, duration).show()
    }

}