package com.compose.starter.features.homeScreen

import com.compose.starter.localData.LocalStore
import com.compose.starter.networking.NetworkManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.first

class HomeScreenRepository(
    private val network: NetworkManager,
    private val store: LocalStore,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {



}