package com.efimov.colormatch.di

import com.efimov.colormatch.data.db.HistoryDao
import com.efimov.colormatch.data.repository.HistoryRepositoryImpl
import com.efimov.colormatch.domain.repository.HistoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideHistoryRepository(dao: HistoryDao): HistoryRepository {
        return HistoryRepositoryImpl(dao)
    }
}