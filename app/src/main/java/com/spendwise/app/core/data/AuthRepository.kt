package com.spendwise.app.core.data

import kotlinx.coroutines.flow.Flow

data class AuthenticatedUser(
    val id: String,
    val email: String?,
    val displayName: String?,
)

sealed interface AuthState {
    data object Loading : AuthState
    data object SignedOut : AuthState
    data class SignedIn(val user: AuthenticatedUser) : AuthState
}

/** Contract for Firebase Authentication. No Firebase implementation is connected yet. */
interface AuthRepository {
    val authState: Flow<AuthState>
    suspend fun signIn(email: String, password: String): Result<Unit>
    suspend fun register(email: String, password: String): Result<Unit>
    suspend fun signOut()
}

