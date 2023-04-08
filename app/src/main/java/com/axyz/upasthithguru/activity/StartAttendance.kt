package com.axyz.upasthithguru.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.*
import android.bluetooth.le.*
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.*
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.axyz.upasthithguru.*
import com.axyz.upasthithguru.Realm.ClassAttendanceManager
import com.axyz.upasthithguru.Realm.CourseRepository
import com.axyz.upasthithguru.Realm.StudentRecord
import com.axyz.upasthithguru.adapters.studentAttendanceListAdapter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

// TODO refactor the code and look at the stopAttendanceButton for more info
class StartAttendance : AppCompatActivity() {

    class DiscoveredBluetoothDevice(val device: BluetoothDevice, val serviceUuids: List<ParcelUuid>)

    private var isScanning: Boolean = false
    private var bluetoothLeScanner: BluetoothLeScanner? = null
    private var scanCallback: ScanCallback? = null
    val classAttendanceManager = ClassAttendanceManager()
    val deviceSet = mutableListOf<DiscoveredBluetoothDevice>()
    val rollList = mutableListOf<String>()
    lateinit var adapter: studentAttendanceListAdapter
    lateinit var courseId: String
    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scan_for_attendence)
        courseId = intent.getStringExtra("Course Id").toString()
        // pinTextView
        val generatedPin: String? = intent.getStringExtra("Pin")
        val pin = findViewById<TextView>(R.id.pinTextView)
        pin.text = "Pin = " + generatedPin.toString()

        // progressBar
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
        progressBar.visibility = View.VISIBLE

        val scanForAttendanceText = findViewById<TextView>(R.id.scanForAttendanceTextView)

        // progressBar will run for 2 minutes
        val handler = Handler(Looper.getMainLooper())

//        handler.postDelayed({
//            stopAttendance()
//            progressBar.visibility = View.GONE
//            scanForAttendanceText.text = "Scanning Complete!!!"
//        }, 5*60*1000)

        // students Present
        var totalStudentsPresent = findViewById<TextView>(R.id.totalStudentsPresentTextView)
        val stopAttendanceButton = findViewById<Button>(R.id.stopAttendanceButton)
        stopAttendanceButton.isEnabled = true
        stopAttendanceButton.setOnClickListener {
            stopAttendance()
            // Add attendance
            // TODO() some error in this part of the code or find another way to add attendance in the CourseRepository()
//            Log.d(TAG,"Saving the Course attendance - $courseId -> ${classAttendanceManager.getAttendance()}")
//            CourseRepository().addAttendance(courseId, classAttendanceManager.getAttendance())

            // Redirect to another activity
            val intent = Intent(this, ViewStudentAttendance::class.java)
            Toast.makeText(this,"CourseId$courseId",Toast.LENGTH_SHORT).show()
            intent.putExtra("Course Id",courseId)
            startActivity(intent)
            finish()
        }

        // studentRecyclerView
        val studentRecyclerView = findViewById<RecyclerView>(R.id.students_recycler_view)
        val layoutManager = LinearLayoutManager(this)
        studentRecyclerView.layoutManager = layoutManager
        adapter = studentAttendanceListAdapter(rollList)
        studentRecyclerView.adapter = adapter
        totalStudentsPresent.text = adapter.itemCount.toString()

        // Check if Bluetooth is supported on the device
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
            ?: // Device does not support Bluetooth
            return

        val REQUEST_ENABLE_BT = 123
// Check if Bluetooth is enabled
        if (!bluetoothAdapter.isEnabled) {
            // Bluetooth is not enabled, request permission to enable it
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
        }else{
            startScan(bluetoothAdapter)
        }



    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 123) {
            if (resultCode == Activity.RESULT_OK) {
                // Bluetooth is now enabled, do something
                val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
                    ?: // Device does not support Bluetooth
                    return
                startScan(bluetoothAdapter)
            } else {
//                Toast(this,this@StartAttendance,)
                Log.e(TAG, "User Denied to Turn on Bluetooth")
                // User did not enable Bluetooth, handle this case
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun startScan(bluetoothAdapter: BluetoothAdapter) {
        Log.d(TAG, "Started Attendance Taking")
        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Log.e(TAG, "Device does not support Bluetooth LE")
            return
        }

        // Initialize the BluetoothLeScanner and scan settings
        bluetoothLeScanner = bluetoothAdapter.bluetoothLeScanner
        val scanSettings = ScanSettings.Builder().build()
        val scanFilter = ScanFilter.Builder().build()

        // Initialize the list to store scan results
        val scanResultList = mutableListOf<ScanResult>()

        // Stop any ongoing scan and set the callback to null
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.BLUETOOTH_SCAN
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf(Manifest.permission.BLUETOOTH_SCAN),
//                1
//            )
//            Log.d(TAG,"Bluetooth Scan Stopped ---xxx--- permission check bluetoothLeScanner Stop any ongoing scan and set the callback to null ")
//            return
//        }
        bluetoothLeScanner?.stopScan(scanCallback)

        scanCallback = null

        // Initialize the scanCallback object
        scanCallback = object : ScanCallback() {
            override fun onScanResult(callbackType: Int, result: ScanResult) {
                // Add the scan result to the list
                scanResultList.add(result)

                // Get the ScanRecord object from the ScanResult
                val scanRecord = result.scanRecord

                val device: BluetoothDevice = result.device
                // Check if the device is already in the list
                if (deviceSet.any { it.device == device }) {
                    // Device is already in the list, do nothing
                } else {
//                    Log.d(TAG,"New Device Discovered :: ${device.address}")
                    // Print the service UUIDs
                    val serviceUuids: List<ParcelUuid> = scanRecord?.serviceUuids ?: emptyList()
                    if (serviceUuids.isNotEmpty()) {
//                        Log.d(TAG, "Service UUIDs: $serviceUuids")
                        for (service_uuid in serviceUuids) {
                            if (service_uuid.toString().startsWith("f4f5f6f9")) {
                                // Device is new, and has our made uuid now add it to the list
                                Log.d(TAG, "------> Yes this $service_uuid starts with f4f5f6f9")

                                Log.d(TAG, "------> Device Added - $device")
                                deviceSet.add(DiscoveredBluetoothDevice(device, serviceUuids))
                                connectToDevice(device)
                            }
                        }
                    } else {
                        Log.d(TAG, "No service UUIDs available")
                    }
                }
            }
        }

        // Start scanning for Bluetooth devices
        Log.d(TAG, "Scanning Started Successfully")
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.BLUETOOTH_SCAN
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            return
//        }
        bluetoothLeScanner?.startScan(listOf(scanFilter), scanSettings, scanCallback)
        isScanning = true
    }

    @SuppressLint("MissingPermission")
    fun connectToDevice(device: BluetoothDevice) {
        println("------> Request for Connecting to Device - $device")
        device.connectGatt(this@StartAttendance, false, object : BluetoothGattCallback() {
            lateinit var studentRollNo: String
            override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    Log.d(TAG,"------> Connected to Device :: -> $device and discovering services")
                    gatt.discoverServices()
                }
                if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    Log.d(TAG,"------> Disconnected from the device :: -> $device")
                }
            }

            override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
                if (status == BluetoothGatt.GATT_SUCCESS) {
                    Log.d(TAG,"------> Services Discovered ");
                    val services = gatt.services
                    var isFound = false;
                    for (service in services) {
                        if (service.uuid.toString().startsWith(CustomServiceUuidPrefix)) {
                            isFound = true
                            // make uuid from our customCharUuid
                            val characteristicId = UUID.fromString(CustomCharUuid)
                            // get that characteristic
                            val characteristic = service.getCharacteristic(characteristicId)
                            // send the request to read the characteristic to get the rollno.
                            if (gatt.readCharacteristic(characteristic)) {
                                // Request was sent successfully
                                Log.d(TAG,"------> Request was sent successfully ");
//                                println("Request was sent successfully")
                            } else {
                                // Request failed
                                Log.d(TAG,"------> Failed to request was sent request");
                            }
                        }
                    }
                    if(!isFound){
//                        disconnect from the device
                        Log.d(TAG,"---xxxxx---> OUR SERVICE ID WAS NOT FOUND")
                        gatt.disconnect()
                    }
                }else{
                    Log.d(TAG,"---xxxxx---> Failed to discover services")
                }
            }

            override fun onCharacteristicRead(
                gatt: BluetoothGatt?,
                characteristic: BluetoothGattCharacteristic?,
                status: Int
            ) {
                super.onCharacteristicRead(gatt, characteristic, status)
                if (status == BluetoothGatt.GATT_SUCCESS) {
                    // Characteristic read successfully
                    val jsonString = characteristic?.getStringValue(0)
                    try {
                        val jsonObject = jsonString?.let { JSONObject(it) }
                        // do something with the JSON object
                        if (jsonObject != null) {
                            Log.d(TAG,"------> Characteristic Read -> ${jsonObject.get("RollNo") as String}");
                            // get the RollNo of the student
                            val RollNo = addTimeToString(jsonObject.get("RollNo") as String)

                            // mark the attendance or store it in the record
                            storeRecord(RollNo)

//                            deviceSet.remove(device as BluetoothDevice)
//                            deviceSet.remove(device)

                            val handler = Handler(Looper.getMainLooper())
                            handler.post {
                                adapter.notifyDataSetChanged()
                            }

                            // send request to write characteristic
                            // make data to be written
                            val characteristicData = CharacteristicDataClass(true)
                            val dataJsonString = Json.encodeToString(characteristicData)
                            val value = dataJsonString.toByteArray(Charsets.UTF_8)
                            // write the new data
                            characteristic.writeType =
                                BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT
                            characteristic.value = value
                            if (gatt != null) {
                                if (gatt.writeCharacteristic(characteristic)) {
                                    // Request was sent successfully
                                    Log.d(TAG,"------> Write Request -> $value");
//                                    println("Write Request ------------> $value")
                                } else {
                                    Log.d(TAG,"---xxxx---> Write Request -> FAILED");
//                                    println("Write Request ------------> FAILED")
                                    // Request failed
                                }
                            }else{
                                Log.d(TAG,"---xxxxx---> GATT WAS NULL")
                            }
                        }

                    } catch (e: JSONException) {
                        Log.e("JSON", "Error decoding JSON string", e)
                    }
                } else {
                    // Characteristic read failed
                    Log.d(TAG,"---xxxxx---> Characteristic READ FAILED")
//                    println("Characteristic read failed")
                    if (gatt != null) {
                        gatt.disconnect()
                        Log.d(TAG,"---xxxxx---> Disconnected from the device")
                    }else{
                        Log.d(TAG,"---xxxxx---> But FAILED to disconnected from the device")
                    }
                }
            }

            override fun onCharacteristicWrite(
                gatt: BluetoothGatt?,
                characteristic: BluetoothGattCharacteristic?,
                status: Int
            ) {
                super.onCharacteristicWrite(gatt, characteristic, status)
                Log.d(TAG,"--->>>---> Characteristic Wrote Successfully -> ::  $device");
//                println("Characteristic Wrote Successfully -> ::  $device")

//                rollList.add(studentRollNo)
                if (gatt != null) {
                    gatt.disconnect()
                }else{
                    Log.d(TAG,"---xxxxx---> Characteristic READ FAILED")
                }
            }
        })

    }

    @SuppressLint("MissingPermission")
    private fun stopAttendance() {
        // Disable Bluetooth scanning
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH_SCAN
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
//        bluetoothLeScanner?.stopScan(scanCallback)

        // Disable Bluetooth
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter.isEnabled) {
            bluetoothAdapter.disable()
        }
    }

    fun storeRecord(studentRollNo: String) {
        rollList.add(studentRollNo)
        val emailid =
            getSharedPreferences("upasthithGuru", Context.MODE_PRIVATE).getString("email", "") ?: ""
//        classAttendanceManager.addStudentRecord(StudentRecord(emailid, true, "success", Date()))
        Log.d(TAG,"------> Added Record to the Database")
    }

    fun addTimeToString(s: String): String {
        val currentTime = Calendar.getInstance().time
        val formatter = SimpleDateFormat("HH:mm:ss")
        val time = formatter.format(currentTime)
        return "$s - $time"
    }

    override fun onBackPressed() {
        // Do nothing
    }
}