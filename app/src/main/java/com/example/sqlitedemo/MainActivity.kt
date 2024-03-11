package com.example.sqlitedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sqlitedemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), Communicator{
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }


    private fun initViews() {
        supportFragmentManager.beginTransaction().add(R.id.fragment1,SenderFragment()).commit()
        supportFragmentManager.beginTransaction().add(R.id.fragment2,ReceiverFragment()).commit()
    }

    override fun sendMessage() {
        val receiverFragment = supportFragmentManager.findFragmentById(R.id.fragment2) as? ReceiverFragment
        receiverFragment?.updateAdapter()
    }


}