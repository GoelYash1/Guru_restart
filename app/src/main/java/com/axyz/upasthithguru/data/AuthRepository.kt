package com.axyz.upasthithguru.data

import android.util.Log
import com.axyz.upasthithguru.apidata.LoginRequest
import com.axyz.upasthithguru.apidata.ResponseObj
import com.axyz.upasthithguru.apidata.SignupRequest
import com.axyz.upasthithguru.app
import com.axyz.upasthithguru.other.Resource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.mongodb.Credentials
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * Repository allowing users to create accounts or log in to the app with an existing account.
 */
interface AuthRepository {
    /**
     * Creates an account with the specified [email] and [password].
     */
    suspend fun createAccount(email:String,password:String): Resource<ResponseObj>

    /**
     * Logs in with the specified [email] and [password].
     */
    suspend fun login(email: String,password: String): Resource<ResponseObj>
}
/**
 * [AuthRepository] for authenticating with MongoDB.
 */

class RealmAuthRepository @Inject constructor(): AuthRepository {


    override suspend fun createAccount(email: String,password: String): Resource<ResponseObj> {
        return try {
            val res = app.emailPasswordAuth.registerUser(email, password)
            Log.d("Create Account EmailPass :: ","Result -- $res")
            Resource.Success(ResponseObj("true","Successfull","asdfasdflkaj"))
        } catch (e: IOException) {
            Log.e("LoginActivity", "IOEException, no internet?", e)
            Resource.Error("Network Failure")
        } catch (e: HttpException) {
            Log.e("LoginActivity", "HTTP exception, unexpected response", e)
            Resource.Error("Network Failure")
        }
    }

    override suspend fun login(email: String,password: String): Resource<ResponseObj> {
        return try {
            val res = app.login(Credentials.emailPassword(email, password))
            Log.d("Create Account EmailPass :: ","Result -- $res")
            Resource.Success(ResponseObj("true","Successfull","asdfasdflkaj"))
        } catch (e: IOException) {
            Log.e("LoginActivity", "IOEException, no internet?", e)
            Resource.Error("Network Failure")
        } catch (e: HttpException) {
            Log.e("LoginActivity", "HTTP exception, unexpected response", e)
            Resource.Error("Network Failure")
        }
    }
}
