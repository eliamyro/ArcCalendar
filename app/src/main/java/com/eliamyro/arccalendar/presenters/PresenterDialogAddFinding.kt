package com.eliamyro.arccalendar.presenters

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import com.eliamyro.arccalendar.common.FIREBASE_LOCATION_FINDINGS
import com.eliamyro.arccalendar.contracts.ContractDialogAddFinding.Actions
import com.eliamyro.arccalendar.contracts.ContractDialogAddFinding.Views
import com.eliamyro.arccalendar.models.Finding
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import org.jetbrains.anko.doAsync
import java.io.ByteArrayOutputStream

/**
 * Created by Elias Myronidis on 24/10/17.
 */
class PresenterDialogAddFinding(val mView: Views) : Actions {

    companion object {
        private val TAG: String = PresenterDialogAddFinding::class.java.simpleName
    }

    override fun saveFinding(image: ImageView, hasImage: Boolean, finding: Finding,
                             excavationItemId: String, workItemId: String, workLocationItemId: String): Boolean {

        val reference: DatabaseReference = FirebaseDatabase.getInstance().reference

        val updatedItemToAddMap = HashMap<String, Any>()
        val itemsRef: DatabaseReference = FirebaseDatabase.getInstance().reference
                .child("$FIREBASE_LOCATION_FINDINGS/$excavationItemId/$workItemId/$workLocationItemId")

        /* Save push() to maintain same random Id */
        val newRef = itemsRef.push()
        val findingKey = newRef.key

        val itemToAdd = ObjectMapper().convertValue(finding, Map::class.java) as HashMap<*, *>

        if (hasImage) {
            val storageRef = FirebaseStorage.getInstance().reference

            image.isDrawingCacheEnabled = true
            image.buildDrawingCache()
            val bitmap = image.drawingCache
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()
            val uploadTask: UploadTask = storageRef.child("$findingKey/$findingKey").putBytes(data)
            uploadTask.addOnSuccessListener { taskSnapshot ->
                val uri = taskSnapshot.downloadUrl
                val f = Finding(finding.name, finding.description, uri.toString())
                val fItem = ObjectMapper().convertValue(f, Map::class.java) as HashMap<*, *>
                updatedItemToAddMap.put("/$FIREBASE_LOCATION_FINDINGS/$excavationItemId/$workItemId/$workLocationItemId/$findingKey", fItem)
                reference.updateChildren(updatedItemToAddMap)
            }
        } else {
            updatedItemToAddMap.put("/$FIREBASE_LOCATION_FINDINGS/$excavationItemId/$workItemId/$workLocationItemId/$findingKey", itemToAdd)
            reference.updateChildren(updatedItemToAddMap)
        }
        return true
    }

    private fun saveImage(findingKey: String, image: ImageView, finding: Finding) {

    }
}