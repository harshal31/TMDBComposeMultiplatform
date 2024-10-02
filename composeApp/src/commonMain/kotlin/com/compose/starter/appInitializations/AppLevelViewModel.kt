package com.compose.starter.appInitializations

import androidx.lifecycle.ViewModel
import com.compose.starter.localData.LocalStore

class AppLevelViewModel(store: LocalStore) : ViewModel() {
    val themState = store.getAsyncString(LocalStore.THEME)
}
