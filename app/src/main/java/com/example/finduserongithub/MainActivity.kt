package com.example.finduserongithub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import android.text.TextUtils
import android.R
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import com.example.finduserongithub.databinding.ActivityMainBinding
import rx.Observer
import rx.Subscription
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private val adapter = GitHubRepoAdapter()
    private var subscription: Subscription? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)
        val listView: ListView = findViewById<View>(R.id.list_view_repos) as ListView
        listView.setAdapter(adapter)
        val editTextUsername = findViewById<View>(R.id.edit_text_username) as EditText
        val buttonSearch: Button = findViewById<View>(R.id.button_search) as Button
        buttonSearch.setOnClickListener {
            fun onClick(v: View?) {
                val username = editTextUsername.text.toString()
                if (!TextUtils.isEmpty(username)) {
                    getStarredRepos(username)
                }
            }
        }
    }

    override fun onDestroy() {
        if (subscription != null && !subscription!!.isUnsubscribed()) {
            subscription!!.unsubscribe()
        }
        super.onDestroy()
    }

    private fun getStarredRepos(username: String) {
        subscription = GitHubClient.instance
            ?.getStarredRepos(username)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Observer<List<GitHubRepo?>?> {
                override fun onCompleted() {
                    Log.d(TAG, "In onCompleted()")
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                    Log.d(TAG, "In onError()")
                }

                override fun onNext(gitHubRepos: List<GitHubRepo?>?) {
                    Log.d(TAG, "In onNext()")
                    adapter.setGitHubRepos(gitHubRepos as List<GitHubRepo>?)
                }
            })
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}