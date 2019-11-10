package be.amellaa.shoppinglist

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.amellaa.shoppinglist.models.ShoppingItem

class ItemListAdapter(val values : ArrayList<ShoppingItem>) : RecyclerView.Adapter<ItemListAdapter.ListRowHolder>()
{

    override fun getItemCount(): Int {
        return values.size;
    }

    override fun onBindViewHolder(holder: ListRowHolder, position: Int) {
        val shoppingList : ShoppingItem = values[position]
        holder.bind(shoppingList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListRowHolder {
        var layoutInflater : LayoutInflater = LayoutInflater.from(parent.context)
        return ListRowHolder(layoutInflater, parent);
    }

    class ListRowHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.holder_listitem, parent, false))
    {
        lateinit var mShoppingItem: ShoppingItem
        lateinit var mNameTextView : TextView
        lateinit var mIdTextView : TextView

        init {
            mNameTextView = itemView.findViewById(R.id.itemName)
            mIdTextView = itemView.findViewById(R.id.itemId)
        }

        fun bind(shoppingItem: ShoppingItem)
        {
            mShoppingItem = shoppingItem
            mNameTextView.text = shoppingItem.name;
            mIdTextView.text = shoppingItem.id;
        }

    }

}