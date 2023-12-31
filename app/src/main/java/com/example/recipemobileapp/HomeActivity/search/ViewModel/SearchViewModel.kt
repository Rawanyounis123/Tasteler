package com.example.recipemobileapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipemobileapp.Database.Meal
import com.example.recipemobileapp.Database.User
import com.example.recipemobileapp.Database.Wishlist
import com.example.recipemobileapp.HomeActivity.home.Repo.SearchRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(val searchRepo: SearchRepo):ViewModel() {
    private val _searchMealList = MutableLiveData<List<Meal>>()
    val searchMealList: LiveData<List<Meal>> = _searchMealList

    fun getSearchResult(search :String ){
        viewModelScope.launch {
            val response = searchRepo.getSearchResultFromAPI(search)
                _searchMealList.value = response.meals
        }
    }

    fun insertMeal(meal: Meal){
        viewModelScope.launch(Dispatchers.IO) {
            searchRepo.insertMeal(meal)
        }
    }

    fun insertFav(wishlist: Wishlist){
        viewModelScope.launch(Dispatchers.IO) {
            searchRepo.insertIntofavs(wishlist)
        }
    }
    suspend fun isFavourite(userid: Int, idMeal: String):Boolean {
        return searchRepo.isFavourite(userid,idMeal)
    }
    fun deleteMeal(meal:Meal){
        viewModelScope.launch(Dispatchers.IO) {
            searchRepo.deleteMeal(meal)
        }
    }

    fun deleteWishlist(wishlist: Wishlist){
        viewModelScope.launch(Dispatchers.IO) {
            searchRepo.deleteWishlist(wishlist)
        }
    }
}