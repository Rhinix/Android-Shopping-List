package be.amellaa.shoppinglist

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context

/**
 *  A simple ProgressDialog
 */
class ProgressDialog {
    private val dialog: Dialog

    constructor(context: Context) {
        var builder = AlertDialog.Builder(context)
        builder.setView(R.layout.progress)
        dialog = builder.create()
    }

    fun show() {
        dialog.show()
    }

    fun dismiss() {
        dialog.dismiss()
    }
}