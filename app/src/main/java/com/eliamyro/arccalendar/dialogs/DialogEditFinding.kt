package com.eliamyro.arccalendar.dialogs

import android.app.Activity
import android.content.ContentValues.TAG
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
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import com.eliamyro.arccalendar.R
import com.eliamyro.arccalendar.common.*
import com.eliamyro.arccalendar.contracts.ContractDialogEditFinding
import com.eliamyro.arccalendar.contracts.ContractDialogEditFinding.Views
import com.eliamyro.arccalendar.models.Finding
import com.eliamyro.arccalendar.presenters.PresenterDialogEditFinding
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.dialog_add_finding.*

/**
 * Created by Elias Myronidis on 4/10/17.
 * LinkedIn: https://www.linkedin.com/in/eliasmyronidis/
 */
class DialogEditFinding : DialogFragment(), Views {

    companion object {
        private val REQUEST_CAMERA = 1
        private val SELECT_FILE = 0
    }

    private val mFinding: Finding by lazy { arguments.getParcelable<Finding>(KEY_FINDING) }
    private val mExcavationItemId: String by lazy { arguments.getString(KEY_EXCAVATION_ITEM_ID) }
    private val mWorkItemId: String by lazy { arguments.getString(KEY_WORK_ITEM_ID) }
    private val mWorkLocationItemId: String by lazy { arguments.getString(KEY_WORK_LOCATION_ITEM_ID) }
    private val mFindingItemId: String by lazy { arguments.getString(KEY_FINDING_ITEM_ID) }

    private val mPresenter: ContractDialogEditFinding.Actions by lazy { PresenterDialogEditFinding(this) }

    private var hasImage: Boolean = false
    private var mState: State? = null

    private enum class DialogItems { CAMERA, GALLERY, CLOSE }
    private enum class State { VISIBLE, HIDDEN }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        mState = State.HIDDEN
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.dialog_add_finding, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        val toolbar = toolbar as Toolbar?

        toolbar?.title = getString(R.string.edit_finding)

        toolbar?.let {
            (activity as AppCompatActivity).setSupportActionBar(it)

            val actionBar = (activity as AppCompatActivity).supportActionBar
            actionBar?.setDisplayHomeAsUpEnabled(true)
            actionBar?.setHomeAsUpIndicator(R.drawable.ic_action_back)
        }

        if (mFinding.imageUrl != "") {
            with(mFinding) {
                Picasso.with(activity).load(imageUrl).into(iv_finding)
                et_finding_name.setText(name)
                et_finding_description.setText(description)
            }
        }

        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                mState = if ((mFinding.name == et_finding_name.text.toString())
                        && (mFinding.description == et_finding_description.text.toString())) {
                    State.HIDDEN
                } else {
                    State.VISIBLE
                }
                activity.invalidateOptionsMenu()
            }
        }

        et_finding_name.addTextChangedListener(textWatcher)
        et_finding_description.addTextChangedListener(textWatcher)

        iv_finding.setOnClickListener { selectImage() }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        menu?.clear()
        inflater?.inflate(R.menu.menu_edit_excavation, menu)

        menu?.findItem(R.id.action_save_edit_dialog_changes)?.isVisible = mState == State.VISIBLE

        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_save_edit_dialog_changes -> {
                val name = et_finding_name.text.toString()
                val description = et_finding_description.text.toString()

                val finding = Finding(name, description)
                if (mPresenter.updateFinding(iv_finding, hasImage, finding, mExcavationItemId, mWorkItemId,
                        mWorkLocationItemId, mFindingItemId)) {
                    fragmentManager?.popBackStack()
                } else {
                    Log.d(TAG, "Update was unsuccessful.")
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
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryIntent.type = "image/*"
        startActivityForResult(Intent.createChooser(galleryIntent, "Something"), SELECT_FILE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 5) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
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

                mState = State.VISIBLE
                activity.invalidateOptionsMenu()

            } else if (requestCode == SELECT_FILE) {
                val selectedImageUri = data?.data
                iv_finding.setImageURI(selectedImageUri)
                if (mFinding.imageUrl != selectedImageUri.toString()) {
                    mState = State.VISIBLE
                    activity.invalidateOptionsMenu()
                }
            }
        }

        data?.let { hasImage = true }
    }
}