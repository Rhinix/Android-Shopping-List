package be.amellaa.shoppinglist.activities.shoppingListActivity.dialogfragment

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import be.amellaa.shoppinglist.R

/**
 *  DialogFragment with on entry, to create or update lists
 */
class WithEntryDialogFragment(var idStringTitle: Int) : DialogFragment(){

    private lateinit var listener: DialogListener
    lateinit var mEditText : EditText

    fun setListener(listener: DialogListener){
        this.listener = listener;
    }

    fun getName() : String{
        return mEditText.text.toString()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater;
            val view = inflater.inflate(R.layout.with_entry_dialog, null)
            mEditText = view.findViewById(R.id.entryShoppingListName)
            builder.setView(view)
                .setTitle(idStringTitle)
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