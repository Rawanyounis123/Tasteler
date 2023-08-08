package com.example.recipemobileapp.HomeActivity.home.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import at.blogc.android.views.ExpandableTextView
import com.bumptech.glide.Glide
import com.example.recipemobileapp.Database.Meal
import com.example.recipemobileapp.Database.Wishlist
import com.example.recipemobileapp.Database.localDataSource.LocalDataSourceImpl
import com.example.recipemobileapp.HomeActivity.home.Repo.MealRepoImpl
import com.example.recipemobileapp.Network.APIClient
import com.example.recipemobileapp.R
import com.example.recipemobileapp.ViewModel.MealViewModel
import com.example.recipemobileapp.ViewModel.MealviewModelFactory
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class DetailsFragment : Fragment() {
    private lateinit var viewModel: MealViewModel
    private lateinit var recipeImageView: ImageView
    private lateinit var recipeNameTextView: TextView
    private lateinit var recipeCategoryTextView: TextView
    private lateinit var descriptionExpandableTextView: ExpandableTextView
    private lateinit var descriptionExpandableTextView2: ExpandableTextView

    private lateinit var tutorialyoutubeView: YouTubePlayerView
    private lateinit var seetutorial: Button






    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_details, container, false)



        recipeImageView = view.findViewById(R.id.imageView2)
        recipeNameTextView = view.findViewById(R.id.textView2)
        descriptionExpandableTextView = view.findViewById(R.id.instructionsTextView)
        descriptionExpandableTextView2 = view.findViewById(R.id.instructionsTextView2)
        tutorialyoutubeView = view.findViewById(R.id.youtube_player_view)
        lifecycle.addObserver(tutorialyoutubeView)

        descriptionExpandableTextView.setAnimationDuration(750L)
        descriptionExpandableTextView.setInterpolator(OvershootInterpolator())

        descriptionExpandableTextView.setOnClickListener {
            if (descriptionExpandableTextView.isExpanded) {
                descriptionExpandableTextView.collapse()
            } else {
                descriptionExpandableTextView.expand()
            }
        }
        descriptionExpandableTextView2.setAnimationDuration(750L)
        descriptionExpandableTextView2.setInterpolator(OvershootInterpolator())

        descriptionExpandableTextView2.setOnClickListener {
            if (descriptionExpandableTextView2.isExpanded) {
                descriptionExpandableTextView2.collapse()
            } else {
                descriptionExpandableTextView2.expand()
            }
        }






        return view
    }


    @SuppressLint("NewApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gettingViewModelReady()


        val recipe = arguments?.getParcelable("recipe",Meal::class.java)
         if (recipe != null) {
                val favbtn:Button = view.findViewById(R.id.addtofavs)
                favbtn.setOnClickListener{
                    Toast.makeText(requireContext(),"Added to Favs", Toast.LENGTH_SHORT).show()
                }
             tutorialyoutubeView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener(){
                 override fun onReady(youTubePlayer: YouTubePlayer) {
                     super.onReady(youTubePlayer)
                     val videoId = recipe.strYoutube.substring(recipe.strYoutube.length-11,recipe.strYoutube.length)
                     Log.d("vid",recipe.strYoutube)
                     Log.d("vid",videoId)
                     youTubePlayer.loadVideo(videoId, 0F)
                     youTubePlayer.pause()

                 }
             })

             Glide.with(requireContext())
                 .load(recipe.strMealThumb)
                 .into(recipeImageView)

                 recipeNameTextView.text = recipe.strMeal
                 descriptionExpandableTextView.text = "Instructions : \n ${recipe.strInstructions}"
                 descriptionExpandableTextView2.text = "General Information : \n - Area: ${recipe.strArea} \n -Category : ${recipe.strCategory}\n -Tags : ${recipe.strTags} \n" +
                         " -Source : ${recipe.strSource}"


            } else {
                Toast.makeText(requireContext(), "Recipe not found", Toast.LENGTH_SHORT).show()
            }

    }




    private fun gettingViewModelReady() {
        val mealFactory = MealviewModelFactory(
            MealRepoImpl(
                APIClient,
                LocalDataSourceImpl(requireContext())
            )
        )
        viewModel = ViewModelProvider(this, mealFactory)[MealViewModel::class.java]
    }













}