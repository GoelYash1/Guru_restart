package com.axyz.upasthithguru.di


import android.content.SharedPreferences
import com.axyz.upasthithguru.api.APIInterface
import com.axyz.upasthithguru.data.AuthRepository
import com.axyz.upasthithguru.data.RealmAuthRepository
//import com.axyz.upasthithguru.repositories.AuthRepository
//import com.axyz.upasthithguru.repositories.DefaultAuthRepository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object AuthModule {

//    @Provides
//    fun providesAuthRepository(retrofitApi: APIInterface, sharedPreferences: SharedPreferences, gson:Gson) =
//        DefaultAuthRepository(retrofitApi, sharedPreferences, gson) as AuthRepository

    @Provides
    fun provideAuthRepository(): AuthRepository {
        return RealmAuthRepository()
    }
}