package com.aikei.booklibrary.di

import com.aikei.booklibrary.data.remote.ApiService
import com.aikei.booklibrary.data.repository.BookRepository
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.Provides

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {
    @Provides
    fun provideBookRepository(apiService: ApiService): BookRepository = BookRepository(apiService)
}
