package com.example.finduserongithub

import android.R
import android.annotation.SuppressLint
import android.widget.TextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.annotation.Nullable
import kotlinx.android.synthetic.main.activity_main.*


class GitHubRepoAdapter : BaseAdapter() {
    private val gitHubRepos: MutableList<GitHubRepo> = ArrayList()
    override fun getCount(): Int {
        return gitHubRepos.size
    }

    override fun getItem(position: Int) {
        if (position < 0 || position >= gitHubRepos.size) {
            null
        } else {
            gitHubRepos[position]
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = if (convertView != null) convertView else createView(parent)
        val viewHolder = view.getTag() as GitHubRepoViewHolder
        viewHolder.setGitHubRepo(getItem(position))
        return view
    }

    fun setGitHubRepos(@Nullable repos: List<GitHubRepo>?) {
        if (repos == null) {
            return
        }
        gitHubRepos.clear()
        gitHubRepos.addAll(repos)
        notifyDataSetChanged()
    }

    private fun createView(parent: ViewGroup): View {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.item_github_repo, parent, false)
        val viewHolder = GitHubRepoViewHolder(view)
        view.setTag(viewHolder)
        return view
    }

    fun add(gitHubRepo: GitHubRepo) {
        gitHubRepos.add(gitHubRepo)
        notifyDataSetChanged()
    }

    private class GitHubRepoViewHolder(view: View) {

        private val textRepoName: TextView
        private val textRepoDescription: TextView
        private val textLanguage: TextView
        private val textStars: TextView
        @SuppressLint("SetTextI18n")
        fun setGitHubRepo(gitHubRepo: Unit) {
            textRepoName.setText(gitHubRepo.name)
            textRepoDescription.setText(gitHubRepo.description)
            textLanguage.text = "Language: " + gitHubRepo.language
            textStars.text = "Stars: " + gitHubRepo.stargazersCount
        }

        init {
            textRepoName = view.findViewById(R.id.text_repo_name)
            textRepoDescription = view.findViewById(R.id.text_repo_description)
            textLanguage = view.findViewById(R.id.text_language)
            textStars = view.findViewById(R.id.text_stars)
        }
    }
}

