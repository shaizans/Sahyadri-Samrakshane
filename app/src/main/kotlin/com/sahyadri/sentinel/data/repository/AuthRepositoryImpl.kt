package com.sahyadri.sentinel.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.sahyadri.sentinel.core.util.Resource
import com.sahyadri.sentinel.domain.model.User
import com.sahyadri.sentinel.domain.repository.AuthRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    override fun login(email: String, password: String): Flow<Resource<User>> = callbackFlow {
        trySend(Resource.Loading())
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser = firebaseAuth.currentUser
                    val user = User(
                        uid = firebaseUser?.uid ?: "",
                        email = firebaseUser?.email
                    )
                    trySend(Resource.Success(user))
                } else {
                    trySend(Resource.Error(task.exception?.message ?: "Login failed"))
                }
            }
        awaitClose { }
    }

    override fun register(
        email: String,
        password: String,
        displayName: String,
        phoneNumber: String
    ): Flow<Resource<User>> = callbackFlow {
        trySend(Resource.Loading())
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser = firebaseAuth.currentUser
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(displayName)
                        .build()
                    
                    firebaseUser?.updateProfile(profileUpdates)?.addOnCompleteListener { profileTask ->
                        val user = User(
                            uid = firebaseUser.uid,
                            email = firebaseUser.email,
                            displayName = displayName,
                            phoneNumber = phoneNumber
                        )
                        
                        // Save extra details to Firestore
                        firestore.collection("users").document(firebaseUser.uid)
                            .set(mapOf(
                                "uid" to firebaseUser.uid,
                                "email" to firebaseUser.email,
                                "displayName" to displayName,
                                "phoneNumber" to phoneNumber
                            ))
                            .addOnCompleteListener { firestoreTask ->
                                trySend(Resource.Success(user))
                            }
                    }
                } else {
                    trySend(Resource.Error(task.exception?.message ?: "Registration failed"))
                }
            }
        awaitClose { }
    }

    override fun googleSignIn(idToken: String): Flow<Resource<User>> = callbackFlow {
        trySend(Resource.Loading())
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser = firebaseAuth.currentUser
                    val user = User(
                        uid = firebaseUser?.uid ?: "",
                        email = firebaseUser?.email
                    )
                    trySend(Resource.Success(user))
                } else {
                    trySend(Resource.Error(task.exception?.message ?: "Google Sign-In failed"))
                }
            }
        awaitClose { }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }

    override fun getCurrentUser(): User? {
        return firebaseAuth.currentUser?.let {
            User(
                uid = it.uid,
                email = it.email,
                displayName = it.displayName,
                phoneNumber = it.phoneNumber // This might be null if not using phone auth, but it's okay
            )
        }
    }

    override fun isUserLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }
}
