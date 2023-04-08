package com.axyz.upasthithguru.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.axyz.upasthithguru.apidata.LoginRequest
import com.axyz.upasthithguru.apidata.ResponseObj
import com.axyz.upasthithguru.apidata.SignupRequest
import com.axyz.upasthithguru.data.AuthRepository
import com.axyz.upasthithguru.other.Event
import com.axyz.upasthithguru.other.Resource

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
//    constructor() : this(authRepository = AuthRepository())

    // Mutable Value
    private val _loginStatus = MutableLiveData<Event<Resource<ResponseObj>>>()

    // Immutable Value
    val signupStatus: LiveData<Event<Resource<ResponseObj>>> = _loginStatus

    fun login(email:String,password:String){
        _loginStatus.postValue(Event(Resource.Loading()))
        viewModelScope.launch(Dispatchers.IO){
            val resp = authRepository.login(email, password)
            _loginStatus.postValue(Event(resp))
        }
    }
}

private fun <T> MutableLiveData<T>.postValue(event: Event<Unit>) {

}
