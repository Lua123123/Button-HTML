package com.example.demo

import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.demo.databinding.ActivityMainBinding
import com.example.demo.extensions.toast
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var noteRef: DocumentReference
    private lateinit var url: String
    private lateinit var actionMode: String
    private lateinit var timeExpired: Date
    private var openPanelVoting: Boolean? = null
    private var singleChoice: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        db = FirebaseFirestore.getInstance()
        noteRef = db.collection("game30s").document("on_off_voting")

        loadApi()
    }

    private fun loadApi() {
        noteRef.get().addOnSuccessListener {
            if (it.exists()) {
                actionMode = it.getString("action_mode").toString()
                openPanelVoting = it.getBoolean("openPanelVoting")!!
                singleChoice = it.getBoolean("single_choice")!!
                timeExpired = it.getDate("timeExpired")!!
                url = it.getString("url").toString()

                binding.tvActionCode.text = "action_code: $actionMode"
                binding.tvOpenPanelVoting.text = "openPanelVoting: $openPanelVoting"
                binding.tvSingleChoice.text = "single_choice: $singleChoice"
                binding.tvTimeExpired.text = "timeExpired: $timeExpired"
                binding.tvUrl.text = "url: $url"

                if (singleChoice == true) {
                    openSheetDialog()
                } else {
                    toast("singleChoice == false")
                }

            } else {
                toast("DOCUMENT DOES NOW EXIST")
            }
        }
    }

    private fun openSheetDialog() {
        val bottomDialogFragment: BottomDialogFragment = BottomDialogFragment.newInstance(url);

        bottomDialogFragment.show(supportFragmentManager, bottomDialogFragment.tag)
    }
}