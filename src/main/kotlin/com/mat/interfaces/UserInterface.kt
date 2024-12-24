package com.mat.interfaces

import com.mat.models.User

interface UserInterface {
    fun getAllUsers(): List<User>
    fun getUserById(id: Int): User?
    fun addUser(ticket: User): User
    fun updateUser(ticket: User): Boolean
    fun deleteUser(id: Int): Boolean
}