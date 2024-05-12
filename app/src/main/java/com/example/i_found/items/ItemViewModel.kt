package com.example.i_found.items

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.contentValuesOf
import androidx.lifecycle.ViewModel
import com.example.i_found.models.lost
import com.example.i_found.repository.StorageRepository
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentId

class ItemViewModel (
    private val repository : StorageRepository
):ViewModel(){
    var itemUIState by mutableStateOf(ItemUIState())
        private set

    private val hasUser: Boolean
        get() = repository.hasUser()

    private val user : FirebaseUser?
        get() = repository.user()


    fun onNameChange(name: String){
        itemUIState = itemUIState.copy(name = name)
    }

    fun ondateChange(date: String){
        itemUIState = itemUIState.copy(date = date)
    }
    fun ondescriptionChange(description: String){
        itemUIState = itemUIState.copy(description = description)
    }
    fun onlocationChange(location: String){
        itemUIState = itemUIState.copy(location = location)
    }
    fun oncontactChange(contact: String){
        itemUIState = itemUIState.copy(contact = contact)
    }

    fun addItem(){
        if (hasUser){
        repository.addLost(
            userId = user!!.uid,
            name = itemUIState.name,
            date = itemUIState.date,
            description = itemUIState.description,
            location = itemUIState.location,
            contact = itemUIState.contact




        ){
            itemUIState = itemUIState.copy(itemAddedStatus = it)
        }

        }
    }

    fun setEditField(lost: lost){
        itemUIState = itemUIState.copy(
            name = lost.name,
            date = lost.date,
            description = lost.description,
            location = lost.location,
            contact = lost.contact,

        )

    }

    fun getLost(lostId: String){
        repository.getLost(
            lostId = lostId,
            onError = {},
        ){
            itemUIState = itemUIState.copy(selectedItem = it)
            itemUIState.selectedItem?.let { it1-> setEditField(it1) }
        }
    }

    fun resetState(){
        itemUIState = ItemUIState()

    }

    fun updateLost(
        lostId: String
    ){
        repository.updateitem(
            description = itemUIState.description,
            lost = itemUIState.lost,
            lostId = lostId,
            name = itemUIState.name,
            date = itemUIState.date,
            location = itemUIState.location,
            contact = itemUIState.contact
        ){
            itemUIState = itemUIState.copy(updateItemStatus = it)
        }
    }

    fun resetItemAddedStatus(){
        itemUIState = itemUIState.copy(
            itemAddedStatus = false,
            updateItemStatus = false
        )
    }





}

data class ItemUIState(
    val name:String = "",
    val date:String = "",
    val description:String = "",
    val location:String = "",
    val lost:String = "",
    val contact:String = "",
    val updateItemStatus:Boolean = false,
    val itemAddedStatus:Boolean = false,
    val selectedItem:lost? = null
)