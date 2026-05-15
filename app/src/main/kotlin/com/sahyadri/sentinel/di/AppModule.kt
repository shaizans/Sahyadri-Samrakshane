package com.sahyadri.sentinel.di

import android.app.Application
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.sahyadri.sentinel.data.location.DefaultLocationTracker
import com.sahyadri.sentinel.data.repository.AuthRepositoryImpl
import com.sahyadri.sentinel.domain.location.LocationTracker
import com.sahyadri.sentinel.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepository(firebaseAuth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(app: Application): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(app)
    }

    @Provides
    @Singleton
    fun provideLocationTracker(
        fusedLocationProviderClient: FusedLocationProviderClient,
        app: Application
    ): LocationTracker {
        return DefaultLocationTracker(fusedLocationProviderClient, app)
    }
}
