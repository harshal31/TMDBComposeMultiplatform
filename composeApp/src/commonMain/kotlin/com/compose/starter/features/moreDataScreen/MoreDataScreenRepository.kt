package com.compose.starter.features.moreDataScreen

import com.compose.starter.networking.NetworkManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

class MoreDataScreenRepository(
    private val network: NetworkManager,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

}