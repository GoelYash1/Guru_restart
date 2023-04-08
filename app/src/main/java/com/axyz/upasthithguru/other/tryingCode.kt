//package com.axyz.upasthithguru.activity
//
//import android.Manifest
//import android.annotation.SuppressLint
//import android.bluetooth.*
//import android.bluetooth.le.*
//import android.content.Context
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.os.*
//import android.util.Log
//import android.view.View
//import android.widget.Button
//import android.widget.ProgressBar
//import android.widget.TextView
//import androidx.annotation.RequiresApi
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.app.ActivityCompat
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.axyz.upasthithguru.*
//import com.axyz.upasthithguru.Realm.ClassAttendanceManager
//import com.axyz.upasthithguru.Realm.StudentRecord
//import com.axyz.upasthithguru.adapters.studentAttendanceListAdapter
//import kotlinx.coroutines.delay
//import kotlinx.serialization.encodeToString
//import kotlinx.serialization.json.Json
//import org.json.JSONException
//import org.json.JSONObject
//import java.text.SimpleDateFormat
//import java.util.*
//
//// TODO refactor the code and look at the stopAttendanceButton for more info
//class StartAttendance2 : AppCompatActivity() {
//
//    class DiscoveredBluetoothDevice(val device: BluetoothDevice, val serviceUuids: List<ParcelUuid>)
//
//    private var scanCallback: ScanCallback? = null
//    private val deviceSet: MutableSet<DiscoveredBluetoothDevice> = mutableSetOf()
//
//
//    private var isScanning: Boolean = false
//    private var bluetoothLeScanner: BluetoothLeScanner? = null
////    private var scanCallback: ScanCallback? = null
//    val classAttendanceManager = ClassAttendanceManager()
////    val deviceSet = mutableListOf<DiscoveredBluetoothDevice>()
//    val rollList = mutableListOf<String>()
//    lateinit var adapter: studentAttendanceListAdapter
//
//    @RequiresApi(Build.VERSION_CODES.S)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.scan_for_attendence)
//
//        // pinTextView
//        val generatedPin: String? = intent.getStringExtra("Pin")
//        val pin = findViewById<TextView>(R.id.pinTextView)
//        pin.text = "Pin = " + generatedPin.toString()
//
//        // progressBar
//        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
//        progressBar.visibility = View.VISIBLE
//
//        val scanForAttendanceText = findViewById<TextView>(R.id.scanForAttendanceTextView)
//
//        // progressBar will run for 2 minutes
////        val handler = Handler(Looper.getMainLooper())
//
////        handler.postDelayed({
////            stopAttendance()
////            progressBar.visibility = View.GONE
////            scanForAttendanceText.text = "Scanning Complete!!!"
////        }, 5*60*1000)
//
//
//        val stopAttendanceButton = findViewById<Button>(R.id.stopAttendanceButton)
//        stopAttendanceButton.isEnabled = true
//        stopAttendanceButton.setOnClickListener {
////            stopAttendance()
//            // Add attendance
//            // TODO() some error in this part of the code or find another way to add attendance in the CourseRepository()
//            // CourseRepository().addAttendance("123", classAttendanceManager.getAttendance())
//
//            // Redirect to another activity
//            val intent = Intent(this, HomeActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
//
//        // studentRecyclerView
//        val studentRecyclerView = findViewById<RecyclerView>(R.id.students_recycler_view)
//        val layoutManager = LinearLayoutManager(this)
//        studentRecyclerView.layoutManager = layoutManager
//        adapter = studentAttendanceListAdapter(rollList)
//        studentRecyclerView.adapter = adapter
//
//        // Check if Bluetooth is supported on the device
//        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
//            ?: // Device does not support Bluetooth
//            return
//
//        // Check if Bluetooth is enabled
//        if (!bluetoothAdapter.isEnabled) {
//            // Bluetooth is not enabled, request permission to enable it
//            val MY_PERMISSION_REQUEST_CODE = 123
//            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
//            if (ActivityCompat.checkSelfPermission(
//                    this,
//                    Manifest.permission.BLUETOOTH_CONNECT,
//                ) != PackageManager.PERMISSION_GRANTED
//            ) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return
//            }
//            startActivityForResult(enableBtIntent, MY_PERMISSION_REQUEST_CODE)
//        }
//
//        startScan(bluetoothAdapter)
//
//    }
//
//
//
//    private fun startScan(bluetoothAdapter: BluetoothAdapter) {
//        println("Started Attendance Taking -- ")
//        // Initialize the BluetoothLeScanner and scan settings
//        val bluetoothLeScanner = bluetoothAdapter.bluetoothLeScanner
//        val scanSettings = ScanSettings.Builder().build()
//        val scanFilter = ScanFilter.Builder().build()
//
//        // Initialize the list to store scan results
//        val scanResultList: MutableList<ScanResult> = mutableListOf()
//
//        // Stop any ongoing scan and set the callback to null
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.BLUETOOTH_SCAN
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return
//        }
//        bluetoothLeScanner?.stopScan(scanCallback)
//        scanCallback = null
//
//        // Initialize the scanCallback object
//        scanCallback = object : ScanCallback() {
//            override fun onScanResult(callbackType: Int, result: ScanResult) {
//                // Add the scan result to the list
//                scanResultList.add(result)
//
//                // Get the ScanRecord object from the ScanResult
//                result.scanRecord?.let { scanRecord ->
//                    val device: BluetoothDevice = result.device
//                    // Check if the device is already in the list
//                    if (!deviceSet.any { it.device == device }) {
//                        // Print the service UUIDs
//                        val serviceUuids: List<ParcelUuid> = scanRecord.serviceUuids ?: emptyList()
//                        if (serviceUuids.isNotEmpty()) {
//                            println("Service UUIDs: $serviceUuids")
//                            for (service_uuid in serviceUuids) {
//                                if (service_uuid.toString().startsWith("f4f5f6f9")) {
//                                    // Device is new, and has our made uuid now add it to the list
//                                    println("Yes this $service_uuid starts with f4f5f6f9")
//                                    deviceSet.add(DiscoveredBluetoothDevice(device, serviceUuids))
//                                    connectToDevice(device)
//                                    println("Device Added ------------> $device")
//                                }
//                            }
//                        } else {
//                            println("No service UUIDs available")
//                        }
//                    }
//                }
//            }
//        }
//
//        // Start scanning for Bluetooth devices
//        println("Scanning Started Successfully ---------- ")
//        bluetoothLeScanner?.startScan(listOf(scanFilter), scanSettings, scanCallback)
//    }
//
//    @SuppressLint("MissingPermission")
//    private suspend fun readCharacteristic(gatt: BluetoothGatt): JSONObject? {
//        val service = gatt.getService(UUID.fromString(CustomServiceUuidPrefix)) ?: return null
//        val characteristic = service.getCharacteristic(UUID.fromString(CustomCharUuid)) ?: return null
//        val success = gatt.readCharacteristic(characteristic)
//        if (success) {
//            val jsonString = characteristic.getStringValue(0)
//            return jsonString?.let { JSONObject(it) }
//        }
//        return null
//    }
//
//    @SuppressLint("MissingPermission")
//    private fun connectToDevice(device: BluetoothDevice) {
//        println("Connect to Device -------------->>>> $device")
//        val gatt = device.connectGatt(this@StartAttendance2, false, object : BluetoothGattCallback() {
//            override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState:Int){
//            when (newState) {
//                BluetoothProfile.STATE_CONNECTED -> {
//                    println("Connected to device ------------->>>>> $device")
//// Discover services
//                    gatt.discoverServices()
//                }
//                BluetoothProfile.STATE_DISCONNECTED -> {
//                    println("Disconnected from device ------------->>>>> $device")
//// Remove the device from the list of connected devices
//                    deviceSet.removeIf { it.device == device }
//                }
//            }
//        })
//// Store the BluetoothGatt object in the list of connected devices
//        deviceSet.add(DiscoveredBluetoothDevice(device, emptyList(), gatt))
//    }
//
//    private fun disconnectFromDevice(device: BluetoothDevice) {
//// Find the BluetoothGatt object for the device
//        val discoveredDevice = deviceSet.find { it.device == device } ?: return
//// Disconnect the device and close the BluetoothGatt object
//        discoveredDevice.gatt.disconnect()
//        discoveredDevice.gatt.close()
//// Remove the device from the list of connected devices
//        deviceSet.remove(discoveredDevice)
//    }
//
//    private fun stopScan(bluetoothAdapter: BluetoothAdapter) {
//// Stop the scan and set the callback to null
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.BLUETOOTH_SCAN
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return
//        }
//        bluetoothAdapter.bluetoothLeScanner?.stopScan(scanCallback)
//        scanCallback = null
//    }
//
//    private fun stopAllScans() {
//// Stop scanning for all Bluetooth devices
//        BluetoothAdapter.getDefaultAdapter()?.let { stopScan(it) }
//    }
//
//    private fun stopAllConnections() {
//// Disconnect and close all connected devices
//        deviceSet.toList().forEach { disconnectFromDevice(it.device) }
//    }
//
//    private suspend fun takeAttendance() {
//// Get the current time
//        val currentTime = System.currentTimeMillis()
//// Set the timeout to 10 seconds
//        val timeout = 10000L
//// Scan for Bluetooth devices and connect to any that have the correct service UUID
//        startScan(BluetoothAdapter.getDefaultAdapter())
//// Wait for devices to connect for up to the timeout period
//        while (System.currentTimeMillis() - currentTime < timeout) {
//            if (deviceSet.any { it.serviceUuids.any { uuid -> uuid.toString().startsWith("f4f5f6f9") } }) {
//// At least one device with the correct service UUID has connected
//                break
//            }
//            delay(1000L)
//        }
//// Stop scanning for Bluetooth devices
//        stopAllScans()
//// Read data from all connected devices and add it to a list
//        val attendanceList: MutableList<JSONObject> = mutableListOf()
//        deviceSet.forEach { discoveredDevice ->
//            discoveredDevice.gatt?.let { gatt ->
//                readCharacteristic(gatt)?.let { attendance ->
//                    attendance.put("device", discoveredDevice.device.address)
//                    attendanceList.add(attendance)
//                }
//            }
//        }
//// Print the attendance list
//        println("Attendance List: $attendanceList")
//// Disconnect from all connected devices
//        stopAllConnections()
//    }
//
//    suspend fun startAttendance() {
//// Take attendance
//        takeAttendance()
//    }
//
//
//
//
//
//    fun storeRecord(studentRollNo: String) {
//        rollList.add(studentRollNo)
//        val emailid =
//            getSharedPreferences("upasthithGuru", Context.MODE_PRIVATE).getString("email", "") ?: ""
//        classAttendanceManager.addStudentRecord(StudentRecord(emailid, true, "success", Date()))
//    }
//
//
//    fun addTimeToString(s: String): String {
//        val currentTime = Calendar.getInstance().time
//        val formatter = SimpleDateFormat("HH:mm:ss")
//        val time = formatter.format(currentTime)
//        return "$s - $time"
//    }
//
//    override fun onBackPressed() {
//        // Do nothing
//    }
//}