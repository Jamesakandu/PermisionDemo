package com.inspirelab.permissionhandling

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog

class MainActivity : AppCompatActivity(),EasyPermissions.PermissionCallbacks {

    companion object{
        const val PERMISSION_LOCATION_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermissionButton()
        setVisibility()

    }


    // this function will be called when permission access is denied by the user
    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (EasyPermissions.somePermissionDenied(this,perms.first())){
            SettingsDialog.Builder(this).build().show()
        }else{
            requestLocationPermission()
        }
    }

    // this function will be called when permission access is  granted by the user
    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        Toast.makeText(this,getString(R.string.permision_alert),Toast.LENGTH_LONG).show()
        setVisibility()

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this)
    }


    private fun requestLocationPermission(){
            EasyPermissions.requestPermissions(this,getString(R.string.notify_user),PERMISSION_LOCATION_REQUEST_CODE,Manifest.permission.ACCESS_FINE_LOCATION)

    }

    private fun hasLocationPermission() = EasyPermissions.hasPermissions(this,Manifest.permission
            .ACCESS_FINE_LOCATION)

    private fun setVisibility(){
        val textView = findViewById<TextView>(R.id.textView)
        val button= findViewById<Button>(R.id.button)

        if (hasLocationPermission()){
            textView.visibility = View.VISIBLE
            button.visibility = View.GONE
        }else{
            textView.visibility = View.GONE
            button.visibility = View.VISIBLE
        }
    }

    private fun requestPermissionButton(){
        val button= findViewById<Button>(R.id.button)
        button.setOnClickListener {
            requestLocationPermission()
        }
    }


}