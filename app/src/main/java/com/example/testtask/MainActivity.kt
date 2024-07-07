package com.example.testtask

import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.apache.commons.compress.archivers.ArchiveEntry
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.security.SecureRandom
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class MainActivity : AppCompatActivity() {

    private lateinit var displayTextView: TextView
    private lateinit var editTextView: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // Initialize UI components
        displayTextView = findViewById(R.id.displayTextView)
        editTextView = findViewById(R.id.editTextView)
        val okButton: Button = findViewById(R.id.okButton)

        // Execute the download and unpack operation in a background thread
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val url = "https://drive.google.com/uc?id=1EMxJYf8uutC-EO1Xm97AwLaTSSBzGzQA&export=download"
                val zipFile = File(filesDir, "testTaskLib.zip")
                val outputDir = File(filesDir, "unzipped")

                downloadFile(url, zipFile)
                unpackZip(zipFile, outputDir)

                // Load the native library
                val architecture = getDeviceArchitecture()
                val libraryFile = when (architecture) {
                    "armeabi-v7a" -> File(outputDir, "armeabi-v7a/libMyLibrary.so")
                    "arm64-v8a" -> File(outputDir, "arm64-v8a/libMyLibrary.so")
                    "x86" -> File(outputDir, "x86/libMyLibrary.so")
                    "x86_64" -> File(outputDir, "x86_64/libMyLibrary.so")
                    else -> throw UnsupportedOperationException("Unsupported architecture: $architecture")
                }
                    System.load(libraryFile.absolutePath)

                withContext(Dispatchers.Main) {
                    // Display text using the native library (if needed)
                    MyLibrary.passMessage("Hello from Kotlin!")
                }

                // Set up the OK button click listener after loading the library
                withContext(Dispatchers.Main) {
                    okButton.setOnClickListener {
                        handleOkButtonClick()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    displayTextView.text = "Error: ${e.message}"
                    Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun handleOkButtonClick() {
        // Handle OK button click actions here
        CoroutineScope(Dispatchers.Main).launch {
            MyLibrary.passMessage(editTextView.text.toString())
                displayTextView.text =  MyLibrary.getMessage()
                Toast.makeText(this@MainActivity, "OK button pressed.", Toast.LENGTH_SHORT).show()

        }
    }

    fun getDeviceArchitecture(): String {
        val supportedAbis = Build.SUPPORTED_ABIS
        // Prefer the first ABI in the list, which is usually the most preferred one
        return supportedAbis.firstOrNull() ?: throw RuntimeException("No supported ABIs found")
    }

    private fun downloadFile(url: String, outputFile: File) {
        val client = OkHttpClient.Builder()
            .sslSocketFactory(getTrustAllSSLSocketFactory(), TrustAllCerts())
            .hostnameVerifier { _, _ -> true }
            .build()
        val request = Request.Builder().url(url).build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Failed to download file: $response")

            response.body?.byteStream()?.use { input ->
                FileOutputStream(outputFile).use { output ->
                    input.copyTo(output)
                }
            }
        }
    }

    private fun unpackZip(zipFile: File, outputDir: File) {
        zipFile.inputStream().use { fis ->
            ZipArchiveInputStream(fis).use { zis ->
                var entry: ArchiveEntry?
                while (zis.nextEntry.also { entry = it } != null) {
                    val outFile = File(outputDir, entry!!.name)
                    if (entry!!.isDirectory) {
                        outFile.mkdirs()
                    } else {
                        outFile.parentFile.mkdirs()
                        FileOutputStream(outFile).use { fos ->
                            zis.copyTo(fos)
                        }
                    }
                }
            }
        }
    }

    private fun getTrustAllSSLSocketFactory(): SSLSocketFactory {
        val trustAllCerts = arrayOf<TrustManager>(TrustAllCerts())
        try {
            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, trustAllCerts, SecureRandom())
            return sslContext.socketFactory
        } catch (e: java.lang.Exception) {
            throw RuntimeException("Failed to create SSL socket factory", e)
        }
    }

    private class TrustAllCerts : X509TrustManager {
        override fun checkClientTrusted(
            p0: Array<out java.security.cert.X509Certificate>?,
            p1: String?
        ) {
        }

        override fun checkServerTrusted(
            p0: Array<out java.security.cert.X509Certificate>?,
            p1: String?
        ) {
        }

        override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> = emptyArray()
    }
}
