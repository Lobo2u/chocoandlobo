package com.example.loboandchoco

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.loboandchoco.databinding.ActivityCommentBinding
import com.example.loboandchoco.databinding.ActivityMainBinding

class CommentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCommentBinding.inflate(layoutInflater)
        setContentView(binding.root)


            }
        }
