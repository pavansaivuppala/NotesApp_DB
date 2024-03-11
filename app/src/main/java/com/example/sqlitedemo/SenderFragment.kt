package com.example.sqlitedemo

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.sqlitedemo.databinding.FragmentSenderBinding


class SenderFragment : Fragment() {
    private lateinit var communicator: Communicator
    private lateinit var databaseHelper:Databasehelper
    private lateinit var binding: FragmentSenderBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Communicator) {
            communicator = context
        } else {
            throw ClassCastException("$context must implement Communicator")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentSenderBinding.inflate(layoutInflater,container,false)
        // Inflate the layout for this fragment
        initViews()
        return binding.root
    }

    private fun initViews() {
        databaseHelper = Databasehelper(requireContext())
        with(binding) {
            btnInsert.setOnClickListener {
                val name = userName.text.toString()
                val age = userAge.text.toString().toInt()
                databaseHelper.insertUser(name, age)
                //communicator.sendMessage()
            }

            btnDelete.setOnClickListener{
                val id= userId.text.toString().toInt()
                databaseHelper.deleteUser(id)
                communicator.sendMessage()
            }

            btnFetch.setOnClickListener {
                val p= databaseHelper.fetchUser()
                //communicator.sendMessage()
                sendDataToReceiverFragment()

            }

            btnUpdate.setOnClickListener {
                val id = userId.text.toString().toInt()
                val name=userName.text.toString()
                val age=userAge.text.toString().toInt()
                databaseHelper.updateUser(User(id,name,age))
                //communicator.sendMessage()
            }
        }
    }
    private fun sendDataToReceiverFragment() {
        val bundle = Bundle()
        bundle.putString("key_name", "John Doe")
        bundle.putInt("key_age", 25)

        val receiverFragment = ReceiverFragment()
        receiverFragment.arguments = bundle

        val transaction = requireFragmentManager().beginTransaction()
        transaction.replace(R.id.fragment2, receiverFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}