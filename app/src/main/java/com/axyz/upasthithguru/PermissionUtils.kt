package com.axyz.upasthithguru

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment

class MyDialogFragment(private val activity: Activity, private val title: String, private val message: String) : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionDialogShown++
        isCancelable = false  // Set the cancelable property to false
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("APP SETTINGS") { dialog, _ ->
                permissionDialogShown--
                // Open the app settings when the "App settings" button is clicked
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", activity.packageName, null)
                }
                activity.startActivity(intent)
            }
            .setNegativeButton("QUIT") { dialog, id ->
                // Quit the app when the "Quit" button is clicked
                permissionDialogShown--
                finishAffinity(activity)
            }
        return builder.create()
    }
}

object PermissionUtils {

    private val REQUIRED_PERMISSIONS: Array<String> =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.BLUETOOTH_ADVERTISE
            )
        } else {
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }

    fun hasPermissions(context: Context) = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermissions(activity: Activity) {
        val deniedPermissions = REQUIRED_PERMISSIONS.filter {
            ContextCompat.checkSelfPermission(activity, it) != PackageManager.PERMISSION_GRANTED
        }
        if (deniedPermissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(activity, deniedPermissions.toTypedArray(), 0)
        }
    }

    fun onRequestPermissionsResult(
        activity: AppCompatActivity,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        var coarseLocationDenied = false
        var fineLocationDenied = false
        var advertisePermissionDenied = false
        var scanPermissionDenied = false
        var connectPermissionDenied = false
        for ((index, permission) in permissions.withIndex()) {
            if (grantResults[index] == PackageManager.PERMISSION_DENIED) {
                when (permission) {
                    Manifest.permission.ACCESS_COARSE_LOCATION -> {
                        coarseLocationDenied = true
                    }
                    Manifest.permission.ACCESS_FINE_LOCATION -> {
                        fineLocationDenied = true
                    }
                    Manifest.permission.BLUETOOTH_ADVERTISE -> {
                        advertisePermissionDenied = true
                    }
                    Manifest.permission.BLUETOOTH_SCAN -> {
                        scanPermissionDenied = true
                    }
                    Manifest.permission.BLUETOOTH_CONNECT -> {
                        connectPermissionDenied = true
                    }
                }
            }
        }
        if( advertisePermissionDenied && scanPermissionDenied && connectPermissionDenied ){
            handleBluetoothPermissionDenied(activity)
        } else if (coarseLocationDenied && fineLocationDenied) {
            handleBothLocationPermissionsDenied(activity)
        } else if (coarseLocationDenied) {
            handleCoarseLocationPermissionDenied()
        } else if (fineLocationDenied) {
            handleFineLocationPermissionDenied(activity)
        } else if (advertisePermissionDenied) {
            handleBluetoothAdvertisePermissionDenied()
        } else if (scanPermissionDenied) {
            handleBluetoothScanPermissionDenied()
        } else if (connectPermissionDenied) {
            handleBluetoothConnectPermissionDenied(activity)
        }
    }

    private fun showDialogFragment(activity: AppCompatActivity,title: String,msg :String) {
        val dialogFragment = MyDialogFragment(activity,title,msg)
        dialogFragment.show(activity.supportFragmentManager, "my_dialog")
    }

    private fun handleBluetoothPermissionDenied(activity: AppCompatActivity) {
        val title = "Bluetooth Permission Required"
        val msg = "Starting from Android 12. the system\n" +
                "requires apps to be granted Bluetooth\n" +
                "access in order to scan for and connect to\n" +
                "BLE devices."
        showDialogFragment(activity,title,msg)
        Log.d("Permissions -> ","handleBluetoothPermissionDenied")
    }

    private fun handleBluetoothAdvertisePermissionDenied() {
        Log.d("Permissions -> ","handleBluetoothAdvertisePermissionDenied")
    }

    private fun handleBluetoothScanPermissionDenied() {
        Log.d("Permissions -> ","handleBluetoothScanPermissionDenied")

    }

    private fun handleBluetoothConnectPermissionDenied(activity: AppCompatActivity) {
        Log.d("Permissions -> ","handleBluetoothConnectPermissionDenied")
    }

    private fun handleFineLocationPermissionDenied(activity: AppCompatActivity) {
        val title = "Please Grant Required Permissions"
        val appName = activity.getString(R.string.app_name)
        val msg = "${appName}® requires high-precision\n" +
                "location access and the \"Nearby\n" +
                "Devices\" permission (Android 12+ only)\n" +
                "to enable unfiltered Bluetooth Low\n" +
                "Energy scanning as part of Android's\n" +
                "requirements.\n" +
                "\n" +
                "Your location is never used by\n" +
                "${appName}® itself."
        showDialogFragment(activity,title,msg)
        Log.d("Permissions -> ","handleFineLocationPermissionDenied")
    }

    private fun handleCoarseLocationPermissionDenied() {
        Log.d("Permissions -> ","handleCoarseLocationPermissionDenied")
    }

    private fun handleBothLocationPermissionsDenied(activity: AppCompatActivity) {
        val title = "Location Permission Required"
        val msg = "Starting from Android M (6.0), the system\n" +
                "requires apps to be granted location access\n" +
                "in order to scan for BLE devices.\n" +
                "\n" +
                "Android 12 and newer versions of Android\n" +
                "let developers use a different set of\n" +
                "Bluetooth permissions, but by omitting the\n" +
                "location permission. some BLE beacons may\n" +
                "not show up, so we have decided to keep\n" +
                "location access as a required permission to\n" +
                "use the app.\n" +
                "\n" +
                "Your location is never actually used aside\n" +
                "from enabling the app to perform BLE\n" +
                "scans."
        showDialogFragment(activity,title,msg)
        Log.d("Permissions -> ","handleCoarseLocationPermissionDenied")
    }

}


