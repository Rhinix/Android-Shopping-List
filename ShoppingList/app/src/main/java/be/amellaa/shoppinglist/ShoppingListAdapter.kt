package be.amellaa.shoppinglist

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import be.amellaa.shoppinglist.models.ShoppingList
import com.google.android.material.behavior.SwipeDismissBehavior

class ShoppingListAdapter(context: Context, val arrayListDetails:ArrayList<ShoppingList>) : BaseAdapter()
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

        listRowHolder.shoppingListName.text = arrayListDetails[position].name
        //listRowHolder.shoppingListId.text = arrayListDetails[position].id
        val swipe : SwipeDismissBehavior<CardView> = SwipeDismissBehavior()
        swipe.setSwipeDirection(SwipeDismissBehavior.SWIPE_DIRECTION_START_TO_END)
        swipe.setListener(object : SwipeDismissBehavior.OnDismissListener{
            override fun onDragStateChanged(state: Int) {
                Log.d("swipe", "onDragStateChanged: state=$state")
            }

            override fun onDismiss(view: View) {
                Log.d("swipe", "onDismiss")
            }

        })
        (listRowHolder.linearLayout.layoutParams as CoordinatorLayout.LayoutParams).behavior = swipe
        listRowHolder.coordinatorLayout.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> Log.d("swipe", "ACTION_DOWN")//Do Something
                    MotionEvent.ACTION_UP -> v?.performClick()
                }

                return v?.onTouchEvent(event) ?: true
            }
        })
        listRowHolder.coordinatorLayout.setOnS
        //listRowHolder.coordinatorLayout.setOnTouchListener(View.OnTouchListener { v, event -> swipe.onTouchEvent(listRowHolder.coordinatorLayout, listRowHolder.linearLayout, event) })
        return view
    }

    private class ListRowHolder(row: View?) {

        val shoppingListName: TextView
        //val shoppingListId: TextView
        val linearLayout: CardView
        val coordinatorLayout : CoordinatorLayout

        init {
            //this.shoppingListId = row?.findViewById<TextView>(R.id.shoppingListId) as TextView
            this.shoppingListName = row?.findViewById<TextView>(R.id.shoppingListName) as TextView
            this.linearLayout = row.findViewById<CardView>(R.id.cardShoppingList) as CardView
            this.coordinatorLayout = row.findViewById(R.id.coordinatorShoppingList) as CoordinatorLayout
        }
    }

}