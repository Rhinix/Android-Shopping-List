package be.amellaa.shoppinglist.activities.shoppingListActivity.dialogfragment

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import be.amellaa.shoppinglist.R

/**
 *  DialogFragment with two entry, to create or update items in a list
 */
class WithEntryAndQuantityDialogFragment(var idStringTitle: Int) : DialogFragment(){

    private lateinit var listener: DialogListener
    lateinit var mEditTextName : EditText
    lateinit var mEditTextQty : EditText
    lateinit var itemId : String

    fun setListener(listener: DialogListener){
        this.listener = listener;
    }

    fun getName() : String{
        return mEditTextName.text.toString()
    }

    fun getQuantity() : String{
        return mEditTextQty.text.toString()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater;
            val view = inflater.inflate(R.layout.with_entry_quantity_dialog, null)
            mEditTextName = view.findViewById(R.id.entryShoppingItemName)
            mEditTextQty = view.findViewById(R.id.entryShoppingItemQty)
            builder.setView(view)
                .setTitle(idStringTitle)
                .setPositiveButton(
                    R.string.addShoppingOk,
                    DialogInterface.OnClickListener { _, _ ->
                        listener.onDialogPositiveClick(this)
                    })
                .setNegativeButton(
                    R.string.addShoppingCancel,
                    DialogInterface.OnClickListener { dialog, _ ->
                        dialog.cancel()
                    })
            builder.create()

        } ?: throw IllegalStateException("Activity cannot be null")
    }


}