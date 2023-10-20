package com.example.foodonway

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodonway.adapters.CategoriesAdapter
import com.example.foodonway.adapters.MostPopularAdapter
import com.example.foodonway.databinding.FragmentHomeBinding
import com.example.foodonway.project.MealsByCategory
import com.example.foodonway.project.Meal
import com.example.foodonway.videoModel.HomeViewModel


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeMvvm: HomeViewModel
    private lateinit var popularItemsAdapter: MostPopularAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm = ViewModelProvider(this).get(HomeViewModel::class.java)

        popularItemsAdapter= MostPopularAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopularItemsRecyclerView()

        homeMvvm.getRandonMeal()
        observerRandomMeal()

        homeMvvm.getPopularItems()
        observePopularItemsLiveData()

        prepareCategoriesRecyclerView()
        homeMvvm.getCategories()
        observeCategoriesLiveData()
//        onPopularItemClick()
    }

    private fun prepareCategoriesRecyclerView() {
        categoriesAdapter = CategoriesAdapter()
        binding.recyclerView.apply {
            layoutManager= GridLayoutManager(context, 3,GridLayoutManager.VERTICAL,false)
            adapter = categoriesAdapter
        }
    }

    private fun observeCategoriesLiveData() {
        homeMvvm.observeCategoriesLiveData().observe(viewLifecycleOwner, Observer{categories->
        categories.forEach { category ->
            categoriesAdapter.setCategoryList(categories)
        }
        })
    }

//    private fun onPopularItemClick() {
//        popularItemsAdapter.onItemClick = { meal->
//            val intent = Intent(activity,Meal::class.java)
//            startActivity(intent)
//        }
//    }

    private fun preparePopularItemsRecyclerView() {
        binding.recView.apply{
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter= popularItemsAdapter
        }
    }

    private fun observePopularItemsLiveData() {
        homeMvvm.observePopularItemsLiveData().observe(viewLifecycleOwner,
            { mealList->
            popularItemsAdapter.setMeals(mealsList = mealList as ArrayList<MealsByCategory>)

        })
    }

    private fun observerRandomMeal(){
        homeMvvm.observeRandomMealLivedata().observe(viewLifecycleOwner,object: Observer<Meal>{
            override fun onChanged(t: Meal) {
                Glide.with(this@HomeFragment)
                    .load(t!!.strMealThumb)
                    .into(binding.randomMealImg)
            }
        })
    }
}



