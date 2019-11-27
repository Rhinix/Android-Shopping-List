package be.amellaa.shoppinglist.activities.shoppingListActivity

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import be.amellaa.shoppinglist.R

class AddShoppingListDialog : DialogFragment(){

    private lateinit var listener: DialogListener
    lateinit var mEditText : EditText

    interface DialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment)
    }

    fun setListener(listener: DialogListener){
        this.listener = listener;
    }

    fun getEditText() : String{
        return mEditText.text.toString()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater;
            val view = inflater.inflate(R.layout.addshoppinglist_fragment, null)
            mEditText = view.findViewById(R.id.addShoppingListName)
            builder.setView(view)
                .setTitle(R.string.addShoppingListTitle)
                .setPositiveButton(R.string.addShoppingOk,
                    DialogInterface.OnClickListener { _, _ ->
                        listener.onDialogPositiveClick(this)
                    })
                .setNegativeButton(R.string.addShoppingCancel,
                    DialogInterface.OnClickListener { dialog, _ ->
                        dialog.cancel()
                    })
            builder.create()

        } ?: throw IllegalStateException("Activity cannot be null")
    }



}