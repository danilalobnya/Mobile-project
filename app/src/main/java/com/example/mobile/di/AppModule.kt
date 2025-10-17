package com.example.mobile.di

import android.content.Context
import com.example.mobile.core.datastore.AppPreferences
import com.example.mobile.data.local.LocalDataSource
import com.example.mobile.data.local.LocalDataSourceImpl
import com.example.mobile.data.remote.RemoteDataSource
import com.example.mobile.data.remote.RemoteDataSourceImpl
import com.example.mobile.data.repository.AuthRepositoryImpl
import com.example.mobile.domain.repository.AuthRepository
import com.example.mobile.domain.service.ValidationService
import com.example.mobile.domain.usecase.LoginUseCase
import com.example.mobile.domain.usecase.RegisterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * AppModule: модуль Hilt для предоставления зависимостей приложения.
 * Определяет, как создавать и предоставлять различные компоненты системы.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    
    /**
     * Предоставляет AppPreferences для работы с DataStore.
     */
    @Provides
    @Singleton
    fun provideAppPreferences(@ApplicationContext context: Context): AppPreferences {
        return AppPreferences(context)
    }
    
    /**
     * Предоставляет LocalDataSource для работы с локальными данными.
     */
    @Provides
    @Singleton
    fun provideLocalDataSource(appPreferences: AppPreferences): LocalDataSource {
        return LocalDataSourceImpl(appPreferences)
    }
    
    /**
     * Предоставляет RemoteDataSource для работы с API.
     */
    @Provides
    @Singleton
    fun provideRemoteDataSource(): RemoteDataSource {
        return RemoteDataSourceImpl()
    }
    
    /**
     * Предоставляет AuthRepository для работы с аутентификацией.
     */
    @Provides
    @Singleton
    fun provideAuthRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): AuthRepository {
        return AuthRepositoryImpl(localDataSource, remoteDataSource)
    }
    
    /**
     * Предоставляет ValidationService для валидации данных.
     */
    @Provides
    @Singleton
    fun provideValidationService(): ValidationService {
        return ValidationService()
    }
    
    /**
     * Предоставляет LoginUseCase для входа пользователя.
     */
    @Provides
    @Singleton
    fun provideLoginUseCase(
        authRepository: AuthRepository,
        validationService: ValidationService
    ): LoginUseCase {
        return LoginUseCase(authRepository, validationService)
    }
    
    /**
     * Предоставляет RegisterUseCase для регистрации пользователя.
     */
    @Provides
    @Singleton
    fun provideRegisterUseCase(
        authRepository: AuthRepository,
        validationService: ValidationService
    ): RegisterUseCase {
        return RegisterUseCase(authRepository, validationService)
    }
}
