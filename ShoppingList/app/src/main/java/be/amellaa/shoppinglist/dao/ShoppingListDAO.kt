package be.amellaa.shoppinglist.dao

import be.amellaa.shoppinglist.models.ShoppingItem
import be.amellaa.shoppinglist.models.ShoppingList
import be.amellaa.shoppinglist.models.User
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.lang.RuntimeException
import javax.net.ssl.*
import kotlin.collections.ArrayList
import android.R.string
import android.util.Log
import okhttp3.Response



class ShoppingListDAO {


    companion object {
        val instance = ShoppingListDAO()
        val httpClient = getOkHttpClient()
        const val DOMAIN_URL = "https://192.168.137.1:3000"
        const val USER_LOGIN_URL = "/user/login/"
        const val USER_SIGNUP_URL = "/user/signup/"
        const val SHOPPINGLIST_URL = "/shoppingList/"
        const val MY_LIST_URL = "/shoppingList/MyLists/"
        const val SHARED_LIST_URL = "/shoppingList/SharedLists/"
        var TOKEN = ""


        private fun getOkHttpClient(): OkHttpClient {
            try {
                val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                    override fun checkClientTrusted(
                        chain: Array<java.security.cert.X509Certificate>,
                        authType: String
                    ) {
                    }

                    override fun checkServerTrusted(
                        chain: Array<java.security.cert.X509Certificate>,
                        authType: String
                    ) {
                    }

                    override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                        return arrayOf()
                    }
                })

                // Install the all-trusting trust manager
                val sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null, trustAllCerts, java.security.SecureRandom())
                // Create an ssl socket factory with our all-trusting manager
                val sslSocketFactory = sslContext.socketFactory

                val builder = OkHttpClient.Builder()
                builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                builder.hostnameVerifier { _, _ -> true }

                return builder.build()
            } catch (e: Exception) {
                throw RuntimeException(e)
            }

        }
    }

    fun login(user: User, communicationInterface: CommunicationInterface){
        val body = FormBody.Builder()
            .add("email", user.email)
            .add("password", user.password)
            .build()

        val request = Request.Builder()
            .post(body)
            .url(DOMAIN_URL + USER_LOGIN_URL)
            .build()

        httpClient.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {

                val res = response.body()!!.string()
                val jsonObject = JSONObject(res)

                if (response.code() == 200) {
                    TOKEN = jsonObject.getString("token")
                }

                communicationInterface.communicateACode(response.code())
            }

            override fun onFailure(call: Call, e: IOException) {
                throw e
            }

        })
    }

    fun signUp(newUser: User, communicationInterface: CommunicationInterface){
        val body = FormBody.Builder()
            .add("email", newUser.email)
            .add("password", newUser.password)
            .build()

        val request = Request.Builder()
            .post(body)
            .url(DOMAIN_URL + USER_SIGNUP_URL)
            .build()

        httpClient.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                communicationInterface.communicateACode(response.code())
            }

            override fun onFailure(call: Call, e: IOException) {
            }
        })
    }

    fun getMyList(communicationInterface: CommunicationInterface){
        val request = Request.Builder()
            .get()
            .header("Authorization", "bearer $TOKEN")
            .url(DOMAIN_URL + MY_LIST_URL)
            .build()

        var newShoppingListArray = ArrayList<ShoppingList>()

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
                communicationInterface.communicateShoppingLists(newShoppingListArray)
            }

            override fun onFailure(call: Call, e: IOException) {
                throw e
            }

        })

    }

    fun getSharedList(communicationInterface: CommunicationInterface) {
        val request = Request.Builder()
            .get()
            .header("Authorization", "bearer $TOKEN")
            .url(DOMAIN_URL + SHARED_LIST_URL)
            .build()

        var newShoppingListArray = ArrayList<ShoppingList>()

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
                communicationInterface.communicateShoppingLists(newShoppingListArray)
            }

            override fun onFailure(call: Call, e: IOException) {
                throw e
            }

        })
    }

    fun saveList() {

    }

    fun getItemFromList(id: String, communicationInterface: CommunicationInterface) {
        val request = Request.Builder()
            .get()
            .header("Authorization", "bearer $TOKEN")
            .url(DOMAIN_URL + SHOPPINGLIST_URL + id)
            .build()
        var newItemList: ArrayList<ShoppingItem> = ArrayList()
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
                communicationInterface.communicateShoppingItems(newItemList)
            }

            override fun onFailure(call: Call, e: IOException) {
                throw e
            }

        })
    }

    fun createShoppingList(name: String){
        var postData = JSONObject()
        postData.put("name", name)
        postData.put("articlesList", JSONArray())
        val body = RequestBody.create(MediaType.parse("application/json"), postData.toString())
        val request = Request.Builder()
            .post(body)
            .header("Authorization", "bearer $TOKEN")
            .url(DOMAIN_URL + SHOPPINGLIST_URL)
            .build()
        httpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                val mMessage = e.message.toString()
                Log.w("failure Response", mMessage)
                //call.cancel();
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {

                val mMessage = response.body()!!.string()
                Log.e("TAG", mMessage)
            }
        })

    }

    fun patchList() {

    }

    fun deleteList(id: String) {
        val request = Request.Builder()
            .delete()
            .header("Authorization", "bearer $TOKEN")
            .url(DOMAIN_URL + SHOPPINGLIST_URL + id)
            .build()
        httpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                val mMessage = e.message.toString()
                Log.w("failure Response", mMessage)
                //call.cancel();
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {

                val mMessage = response.body()!!.string()
                Log.e("TAG", mMessage)
            }
        })
    }

    fun setHeader(fieldName: String, value: String) {

    }
}