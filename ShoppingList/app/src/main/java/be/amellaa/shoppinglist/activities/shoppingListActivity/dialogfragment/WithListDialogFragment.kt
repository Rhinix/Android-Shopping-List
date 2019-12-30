package be.amellaa.shoppinglist.activities.shoppingListActivity.dialogfragment

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import be.amellaa.shoppinglist.R

/**
 *  DialogFragment with a ListView, to display options on long click on a item
 */
class WithListDialogFragment(var idStringTitle: Int, val itemId: String) : DialogFragment(){

    private lateinit var listener: DialogListener
    lateinit var mEditText : EditText

    fun setListener(listener: DialogListener){
        this.listener = listener;
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater;
            val view = inflater.inflate(R.layout.with_list_dialog, null)
            val listView = view.findViewById<ListView>(R.id.dialog_list)
            var adapter = ArrayAdapter<String>(activity!!, R.layout.dialog_listitem_layout, R.id.dialogOptionText, arrayListOf("Modify", "Delete"))
            listView.adapter = adapter
            listView.setOnItemClickListener { _, view, _, _ ->
                val textView= view.findViewById<TextView>(R.id.dialogOptionText)
                listener.onDialogItemClick(textView.text.toString(), itemId)
                dialog.dismiss()
            }
            builder.setView(view)
                .setTitle(idStringTitle)
            builder.create()

        } ?: throw IllegalStateException("Activity cannot be null")
    }



}