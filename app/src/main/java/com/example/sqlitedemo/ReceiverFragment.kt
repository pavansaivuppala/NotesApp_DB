package com.example.sqlitedemo

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import com.example.sqlitedemo.databinding.FragmentReceiverBinding

class ReceiverFragment : Fragment() {
    private lateinit var binding: FragmentReceiverBinding
    private lateinit var databasehelper: Databasehelper
    private lateinit var communicator: Communicator
    private lateinit var adapter: SenderAdapter
    private var yourList = listOf<User>()
    private var isCommunicatorInitialized = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Communicator) {
            communicator = context
            isCommunicatorInitialized = true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReceiverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databasehelper = Databasehelper(requireContext())
        yourList = databasehelper.fetchUser()

        val recyclerView = binding.recycle
        val spanCount = 2
        recyclerView.layoutManager =
            GridLayoutManager(requireContext(), spanCount, GridLayoutManager.VERTICAL, false)

        adapter = SenderAdapter(requireContext(), yourList)
        recyclerView.adapter = adapter

        val name=arguments?.getString("key_name")
        binding.textrevc.text=name


    }
    fun updateAdapter() {
        yourList = databasehelper.fetchUser()
        adapter.updateData(yourList)
    }
}
