package org.sundog.copy.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.sundog.copy.data.repository.CopyContentRepository
import org.sundog.copy.data.repository.CopyContentRepositoryImpl

val CopyContentRepositoryModule = module {
    singleOf(::CopyContentRepositoryImpl) bind CopyContentRepository::class
}