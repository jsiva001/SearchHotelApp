package com.siva.data.di

import com.siva.data.repository.SearchHotelRepoImpl
import com.siva.domain.repository.SearchHotelRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindSearchRepository(searchHotelRepoImpl: SearchHotelRepoImpl): SearchHotelRepository

}