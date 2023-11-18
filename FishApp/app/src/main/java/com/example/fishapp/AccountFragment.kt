package com.example.fishapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.fishapp.databinding.ActivityLogInBinding

class AccountFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editProfileButton = view.findViewById<Button>(R.id.edit_Profile_button)
        val beSellerBtn = view.findViewById<Button>(R.id.beSellerBtn)
        val sellerDashboard= view.findViewById<Button>(R.id.sellerDashboard)

        editProfileButton.setOnClickListener {
            startActivity(Intent(context, EditProfile::class.java))
        }

        beSellerBtn.setOnClickListener {
            startActivity(Intent(context, SellerRegistration::class.java))
        }

       sellerDashboard.setOnClickListener {
            startActivity(Intent(context, SellerDashboard::class.java))
        }
    }
}
