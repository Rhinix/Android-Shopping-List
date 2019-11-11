package be.amellaa.shoppinglist.activities.shoppingListActivity

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.amellaa.shoppinglist.R
import be.amellaa.shoppinglist.models.ShoppingList

class ShoppingListAdapter(val values : ArrayList<ShoppingList>) : RecyclerView.Adapter<ShoppingListAdapter.ListRowHolder>()
{

    override fun getItemCount(): Int {
        return values.size;
    }

    override fun onBindViewHolder(holder: ListRowHolder, position: Int) {
        val shoppingList : ShoppingList = values[position]
        holder.bind(shoppingList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListRowHolder {
        var layoutInflater : LayoutInflater = LayoutInflater.from(parent.context)
        return ListRowHolder(
            layoutInflater,
            parent
        );
    }

    public class ListRowHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(
        R.layout.holder_shoppinglist, parent, false))
    {
        lateinit var mShoppingList: ShoppingList
        lateinit var mNameTextView : TextView
        lateinit var mIdTextView : TextView

        init {
            mNameTextView = itemView.findViewById(R.id.shoppingListName)
            mIdTextView = itemView.findViewById(R.id.shoppingListId)
            itemView.setOnClickListener { v ->
                val intent : Intent = Intent(parent.context, ItemListActivity::class.java)
                intent.putExtra("listId", mShoppingList.id)
                parent.context.startActivity(intent)
            }
        }

        fun bind(shoppingList: ShoppingList)
        {
            mShoppingList = shoppingList
            mNameTextView.text = shoppingList.name;
            mIdTextView.text = shoppingList.id;
        }
    }

}