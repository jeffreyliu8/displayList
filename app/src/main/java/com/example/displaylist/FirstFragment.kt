package com.example.displaylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.displaylist.adapter.ItemAdapter
import com.example.displaylist.databinding.FragmentFirstBinding
import com.example.displaylist.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: ItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViewAndUIs()
        setupViewModel()
    }

    private fun setupRecyclerViewAndUIs() {
        // Creates a vertical Layout Manager
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        // Access the RecyclerView Adapter
        adapter = ItemAdapter {
            val action =
                FirstFragmentDirections.actionFirstFragmentToSecondFragment(code = it.code)
            findNavController().navigate(action)
        }
        binding.recyclerView.adapter = adapter

        binding.swipeToRefreshView.setOnRefreshListener {
            viewModel.getCountries()
        }
    }

    private fun setupViewModel() {
        // Start a coroutine in the lifecycle scope
        lifecycleScope.launch {
            // repeatOnLifecycle launches the block in a new coroutine every time the
            // lifecycle is in the STARTED state (or above) and cancels it when it's STOPPED.

            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Trigger the flow and start listening for values.
                // Note that this happens when lifecycle is STARTED and stops
                // collecting when the lifecycle is STOPPED
                viewModel.uiStateFlow.collect { state ->
                    adapter.updateList(state.counties)
                    binding.swipeToRefreshView.isRefreshing = state.isLoading
                    state.error?.getContentIfNotHandled()?.let {
                        Snackbar.make(binding.coordinatorLayout, it, Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}