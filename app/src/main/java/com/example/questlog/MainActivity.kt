package com.example.questlog

import android.annotation.SuppressLint
import com.example.questlog.review.viewmodel.ReviewViewModel
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.questlog.playlist.viewmodel.PlaylistViewModel

enum class FragmentName{
    Games,Playlist,Reviews,Lists
}
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
   // private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var gamesButton: Button;
    private lateinit var playlistButton: Button;
    private lateinit var reviewsButton: Button;



    private var currentFragment : FragmentName = FragmentName.Games;
    private  lateinit var  shaderPlaylistViewModel: PlaylistViewModel
    private  lateinit var  sharedReviewViewModel: ReviewViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }

        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        //appBarConfiguration = AppBarConfiguration(setOf(R.id.playlistFragment, R.id.gamesFragment, R.id.listsFragment))
        //setupActionBarWithNavController(navController, appBarConfiguration)
        gamesButton = findViewById(R.id.games_page_button);
        playlistButton = findViewById(R.id.playlist_page_button);
        reviewsButton = findViewById(R.id.reviews_page_button);




        shaderPlaylistViewModel = ViewModelProvider(this).get(PlaylistViewModel::class.java)
        sharedReviewViewModel = ViewModelProvider(this).get(ReviewViewModel::class.java)
        gamesButton.setOnClickListener{
            GetCurrentFragment()
            navigateToDestination(currentFragment,FragmentName.Games);
            currentFragment = FragmentName.Games;
        }
        playlistButton.setOnClickListener{
            GetCurrentFragment()
            navigateToDestination(currentFragment,FragmentName.Playlist);
            currentFragment = FragmentName.Playlist;
        }
        reviewsButton.setOnClickListener{
            GetCurrentFragment()
            navigateToDestination(currentFragment,FragmentName.Reviews);
            currentFragment = FragmentName.Reviews;
        }


    }
    /*

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
    */
    //@SuppressLint("SuspiciousIndentation")

    private fun GetCurrentFragment(){
        val id = getIdOfCurrentFragment()
           if( navController.currentDestination?.id != id){
               setCurrentFragmentById(navController.currentDestination?.id)
           }
    }
    private  fun getIdOfCurrentFragment(): Int{
        return when(currentFragment){
            FragmentName.Games -> R.id.gamesFragment
            FragmentName.Reviews -> R.id.reviewsFragment
            else -> R.id.playlistFragment

        }

    }
    private  fun setCurrentFragmentById( id :Int?) {
        when(id){
            R.id.gamesFragment -> currentFragment = FragmentName.Games
            R.id.reviewsFragment ->currentFragment = FragmentName.Reviews
            R.id.playlistFragment ->currentFragment = FragmentName.Playlist

        }

    }
    private fun navigateToDestination(from : FragmentName,to : FragmentName ){
        when(from){
            FragmentName.Games-> navigateFromGames(to);
            FragmentName.Playlist->  navigateFromPlaylist(to);
            else ->navigateFromReviews(to);

        }
    }

    private fun navigateFromGames(to : FragmentName){
        when(to){
            FragmentName.Games -> println("Already In Games");
            FragmentName.Playlist-> navController.navigate(R.id.action_gamesFragment_to_playlist2);
            else-> navController.navigate(R.id.action_gamesFragment_to_reviewsFragment2);

        }
    }
    private fun navigateFromPlaylist(to : FragmentName){
        when(to){
            FragmentName.Playlist-> println("Already in Playlist");
            FragmentName.Games ->  navController.navigate(R.id.action_playlist2_to_gamesFragment);
            else->  navController.navigate(R.id.action_playlist2_to_reviewsFragment);

        }
    }

    private fun navigateFromReviews(to : FragmentName){
        when(to){
            FragmentName.Playlist->   navController.navigate(R.id.action_reviewsFragment_to_playlist2);
            FragmentName.Games ->  navController.navigate(R.id.action_reviewsFragment_to_gamesFragment2);
            else->  println("Already In Reviews");

        }
    }



}


