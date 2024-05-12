package com.example.i_found.ui.theme.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.i_found.models.lost
import com.example.i_found.repository.Resources
import com.example.i_found.repository.StorageRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: StorageRepository
) :ViewModel() {
    var homeUIState by mutableStateOf(HomeUIState())

    val user = repository.user()
    val hasUser: Boolean
        get() = repository.hasUser()
    private val userId: String
        get() = repository.getUserId()

    fun loadItems() {
        if (hasUser) {
            if (userId.isNotBlank()){
                getLostItems(userId)
            }

        }else{
            homeUIState = homeUIState.copy(lostList = Resources.Error(
                throwable = Throwable(message = "User is not logged in")
            ))
        }


    }

    private fun getLostItems(userId:String)= viewModelScope.launch {
        repository.getLostItems(userId).collect{
            homeUIState = homeUIState.copy(lostList = it)
        }
    }

    fun deleteLostItem(lostId: String) = repository.deleteitem(lostId){
        homeUIState = homeUIState.copy(itemDeletedStatus = it)
    }

    fun signOut() = repository.signOut()


}


data class HomeUIState(
    val lostList: Resources<List<lost>> = Resources.Loading(),
    val itemDeletedStatus:Boolean = false
)