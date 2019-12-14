package be.amellaa.shoppinglist.activities.shoppingListActivity

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.amellaa.shoppinglist.R
import be.amellaa.shoppinglist.activities.TabAdapter
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

        init {
            mNameTextView = itemView.findViewById(R.id.shoppingListName)
            itemView.setOnClickListener { _ ->
                val kind = getKindOfList(parent.context)
                val intent : Intent = Intent(parent.context, ShoppingItemActivity::class.java)
                intent.putExtra("listKind", kind)
                intent.putExtra("listId", mShoppingList.id)
                intent.putExtra("listName", mShoppingList.name)
                parent.context.startActivity(intent)
            }
        }

        fun getKindOfList(context : Context) : String?{
            if(context is ShoppingListActivity){
                val shoppingList : ShoppingListActivity  = context as ShoppingListActivity
                if(shoppingList.mViewPager.adapter is TabAdapter){
                    val adapter = shoppingList.mViewPager.adapter as TabAdapter
                    return adapter.getItem(shoppingList.mTabLayout.selectedTabPosition).javaClass.simpleName
                }
                else {
                    return "SharedFragment"
                }
            }
            else {
                return "SharedFragment"
            }
        }

        fun bind(shoppingList: ShoppingList)
        {
            mShoppingList = shoppingList
            mNameTextView.text = shoppingList.name;
        }
    }

}