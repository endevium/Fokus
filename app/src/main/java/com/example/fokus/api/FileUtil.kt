package com.example.fokus.api

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore

object FileUtil {
        fun getPath(context: Context, uri: Uri): String? {
            // Check if the URI is a document URI
            if (DocumentsContract.isDocumentUri(context, uri)) {
                // Handle different document types (like images from Google Photos)
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":").toTypedArray()
                val type = split[0]

                var contentUri: Uri? = null
                when {
                    "primary".equals(type, ignoreCase = true) -> {
                        // Handle primary storage
                        return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                    }
                    "image".equals(type, ignoreCase = true) -> {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    }
                    "video".equals(type, ignoreCase = true) -> {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    }
                    // Add more document types as needed
                }

                if (contentUri != null) {
                    val selection = "_id=?"
                    val selectionArgs = arrayOf(split[1])
                    val cursor = context.contentResolver.query(contentUri, null, selection, selectionArgs, null)
                    return cursor?.use {
                        if (it.moveToFirst()) {
                            val index = it.getColumnIndex(MediaStore.Images.Media.DATA)
                            it.getString(index)
                        } else {
                            null
                        }
                    }
                }
            } else {
                // Handle regular content URIs
                val projection = arrayOf(MediaStore.Images.Media.DATA)
                val cursor = context.contentResolver.query(uri, projection, null, null, null)
                return cursor?.use {
                    if (it.moveToFirst()) {
                        val index = it.getColumnIndex(MediaStore.Images.Media.DATA)
                        it.getString(index)
                    } else {
                        null
                    }
                }
            }
            return null
        }
}