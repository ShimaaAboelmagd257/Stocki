package com.example.stocki.account.profile

import com.example.stocki.data.pojos.account.PortfolioItem
import com.example.stocki.data.pojos.account.UserInfo
import com.example.stocki.data.repository.UserRepository
import javax.inject.Inject

class ProfileUseCase @Inject constructor(private val repository: UserRepository) {

    suspend fun getUserPortfolio(userId: String): List<PortfolioItem>{
        return repository.getUserPortfolio(userId)
    }
    suspend fun updatePortfolio(userId: String , item: PortfolioItem):Boolean {
        return repository.updatePortfolio(userId,item)
    }
    fun signOut() {
        repository.signOut()
    }
    suspend fun getUser(uid: String): UserInfo? {
        return repository.getUser(uid)
    }
    suspend fun updateUser(user : UserInfo):Boolean {
        return repository.updateUser(user)
    }
     suspend fun  updateUserBalance(userId: String , newBalance: Double){
       repository.updateUserBalance(userId,newBalance)
    }
    suspend fun getUserBalance(userId: String): Double{
        return   repository.getUserBalance(userId)
    }

}