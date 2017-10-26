package com.eliamyro.arccalendar.dialogs

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.DialogFragment
import android.support.v4.content.ContextCompat.checkSelfPermission
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.*
import com.eliamyro.arccalendar.R
import kotlinx.android.synthetic.main.dialog_add_finding.*
import com.eliamyro.arccalendar.common.KEY_EXCAVATION_ITEM_ID
import com.eliamyro.arccalendar.common.KEY_WORK_ITEM_ID
import com.eliamyro.arccalendar.common.KEY_WORK_LOCATION_ITEM_ID
import com.eliamyro.arccalendar.contracts.ContractDialogAddFinding
import com.eliamyro.arccalendar.models.Finding
import com.eliamyro.arccalendar.presenters.PresenterDialogAddFinding


/**
 * Created by Elias Myronidis on 2/10/17.
 */
class DialogAddFinding : DialogFragment(), ContractDialogAddFinding.Views {

    companion object {
        private val TAG: String = DialogAddFinding::class.java.simpleName
        private val REQUEST_CAMERA = 1
        private val SELECT_FILE = 0
    }

    enum class DialogItems { CAMERA, GALLERY, CLOSE }

    private val mExcavationItemId: String by lazy { arguments.getString(KEY_EXCAVATION_ITEM_ID) }
    private val mWorkItemId: String by lazy { arguments.getString(KEY_WORK_ITEM_ID) }
    private val mWorkLocationItemId: String by lazy { arguments.getString(KEY_WORK_LOCATION_ITEM_ID) }

    private val mPresenter: ContractDialogAddFinding.Actions by lazy { PresenterDialogAddFinding(this) }
    private var pathToImage: String? = null
    private var hasImage: Boolean = false


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.dialog_add_finding, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        val toolbar = toolbar as Toolbar?

        toolbar?.title = getString(R.string.create_finding)

        toolbar?.let {
            (activity as AppCompatActivity).setSupportActionBar(it)

            val actionBar = (activity as AppCompatActivity).supportActionBar
            actionBar?.setDisplayHomeAsUpEnabled(true)
            actionBar?.setHomeAsUpIndicator(R.drawable.ic_close_white_24dp)
        }

        iv_finding.setOnClickListener { selectImage() }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        menu?.clear()
        activity.menuInflater.inflate(R.menu.menu_add_excavation_work, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.action_save -> {
                val findingName: String = et_finding_name.text.toString()
                val findingDescription: String = et_finding_description.text.toString()

                if (mPresenter.saveFinding(iv_finding, hasImage, Finding(findingName, findingDescription),
                        mExcavationItemId, mWorkItemId, mWorkLocationItemId)){
                    fragmentManager.popBackStack()
                    dismiss()
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun selectImage() {
        val items: Array<DialogItems> = arrayOf(DialogItems.CAMERA, DialogItems.GALLERY, DialogItems.CLOSE)

        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Add image")
        builder.setItems((items.map { it.name }).toTypedArray()) { dialogInterface, position ->
            when (items[position]) {
                DialogItems.CAMERA -> {
                    Log.d(TAG, "Camera selected")
                    if (checkSelfPermission(activity, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(arrayOf(android.Manifest.permission.CAMERA),
                                5)
                    } else {
                        getImageFromCamera()
                    }
                }

                DialogItems.GALLERY -> getImageFromGallery()

                else -> dialogInterface.dismiss()
            }
        }
        builder.show()
    }

    private fun getImageFromCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, REQUEST_CAMERA)
    }

    private fun getImageFromGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryIntent.type = "image/*"
        startActivityForResult(Intent.createChooser(galleryIntent, "Something"), SELECT_FILE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 5) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "Permission granted")
                // Now user should be able to use camera
                getImageFromCamera()
            } else {
                dismiss()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        hasImage = false

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                val bundle = data?.extras
                val bitmap: Bitmap = bundle?.get("data") as Bitmap
                iv_finding.setImageBitmap(bitmap)

            } else if (requestCode == SELECT_FILE) {
                val selectedImageUri = data?.data
                iv_finding.setImageURI(selectedImageUri)
                pathToImage = selectedImageUri?.path
            }
        }

        data?.let { hasImage = true }
    }

}