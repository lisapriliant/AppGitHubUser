package com.lisapriliant.appgithubuser.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lisapriliant.appgithubuser.databinding.FragmentFollowBinding
import com.lisapriliant.appgithubuser.ui.main.MainAdapter

class FollowFragment : Fragment() {
    private lateinit var binding: FragmentFollowBinding
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var mainAdapter: MainAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()

        detailViewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.NewInstanceFactory()
        )[DetailViewModel::class.java]

        arguments?.let { it ->
            val position = it.getInt(ARG_POSITION)
            val username = it.getString(ARG_USERNAME)
            detailViewModel.getFollowers(username.toString())
            detailViewModel.getFollowing(username.toString())

            if (position == 1) {
                showLoading(true)
                detailViewModel.followers.observe(viewLifecycleOwner) {
                    mainAdapter.setList(it)
                }
                showLoading(false)
            } else {
                showLoading(true)
                detailViewModel.following.observe(viewLifecycleOwner) {
                    mainAdapter.setList(it)
                }
                showLoading(false)
            }
            detailViewModel.isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }
        }
    }

    private fun setAdapter() {
        mainAdapter = MainAdapter()
        binding.rvListUsers.adapter = mainAdapter
        binding.rvListUsers.layoutManager = LinearLayoutManager(requireActivity())
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }
}