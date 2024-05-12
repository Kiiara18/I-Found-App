package com.example.i_found.repository

import com.example.i_found.models.lost
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.lang.Error


const val LOST_COLLECTION_REF = "lost"

class StorageRepository (){
    fun user() = Firebase.auth.currentUser
    fun hasUser():Boolean = Firebase.auth.currentUser != null

    fun getUserId():String = Firebase.auth.currentUser?.uid.orEmpty()

    private val lostRef:CollectionReference = Firebase
        .firestore.collection(LOST_COLLECTION_REF)



    fun getLostItems(
        userId:String
    ):Flow<Resources<List<lost>>> = callbackFlow {
        var snapshotStateListener:ListenerRegistration? = null

        try {
            snapshotStateListener = lostRef
                .orderBy("name",Query.Direction.DESCENDING)
                .whereEqualTo("userId",userId)
                .addSnapshotListener{ snapshot,e ->
                    val response = if( snapshot != null){
                        val lost = snapshot.toObjects(lost::class.java)
                        Resources.Success(data = lost)

                    }else {
                        Resources.Error(throwable = e?.cause)
                    }
                    trySend(response)
                }
        }catch (e:Exception){
            trySend(Resources.Error(e?.cause))
            e.printStackTrace()

        }
        awaitClose {
            snapshotStateListener?.remove()
        }

    }

    fun getLost(
        lostId: String,
        onError:(Throwable?) -> Unit,
        onSuccess:(lost?) -> Unit
    ){
        lostRef
            .document(lostId)
            .get()

    }


    fun addLost(
         userId:String,
         name:String  ,
         date:String ,
         description:String ,
         location:String ,
         contact:String,
         onComplete:(Boolean) -> Unit,


    ){
        val documentId = lostRef.document().id
        val lost = lost(
            userId,
            name,
            date,
            description,
            location,
            contact,
            documentId


        )
        lostRef
            .document(documentId)
            .set(lost)
            .addOnCompleteListener { result ->
                onComplete.invoke(result.isSuccessful)

            }


    }

    fun deleteitem(lostId:String,onComplete: (Boolean) -> Unit){
        lostRef.document(lostId)
            .delete()
            .addOnCompleteListener {
                onComplete.invoke(it.isSuccessful)
            }

    }

    fun updateitem(
        name:String  ,
        date:String ,
        description:String ,
        location:String ,
        lost: String,
        contact:String,
        lostId: String,
        onResult:(Boolean) -> Unit

    ){
        val updateData = hashMapOf<String,Any>(
            "name" to name,
            "date" to date,
            "description" to description,
            "location" to location,
            "lost" to lost,
            "contact" to contact,

        )

        lostRef.document(lostId)
            .update(updateData)
            .addOnCompleteListener {
                onResult(it.isSuccessful)
            }

    }

    fun signOut() = Firebase.auth.signOut()
}


sealed class Resources<T>(
    val data: T? = null,
    val throwable: Throwable? = null,
){
    class Loading<T>:Resources<T>()
    class Success<T>(data : T?):Resources<T>(data = data)
    class Error<T>(throwable: Throwable?):Resources<T>(throwable = throwable)



}
