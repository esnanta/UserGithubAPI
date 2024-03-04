package com.esnanta.usergithubapi.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.esnanta.usergithubapi.data.response.UserItem
import com.esnanta.usergithubapi.databinding.ActivityMainBinding
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewUserModel : ViewModel by viewModels()

    companion object {
        private val TAG = MainActivity::class.java.simpleName
        private const val USERNAME_GITHUB = "sidiqpermana"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.userRecyclerView.setLayoutManager(layoutManager)
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.userRecyclerView.addItemDecoration(itemDecoration)

        getUsers()
        //val userItem = DummyData.getDummyData(this)
        //val userAdapter = UserAdapter(userItem)

//        val recyclerViewNews = binding.userRecyclerView
//        recyclerViewNews.adapter = userAdapter
//        recyclerViewNews.layoutManager = LinearLayoutManager(this)
//        recyclerViewNews.setHasFixedSize(true)

    }

    private fun getUsers(){
        binding.progressBar.visibility = View.VISIBLE

        val client = AsyncHttpClient()
        val url = "https://api.github.com/search/users?q=$USERNAME_GITHUB"

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                val userItem = arrayListOf<UserItem>()
                val result = responseBody?.let { String(it) }
                Log.d(TAG, result.toString())
                try {
                    val responseObject = JSONObject(result)

                    val jsonArray = JSONArray(result)

                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val username = jsonObject.getString("login")
                        val id = jsonObject.getString("id")
                        val userItemDetail = UserItem(
                            Integer.parseInt(id.toString()),
                            username
                        )
                        userItem.add(userItemDetail)
                    }

                    val userAdapter = UserAdapter(userItem)
                    binding.userRecyclerView.adapter = userAdapter

                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                binding.progressBar.visibility = View.INVISIBLE
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }

                Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }

}