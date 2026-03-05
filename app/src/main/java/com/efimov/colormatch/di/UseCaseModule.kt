package com.efimov.colormatch.di

import com.efimov.colormatch.data.image.ImageProcessorImpl
import com.efimov.colormatch.domain.repository.HistoryRepository
import com.efimov.colormatch.domain.usercase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideImageProcessor(): ImageProcessor {
        return ImageProcessorImpl()
    }

    @Provides
    fun provideGetClosestColorUseCase(): GetClosestColorUseCase {
        return GetClosestColorUseCase()
    }

    @Provides
    fun provideComputeHarmoniesUseCase(
        getClosestColor: GetClosestColorUseCase
    ): ComputeHarmoniesUseCase {
        return ComputeHarmoniesUseCase(getClosestColor)
    }

    @Provides
    @Singleton
    fun provideProcessImageUseCase(
        imageProcessor: ImageProcessor,
        getClosestColor: GetClosestColorUseCase
    ): ProcessImageUseCase {
        return ProcessImageUseCase(imageProcessor, getClosestColor)
    }

    @Provides
    fun provideAddHistoryEntryUseCase(
        repository: HistoryRepository
    ): AddHistoryEntryUseCase = AddHistoryEntryUseCase(repository)

    @Provides
    fun provideGetHistoryUseCase(
        repository: HistoryRepository
    ): GetHistoryUseCase = GetHistoryUseCase(repository)

    @Provides
    fun provideDeleteHistoryEntryUseCase(
        repository: HistoryRepository
    ): DeleteHistoryEntryUseCase = DeleteHistoryEntryUseCase(repository)

    @Provides
    fun provideClearHistoryUseCase(
        repository: HistoryRepository
    ): ClearHistoryUseCase = ClearHistoryUseCase(repository)
}