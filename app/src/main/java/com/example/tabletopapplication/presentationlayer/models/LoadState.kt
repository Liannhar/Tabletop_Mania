package com.example.tabletopapplication.presentationlayer.models

sealed class LoadState {

    class Initialized(): LoadState()
    class Pending(): LoadState()
    class Success<T>(val result: T): LoadState()
    class Error(val error: Throwable): LoadState()

}