package com.eliamyro.arccalendar.presenters

import android.graphics.Bitmap
import android.widget.ImageView
import com.eliamyro.arccalendar.common.FIREBASE_LOCATION_FINDINGS
import com.eliamyro.arccalendar.common.FIREBASE_PROPERTY_FINDING_DESCRIPTION
import com.eliamyro.arccalendar.common.FIREBASE_PROPERTY_FINDING_IMAGE_URL
import com.eliamyro.arccalendar.common.FIREBASE_PROPERTY_FINDING_NAME
import com.eliamyro.arccalendar.contracts.ContractDialogEditFinding.Actions
import com.eliamyro.arccalendar.contracts.ContractDialogEditFinding.Views
import com.eliamyro.arccalendar.models.Finding
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.io.ByteArrayOutputStream

/**
 * Created by Elias Myronidis on 27/10/17.
 * LinkedIn: https://www.linkedin.com/in/eliasmyronidis/
 */

class PresenterDialogEditFinding(val mView: Views) : Actions {

    override fun updateFinding(image: ImageView, hasImage: Boolean, finding: Finding,
                               excavationItemId: String, workItemId: String,
                               workLocationItemId: String, findingItemId: String): Boolean {

        var isUpdateSuccessful = true

        val reference: DatabaseReference = FirebaseDatabase.getInstance().reference
        val updatedFindingMap = HashMap<String, Any>()

        if (hasImage) {
            val storageRef = FirebaseStorage.getInstance().reference

            image.isDrawingCacheEnabled = true
            image.buildDrawingCache()
            val bitmap = image.drawingCache
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()
            val uploadTask: UploadTask = storageRef.child("images/$findingItemId").putBytes(data)
            uploadTask.addOnSuccessListener { taskSnapshot ->
                val uri = taskSnapshot.downloadUrl
                updatedFindingMap.put("/$FIREBASE_LOCATION_FINDINGS/$excavationItemId/$workItemId/$workLocationItemId/$findingItemId/$FIREBASE_PROPERTY_FINDING_NAME", finding.name)

                updatedFindingMap.put("/$FIREBASE_LOCATION_FINDINGS/$excavationItemId/$workItemId/$workLocationItemId/$findingItemId/$FIREBASE_PROPERTY_FINDING_DESCRIPTION", finding.description)

                updatedFindingMap.put("/$FIREBASE_LOCATION_FINDINGS/$excavationItemId/$workItemId/$workLocationItemId/$findingItemId/$FIREBASE_PROPERTY_FINDING_IMAGE_URL", uri.toString())

                reference.updateChildren(updatedFindingMap).addOnFailureListener { isUpdateSuccessful = false }
            }
        } else {
            updatedFindingMap.put("/$FIREBASE_LOCATION_FINDINGS/$excavationItemId/$workItemId/$workLocationItemId/$findingItemId", finding)
            reference.updateChildren(updatedFindingMap).addOnFailureListener { isUpdateSuccessful = false }
        }
        return isUpdateSuccessful
    }
}