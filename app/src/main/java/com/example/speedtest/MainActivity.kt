package com.example.speedtest

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.speedtest.databinding.ActivityMainBinding
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val client = OkHttpClient()
        val request = Request.Builder()

            .url("https://download2267.mediafire.com/5zxeq2z9620gULRyO4K2z5iUgmfFo3FGaMsbAhOLs-T8rxtalodszvUHaSIWcTXi3NTFg2_hSEEFF_HjDkuxjcj3w_wnWA0BIXQQhw6k3X75OJp9oUqn9hKlVY8uXhJOhknmlDLYg8ZQgIMVjDlsZh4QE3lHNHehp8A-eMaYhIUj1yag/ky34vy4kbdbzbd2/english_clone.apk") // replace image url
            .build()

        val call = client.newCall(request)
        val startTime = System.currentTimeMillis()

        call.enqueue(object : Callback {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call, response: Response) {

                val endTime = System.currentTimeMillis()

                var fileSize = (response.body?.contentLength() ?: 1) / (1024.0f * 1024.0f)

                val downloadTimeInSec = (endTime - startTime) / 1000.0f

                val downloadSpeedMbps = ((fileSize) / downloadTimeInSec)

                runOnUiThread {
                    with(binding) {
                        tvSpeed.text = "downloadSpeed: $downloadSpeedMbps Mbps"
                        tvFileSize.text = "fileSize: $fileSize MB"
                        tvDownloadTime.text = "downloadTime: $downloadTimeInSec S"
                    }
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                // Handle failure
            }
        })
    }
}