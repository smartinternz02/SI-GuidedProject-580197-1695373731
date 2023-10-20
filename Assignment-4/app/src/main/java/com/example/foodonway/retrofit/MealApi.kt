package com.example.foodonway.retrofit
import com.example.foodonway.project.CategoryList
import com.example.foodonway.project.MealsByCategoryList
import com.example.foodonway.project.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun getRandomMeal():Call<MealList>

    @GET("filter.php")
    fun getPopularItems(@Query("a") categoryName:String) : Call<MealsByCategoryList>

    @GET("categories.php")
    fun getCategories() : Call<CategoryList>
}