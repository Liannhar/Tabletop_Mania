package com.example.tabletopapplication.presentationlayer.models

import com.example.tabletopapplication.businesslayer.models.GameEntity

sealed class LoadState {

    class Initialized(): LoadState()

    class Pending(): LoadState()

    class Success(val result: List<GameEntity>): LoadState()

    class Error(val error: Throwable): LoadState()

}