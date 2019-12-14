package be.amellaa.shoppinglist.dto

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
import android.accounts.Account
import android.accounts.AccountManager
import android.util.Log
import okhttp3.Response


class ShoppingListDTO {

    companion object {
        val instance = ShoppingListDTO()
        val httpClient = getOkHttpClient()
        const val DOMAIN_URL = "https://192.168.1.41:3000"
        const val USER_LOGIN_URL = "/user/login/"
        const val USER_SIGNUP_URL = "/user/signup/"
        const val SHOPPINGLIST_URL = "/shoppingList/"
        const val MY_LIST_URL = "/shoppingList/MyLists/"
        const val SHARED_LIST_URL = "/shoppingList/SharedLists/"
        const val ARTICLE_URL = "/article/"
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

    fun login(user: User, communicationInterface: ICommunicateData<User>) {
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
                    user.Token = jsonObject.getString("token")
                    communicationInterface.communicateData(user)
                }

                communicationInterface.communicateCode(response.code())
            }

            override fun onFailure(call: Call, e: IOException) {
                throw e
            }
        })
    }

    fun signUp(newUser: User, communicationInterface: ICommunicateCode) {
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
                communicationInterface.communicateCode(response.code())
            }

            override fun onFailure(call: Call, e: IOException) {
            }
        })
    }

    fun getMyList(communicationInterface: ICommunicateData<ArrayList<ShoppingList>>) {

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
                communicationInterface.communicateData(newShoppingListArray)
            }

            override fun onFailure(call: Call, e: IOException) {
                throw e
            }

        })

    }

    fun getSharedList(communicationInterface: ICommunicateData<ArrayList<ShoppingList>>) {
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
                communicationInterface.communicateData(newShoppingListArray)
            }

            override fun onFailure(call: Call, e: IOException) {
                throw e
            }

        })
    }

    fun getItemFromList(
        id: String,
        communicationInterface: ICommunicateData<ArrayList<ShoppingItem>>
    ) {
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
                    newShoppingItem.checked = jsonObject.getBoolean("checked")
                    newItemList.add(newShoppingItem)
                }
                communicationInterface.communicateData(newItemList)
            }

            override fun onFailure(call: Call, e: IOException) {
                throw e
            }

        })
    }

    fun createShoppingList(name: String, communicationInterface: ICommunicateCode) {
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
                communicationInterface.communicateCode(response.code())
            }
        })

    }

    fun patchList(name: String, id: String, communicationInterface: ICommunicateCode) {
        var postData = JSONObject()
        postData.put("name", name)
        val body = RequestBody.create(MediaType.parse("application/json"), postData.toString())
        val request = Request.Builder()
            .patch(body)
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
                communicationInterface.communicateCode(response.code())
            }
        })
    }

    fun deleteList(id: String, communicationInterface: ICommunicateCode) {
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
                communicationInterface.communicateCode(response.code())
            }
        })
    }

    fun deleteItem(id: String, communicationInterface: ICommunicateCode) {
        val request = Request.Builder()
            .delete()
            .header("Authorization", "bearer $TOKEN")
            .url(DOMAIN_URL + ARTICLE_URL + id)
            .build()
        httpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                val mMessage = e.message.toString()
                Log.w("failure Response", mMessage)
                //call.cancel();
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                communicationInterface.communicateCode(response.code())
            }
        })
    }

    fun createItem(name: String, qty: String, listId: String, communicationInterface: ICommunicateCode){
        var postData = JSONObject()
        postData.put("listId", listId)
        postData.put("name", name)
        postData.put("qty", qty)
        val body = RequestBody.create(MediaType.parse("application/json"), postData.toString())
        val request = Request.Builder()
            .post(body)
            .header("Authorization", "bearer $TOKEN")
            .url(DOMAIN_URL + ARTICLE_URL)
            .build()
        httpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                val mMessage = e.message.toString()
                Log.w("failure Response", mMessage)
                //call.cancel();
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                communicationInterface.communicateCode(response.code())
            }
        })
    }

    fun patchItem(name: String?, qty: String?, checked: Boolean?, id: String, communicationInterface: ICommunicateCode) {
        var postData = JSONObject()
        if(name != null){
            postData.put("name", name)
        }
        if(qty != null){
            postData.put("qty", qty)
        }
        if(checked != null){
            postData.put("checked", checked)
        }
        val body = RequestBody.create(MediaType.parse("application/json"), postData.toString())
        val request = Request.Builder()
            .patch(body)
            .header("Authorization", "bearer $TOKEN")
            .url(DOMAIN_URL + ARTICLE_URL + id)
            .build()
        httpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                val mMessage = e.message.toString()
                Log.w("failure Response", mMessage)
                //call.cancel();
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                communicationInterface.communicateCode(response.code())
            }
        })
    }
}