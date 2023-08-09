package com.example.recipemobileapp.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipemobileapp.Database.Meal
import com.example.recipemobileapp.Database.User
import com.example.recipemobileapp.Database.Userwithmeals
import com.example.recipemobileapp.Database.Wishlist
import com.example.recipemobileapp.HomeActivity.home.Repo.MealRepo
import com.example.recipemobileapp.HomeActivity.home.Repo.SearchRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MealViewModel(val mealRepo: MealRepo):ViewModel() {
    private val _mealList = MutableLiveData<List<Meal>>()
    val mealList: LiveData<List<Meal>> = _mealList

    private val _randomMealList = MutableLiveData<List<Meal>>()
    val randomMealList: LiveData<List<Meal>> = _randomMealList

    private val _loggedUser = MutableLiveData<User>()
    val loggedUser:LiveData<User> = _loggedUser

    private val _savedMeal = MutableLiveData<Meal>()
    val savedMeal:LiveData<Meal> = _savedMeal

    private val _userWithMeal = MutableLiveData<List<Userwithmeals>>()
    val userwithmeals:LiveData<List<Userwithmeals>> = _userWithMeal

    fun getMealsList(randomChar: Char){
        viewModelScope.launch {
            try {
                val response = mealRepo.getAllMealsFromAPI(randomChar)
                _mealList.value =response.meals
            } catch (e: Exception) {
                Log.d("Connection", "getMealsList: No connection")
            }
        }
    }
    fun getRandomMeal(){
        viewModelScope.launch {
            try {
                val response = mealRepo.getRandomMealFromAPI()
                _randomMealList.value = response.meals
            } catch (e: Exception) {
                Log.d("Connion", "getMealsList: No connection in random")
            }
        }        }

    fun insertFav(wishlist: Wishlist){
        viewModelScope.launch(Dispatchers.IO) {
            mealRepo.insertIntofavs(wishlist)
            Log.d("TAG", "insertFav: inserted $wishlist")
        }
    }

    fun insertMeal(meal: Meal){
        viewModelScope.launch(Dispatchers.IO) {
            mealRepo.insertMeal(meal)
        }
    }
    fun getUserId(userEmail:String){
        viewModelScope.launch {
            val userResponse = mealRepo.getUserIdByEmail(userEmail)
            _loggedUser.value = userResponse
        }
    }
//    private val _isFav = MutableLiveData<Boolean>()
//    val isFav:LiveData<Boolean> = _isFav
     suspend fun isFavourite(userid: Int, idMeal: String):Boolean {
            return mealRepo.isFavourite(userid,idMeal)
     }
    fun deleteMeal(meal:Meal){
        viewModelScope.launch(Dispatchers.IO) {
            mealRepo.deleteMeal(meal)
        }
    }
    fun deleteWishlist(wishlist: Wishlist){
        viewModelScope.launch(Dispatchers.IO) {
            mealRepo.deleteWishlist(wishlist)
        }
    }

}