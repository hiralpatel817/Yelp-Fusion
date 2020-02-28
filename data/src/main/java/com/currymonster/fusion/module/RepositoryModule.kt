package com.currymonster.fusion.module

import com.currymonster.fusion.net.ReviewService
import com.currymonster.fusion.net.SearchService
import com.currymonster.fusion.repository.*
import dagger.Module
import dagger.Provides

@Module
open class RepositoryModule {

    @Provides
    @SessionScope
    open fun provideVersionRepository(): VersionRepository = VersionRepositoryImpl()

    @Provides
    @SessionScope
    open fun providesSearchRepository(searchService: SearchService): SearchRepository =
        SearchRepositoryImpl(searchService)

    @Provides
    @SessionScope
    open fun providesReviewsRepository(reviewService: ReviewService): ReviewsRepository =
        ReviewsRepositoryImpl(reviewService)
}