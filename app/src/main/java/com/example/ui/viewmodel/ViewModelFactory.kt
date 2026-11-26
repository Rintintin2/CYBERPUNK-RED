package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.RedlineApplication

class RedlineViewModelFactory(
    private val app: RedlineApplication
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                campaignRepository = app.campaignRepository,
                characterRepository = app.characterRepository,
                sessionRepository = app.sessionRepository,
                ruleLibraryRepository = app.ruleLibraryRepository,
                skillCheckEngine = app.skillCheckEngine,
                netrunningEngine = app.netrunningEngine,
                gameMasterGateway = app.gameMasterGateway
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
