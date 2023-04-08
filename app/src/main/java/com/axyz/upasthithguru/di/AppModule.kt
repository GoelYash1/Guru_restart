package com.axyz.upasthithguru.di

import android.content.Context
import android.content.SharedPreferences
import com.axyz.upasthithguru.R
import com.axyz.upasthithguru.Realm.CourseRepository
import com.axyz.upasthithguru.api.APIInterface
import com.axyz.upasthithguru.data.RealmSyncRepository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.mongodb.exceptions.SyncException
import io.realm.kotlin.mongodb.sync.SyncSession
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton // This determines that only one instance of this context exists
    @Provides
    fun provideApplicationContext(@ApplicationContext context: Context) =
        context

    @Singleton
    @Provides
    fun provideGson(): Gson = Gson()

    @Singleton
    @Provides
    fun providesRetrofitInstance(): Retrofit = Retrofit.Builder()
        .baseUrl("https://upasthit-backend.vercel.app/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun providesAPIInterface(retrofit: Retrofit): APIInterface = retrofit.create(APIInterface::class.java)

    @Singleton
    @Provides
    fun providesSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("upasthithGuru", Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideRealmSyncRepository(@Named("syncRepo") onSyncError: (session: SyncSession, error: SyncException) -> Unit): RealmSyncRepository {
        return RealmSyncRepository(onSyncError)
    }

//    @Provides
//    fun provideCourseRepository(): CourseRepository {
//        return CourseRepository()
//    }
}
