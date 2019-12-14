package be.amellaa.shoppinglist.activities.shoppingListActivity

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.amellaa.shoppinglist.R
import be.amellaa.shoppinglist.activities.shoppingListActivity.itemFragment.BaseItemFragment
import be.amellaa.shoppinglist.models.ShoppingItem

class ShoppingItemAdapter(val values : ArrayList<ShoppingItem>) : RecyclerView.Adapter<ShoppingItemAdapter.ListRowHolder>()
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
        return ListRowHolder(
            layoutInflater,
            parent
        );
    }

    class ListRowHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(
        R.layout.holder_listitem, parent, false))
    {
        lateinit var mShoppingItem: ShoppingItem
        lateinit var mNameTextView : TextView
        lateinit var mQtyTextView : TextView
        lateinit var mCheckBox: CheckBox
        val frag = (parent.context as ShoppingItemActivity).frag as BaseItemFragment

        init {
            mNameTextView = itemView.findViewById(R.id.itemName)
            mQtyTextView = itemView.findViewById(R.id.itemQty)
            mCheckBox = itemView.findViewById(R.id.itemCheck)
            mCheckBox.setOnClickListener { _ ->
                if(parent.context is ShoppingItemActivity){
                    frag.onCheckBoxChecked(mShoppingItem, mCheckBox.isChecked)
                }
            }
            itemView.setOnLongClickListener {
                frag.openListChoiceDialog(mShoppingItem.id)
            }
        }

        fun bind(shoppingItem: ShoppingItem)
        {
            mShoppingItem = shoppingItem
            mNameTextView.text = shoppingItem.name;
            mQtyTextView.text = shoppingItem.qty.toString()
            mCheckBox.isChecked = shoppingItem.checked
        }

    }

}