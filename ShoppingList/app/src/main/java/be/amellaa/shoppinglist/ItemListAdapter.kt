package be.amellaa.shoppinglist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import be.amellaa.shoppinglist.models.ShoppingItem

class ItemListAdapter(context: Context, val arrayListDetails:ArrayList<ShoppingItem>) : BaseAdapter()
{

    private val layoutInflater : LayoutInflater = LayoutInflater.from(context)

    override fun getItem(position: Int): Any {
        return arrayListDetails[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return arrayListDetails.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view: View?
        val listRowHolder: ListRowHolder
        if (convertView == null) {
            view = this.layoutInflater.inflate(R.layout.adapter_shoppinglist, parent, false)
            listRowHolder = ListRowHolder(view)
            if (view != null) {
                view.tag = listRowHolder
            }
        } else {
            view = convertView
            listRowHolder = view.tag as ListRowHolder
        }

        listRowHolder.tvName.text = arrayListDetails.get(position).name
        listRowHolder.tvId.text = arrayListDetails.get(position).id
        return view
    }

    private class ListRowHolder(row: View?) {
        public val tvName: TextView
        public val tvId: TextView
        public val linearLayout: LinearLayout

        init {
            this.tvId = row?.findViewById<TextView>(R.id.tvId) as TextView
            this.tvName = row?.findViewById<TextView>(R.id.tvName) as TextView
            this.linearLayout = row?.findViewById<LinearLayout>(R.id.linearLayout) as LinearLayout
        }
    }

}