package com.tabletop.tabletopapplication.businesslayer.API.common

sealed class LoadState {

    class Initialized(): LoadState()
    class Pending(): LoadState()
    class Success<T>(val result: T): LoadState()
    class Error(val error: Throwable): LoadState()

}