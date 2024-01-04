package com.jigar.safexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.documentfile.provider.DocumentFile
import com.jigar.safexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var openDocumentLauncher: ActivityResultLauncher<Intent>

    lateinit var binding: ActivityMainBinding

    private companion object {
        const val REQUEST_CODE_OPEN_DOCUMENT = 42
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Initialize the ActivityResultLauncher
        openDocumentLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                data?.data?.let { uri ->
                    // Use DocumentFile to work with the selected file
                    val documentFile = DocumentFile.fromSingleUri(this, uri)

                    documentFile?.let {
                        if (it.exists()) {
                            // Now you can perform operations on the selected file using DocumentFile
                            val fileName = it.name
                            val mimeType = contentResolver.getType(uri)

                            // Add your logic here...
                        } else {
                            // Handle the case where the file does not exist or is not accessible
                        }
                    }
                }
            }
        }

        binding.tvOpen.setOnClickListener {
            openFilePicker()
        }
    }
    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"  // Set the MIME type you want to filter
        }

        openDocumentLauncher.launch(intent)
    }
}