package com.axyz.upasthithguru.repositories
//
//
//import android.content.SharedPreferences
//import android.util.Log
//import com.axyz.upasthithguru.Realm.Course
//import com.axyz.upasthithguru.Realm.CourseRepository
//import com.axyz.upasthithguru.api.APIInterface
//import com.axyz.upasthithguru.apidata.LoginRequest
//import com.axyz.upasthithguru.apidata.ResponseObj
//import com.axyz.upasthithguru.apidata.SignupRequest
//import com.axyz.upasthithguru.app
//import com.axyz.upasthithguru.data.RealmAuthRepository
//import com.axyz.upasthithguru.other.Resource
//import com.google.gson.Gson
//import io.realm.kotlin.mongodb.Credentials
//import retrofit2.HttpException
//import java.io.IOException
//import javax.inject.Inject
//
//class DefaultAuthRepository @Inject constructor(
//    private val retrofitApi: APIInterface,
//    private val sharedPreferences: SharedPreferences,
//    private val gson: Gson
//) : AuthRepository {
//
//    override suspend fun loginUser(loginRequest: LoginRequest): Resource<ResponseObj> {
//        val email = loginRequest.email;
//        val password = loginRequest.password;
//
//        val response = try {
//
//            val authRepository: RealmAuthRepository = RealmAuthRepository()
//            authRepository.login(email, password)
//
//        } catch (e: IOException) {
//            Log.e("LoginActivity", "IOEException, no internet?", e)
//            return Resource.Error("Network Failure")
//        } catch (e: HttpException) {
//            Log.e("LoginActivity", "HTTP exception, unexpected response", e)
//            return Resource.Error("Network Failure")
//        }
//        Log.d("LoginActivity Login", "Response: $response")
//
//        return Resource.Error("Login Failed")
//
//
////        val response = try {
////            retrofitApi.login(loginRequest)
////        } catch (e: IOException) {
////            Log.e("LoginActivity", "IOEException, no internet?", e)
////            return Resource.Error("Network Failure")
////        } catch (e: HttpException) {
////            Log.e("LoginActivity", "HTTP exception, unexpected response", e)
////            return Resource.Error("Network Failure")
////        }
////        Log.d("LoginActivity", "Response: $response")
////        if (response.isSuccessful && response.body() != null) {
////            Log.d("LoginActivity", "Login Successful ${response.body()}")
////
//////            CourseRepository().insertCourse(Course("128","Software Defined Networking","CS2008","SDN Course","3","CSE","2020","5","lkjhlkjh"))
////
////            saveAuthToken(response.body()!!.authToken)
////            saveExtraDetails("Dummy",loginRequest.email,"Dummy Description")
////            return Resource.Success(response.body()!!)
////        } else {
////            Log.d("LoginActivity", "Login Failed")
////            response.errorBody()?.let {
////                Log.e("LoginActivity", it.string())
////            }
////        }
////        return Resource.Error("Login Failed")
//    }
//
//    override suspend fun signupUser(signupRequest: SignupRequest): Resource<ResponseObj> {
//        val email = signupRequest.email
//        val password = signupRequest.password
//        val response = try {
//            val authRepository: RealmAuthRepository = RealmAuthRepository()
//            authRepository.createAccount(email,password)
//        } catch (e: IOException) {
//            Log.e("LoginActivity", "IOEException, no internet?", e)
//            return Resource.Error("Network Failure")
//        } catch (e: HttpException) {
//            Log.e("LoginActivity", "HTTP exception, unexpected response", e)
//            return Resource.Error("Network Failure")
//        }
//
//        Log.d("LoginActivity Signup", "Response: $response")
//
////        val response = try {
////            retrofitApi.signup(signupRequest)
////        } catch (e: IOException) {
////            Log.e("LoginActivity", "IOEException, no internet?", e)
////            return Resource.Error("Network Failure")
////        } catch (e: HttpException) {
////            Log.e("LoginActivity", "HTTP exception, unexpected response", e)
////            return Resource.Error("Network Failure")
////        }
////        Log.d("LoginActivity", "Response: $response")
////        if (response.isSuccessful && response.body() != null) {
////            Log.d("LoginActivity", "Login Successful ${response.body()}")
////            saveAuthToken(response.body()!!.authToken)
////            saveExtraDetails(signupRequest.name,signupRequest.email,signupRequest.description)
////            return Resource.Success(response.body()!!)
////        } else {
////            Log.d("LoginActivity", "Login Failed")
////            response.errorBody()?.let {
////                Log.e("LoginActivity", it.string())
////            }
////        }
//        return Resource.Error("Login Failed")
//    }
//
//    override fun saveAuthToken(authToken: String) {
//        sharedPreferences.edit().putString("authToken", authToken).apply()
////        CourseRepository().insertCourse(Course("128","Software Defined Networking","CS2008","SDN Course","3","CSE","2020","5","lkjhlkjh"))
//
//    }
//
//    override fun saveExtraDetails(name:String,email:String,description: String){
//        sharedPreferences.edit().putString("name",name).apply()
//        sharedPreferences.edit().putString("email",email).apply()
//        sharedPreferences.edit().putString("description",description).apply()
//    }
//
//}