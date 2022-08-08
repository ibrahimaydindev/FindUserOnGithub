package com.example.finduserongithub

import androidx.annotation.NonNull

import retrofit2.converter.gson.GsonConverterFactory

import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory

import retrofit2.Retrofit

import com.google.gson.FieldNamingPolicy

import com.google.gson.GsonBuilder

import com.google.gson.Gson
import rx.Observable


class GitHubClient private constructor() {
    private val gitHubService: GitHubService
    fun getStarredRepos(userName: String): Observable<List<GitHubRepo?>?>? {
        return gitHubService.getStarredRepositories(userName)
    }

    companion object {
        private const val GITHUB_BASE_URL = "https://api.github.com/"
        var instance: GitHubClient? = null
            get() {
                if (field == null) {
                    field = GitHubClient()
                }
                return field
            }
            private set
    }

    init {
        val gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
        val retrofit = Retrofit.Builder().baseUrl(GITHUB_BASE_URL)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        gitHubService = retrofit.create(GitHubService::class.java)
    }
}