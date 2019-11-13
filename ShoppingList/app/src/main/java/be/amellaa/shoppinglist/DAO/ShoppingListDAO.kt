package be.amellaa.shoppinglist.DAO

import be.amellaa.shoppinglist.models.ShoppingItem
import be.amellaa.shoppinglist.models.ShoppingList
import be.amellaa.shoppinglist.models.User
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.lang.Exception
import java.util.concurrent.CountDownLatch

class ShoppingListDAO {


    companion object {
        val instance = ShoppingListDAO()
        val httpClient = OkHttpClient()
        const val DOMAIN_URL = "http://192.168.1.42:3000"
        const val USER_LOGIN_URL = "/user/login/"
        const val USER_SIGNUP_URL = "/user/signup/"
        const val SHOPPINGLIST_URL = "/shoppingList/"
        const val MY_LIST_URL = "/shoppingList/MyLists/"
        const val SHARED_LIST_URL = "/shoppingList/SharedList/"
        var TOKEN = ""
    }

    fun login(user: User): Int {
        val body = FormBody.Builder()
            .add("email", user.email)
            .add("password", user.password)
            .build()

        val request = Request.Builder()
            .post(body)
            .url(DOMAIN_URL + USER_LOGIN_URL)
            .build()

        var returnCode = Int.MAX_VALUE

        val countDownLatch: CountDownLatch = CountDownLatch(1)
        httpClient.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {

                val res = response.body()!!.string()
                val jsonObject = JSONObject(res)

                if (response.code() == 200) {
                    TOKEN = jsonObject.getString("token")
                }

                returnCode = response.code()
                countDownLatch.countDown()
            }

            override fun onFailure(call: Call, e: IOException) {
                countDownLatch.countDown()
                throw e
            }

        })

        countDownLatch.await()
        return returnCode
    }

    fun signUp(newUser: User): Int {
        val body = FormBody.Builder()
            .add("email", newUser.email)
            .add("password", newUser.password)
            .build()

        val request = Request.Builder()
            .post(body)
            .url(DOMAIN_URL + USER_SIGNUP_URL)
            .build()

        var returnCode = Int.MAX_VALUE

        val countDownLatch: CountDownLatch = CountDownLatch(1)
        httpClient.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                returnCode = response.code()
                countDownLatch.countDown()
            }

            override fun onFailure(call: Call, e: IOException) {
                countDownLatch.countDown()
            }
        })

        countDownLatch.await()
        return returnCode
    }

    fun getMyList(): ArrayList<ShoppingList> {
        val request = Request.Builder()
            .get()
            .header("Authorization", "bearer $TOKEN")
            .url(DOMAIN_URL + MY_LIST_URL)
            .build()

        var newShoppingListArray = ArrayList<ShoppingList>()

        val countDownLatch: CountDownLatch = CountDownLatch(1)
        httpClient.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {

                var res = response.body()!!.string()

                var jsonArray = JSONArray(res)

                var newShoppingList: ShoppingList
                var jsonObject: JSONObject

                for (i in 0 until jsonArray.length()) {
                    jsonObject = jsonArray.getJSONObject(i)
                    newShoppingList = ShoppingList()
                    newShoppingList.id = jsonObject.getString("_id")
                    newShoppingList.name = jsonObject.getString("name")
                    newShoppingList.nbArticles = jsonObject.getInt("nbArticles")
                    newShoppingListArray.add(newShoppingList)
                }
                countDownLatch.countDown()
            }

            override fun onFailure(call: Call, e: IOException) {
                countDownLatch.countDown()
                throw e
            }

        })
        countDownLatch.await();
        return newShoppingListArray

    }

    fun getSharedList() {

    }

    fun saveList() {

    }

    fun getItemFromList(id: String): ArrayList<ShoppingItem> {
        val request = Request.Builder()
            .get()
            .header("Authorization", "bearer $TOKEN")
            .url(DOMAIN_URL + SHOPPINGLIST_URL + id)
            .build()
        var newItemList: ArrayList<ShoppingItem> = ArrayList()
        val countDownLatch: CountDownLatch = CountDownLatch(1)
        httpClient.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {

                var res = response.body()!!.string()

                var jsonArray = JSONObject(res).getJSONArray("articlesList")

                var newShoppingItem: ShoppingItem
                var jsonObject: JSONObject

                for (i in 0 until jsonArray.length()) {
                    jsonObject = jsonArray.getJSONObject(i)
                    newShoppingItem = ShoppingItem()
                    newShoppingItem.id = jsonObject.getString("_id")
                    newShoppingItem.name = jsonObject.getString("name")
                    newShoppingItem.qty = jsonObject.getInt("qty")
                    newItemList.add(newShoppingItem)
                }
                countDownLatch.countDown()
            }

            override fun onFailure(call: Call, e: IOException) {
                countDownLatch.countDown()
                throw e
            }

        })
        countDownLatch.await();
        return newItemList;
    }

    fun patchList() {

    }

    fun deleteList(id: String) {

    }

    fun setHeader(fieldName: String, value: String) {

    }
}