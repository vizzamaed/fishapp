package com.example.fishapp

import android.app.Dialog
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import com.example.fishapp.databinding.ActivityEditProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class EditProfile : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var auth:FirebaseAuth
    private lateinit var databaseReference:DatabaseReference
    private lateinit var storageReference:StorageReference
    private lateinit var imageUri:Uri
    private lateinit var dialog:Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth= FirebaseAuth.getInstance()
        val uid=auth.currentUser?.uid
        databaseReference=FirebaseDatabase.getInstance().getReference("Users")
        binding.saveBtn.setOnClickListener {

            showProgressBar()
            val name =binding.editname.text.toString()
            val bio =binding.editbio.text.toString()
            val contact=binding.editcontact.text.toString()

            val user= User(name, bio, contact)
            if (uid != null){
                databaseReference.child(uid).setValue(user).addOnCompleteListener {
                    if (it.isSuccessful){
                        uploadProfilePic()
                    }else{
                        hideProgressBar()
                        Toast.makeText(this@EditProfile, "Failed to update profile", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun uploadProfilePic() {

        imageUri = Uri.parse("android.resource://$packageName/${R.drawable.profile}")
        storageReference=FirebaseStorage.getInstance().getReference("Users/"+auth.currentUser?.uid)
        storageReference.putFile(imageUri).addOnSuccessListener {

            hideProgressBar()
            Toast.makeText(this@EditProfile, "Profile successfully updated",Toast.LENGTH_SHORT).show()

        }.addOnFailureListener{

            hideProgressBar()
            Toast.makeText(this@EditProfile, "Failed to upload the image",Toast.LENGTH_SHORT).show()
        }
    }

    private fun showProgressBar(){
        dialog=Dialog(this@EditProfile)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
    private fun hideProgressBar(){
        dialog.dismiss()
    }
}

