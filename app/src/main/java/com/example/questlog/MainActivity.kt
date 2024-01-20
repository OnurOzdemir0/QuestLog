package com.example.questlog

import android.annotation.SuppressLint
import com.example.questlog.review.viewmodel.ReviewViewModel
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.questlog.databinding.ActivityMainBinding
import com.example.questlog.playlist.viewmodel.PlaylistViewModel
import com.example.questlog.user.viewmodel.UserViewModel
import com.google.firebase.firestore.auth.User

enum class FragmentName{
    Games,Playlist,Reviews,Profile
}
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController


    private var currentFragment : FragmentName = FragmentName.Games;
    private  lateinit var  sharedPlaylistViewModel: PlaylistViewModel
    private  lateinit var  sharedReviewViewModel: ReviewViewModel
    private  lateinit var  userViewModel : UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }

        val binding: ActivityMainBinding =DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
       //appBarConfiguration = AppBarConfiguration(setOf(R.id.playlistFragment, R.id.gamesFragment, R.id.listsFragment))
        //setupActionBarWithNavController(navController, appBarConfiguration)
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.height  =400
        binding.imageView.layoutParams = layoutParams
        binding.textView3.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        navController.addOnDestinationChangedListener{ _,destination,_ ->
            when(destination.id){
                R.id.gamesFragment -> {
                    layoutParams.height = 0
                    binding.imageView.layoutParams = layoutParams
                    binding.textView3.layoutParams = LinearLayout.LayoutParams(
                       LinearLayout.LayoutParams.MATCH_PARENT,0
                    )
                    binding.verticalLayout.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
                    binding.gamesPageButton.visibility = View.VISIBLE
                    binding.profilePageButton.visibility = View.VISIBLE
                    binding.playlistPageButton.visibility = View.VISIBLE
                    binding.reviewsPageButton.visibility = View.VISIBLE
                }
                R.id.profileFragment2->{
                    layoutParams.height = 0
                    binding.imageView.layoutParams = layoutParams
                    binding.textView3.layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,0
                    )
                    binding.verticalLayout.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
                    binding.gamesPageButton.visibility = View.VISIBLE
                    binding.profilePageButton.visibility = View.VISIBLE
                    binding.playlistPageButton.visibility = View.VISIBLE
                    binding.reviewsPageButton.visibility = View.VISIBLE

                }
                R.id.changePasswordFragment -> {
                    layoutParams.height = 400
                    binding.imageView.layoutParams = layoutParams
                    binding.textView3.layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    binding.verticalLayout.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0)
                    binding.gamesPageButton.visibility = View.INVISIBLE
                    binding.profilePageButton.visibility = View.INVISIBLE
                    binding.playlistPageButton.visibility = View.INVISIBLE
                    binding.reviewsPageButton.visibility = View.INVISIBLE

                }
            }


        }




        sharedPlaylistViewModel = ViewModelProvider(this).get(PlaylistViewModel::class.java)
        sharedReviewViewModel = ViewModelProvider(this).get(ReviewViewModel::class.java)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        binding.gamesPageButton.setOnClickListener{
            updateCurrentFragment()
            navigateToDestination(currentFragment,FragmentName.Games);
            currentFragment = FragmentName.Games;
        }
        binding.playlistPageButton.setOnClickListener{
            updateCurrentFragment()
            navigateToDestination(currentFragment,FragmentName.Playlist);
            currentFragment = FragmentName.Playlist;
        }
        binding.reviewsPageButton.setOnClickListener{
            updateCurrentFragment()
            navigateToDestination(currentFragment,FragmentName.Reviews);
            currentFragment = FragmentName.Reviews;
        }

        binding.profilePageButton.setOnClickListener{
            updateCurrentFragment()
            navigateToDestination(currentFragment,FragmentName.Profile)
            currentFragment = FragmentName.Profile
        }

    }
    /*

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
    */
    //@SuppressLint("SuspiciousIndentation")

    private fun updateCurrentFragment(){
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
            R.id.profileFragment2 ->currentFragment = FragmentName.Profile

        }

    }
    private fun navigateToDestination(from : FragmentName,to : FragmentName ){
        when(from){
            FragmentName.Games-> navigateFromGames(to);
            FragmentName.Playlist->  navigateFromPlaylist(to);
            FragmentName.Profile->navigateFromProfile(to)
            else ->navigateFromReviews(to);
        }

    }

    private fun navigateFromGames(to : FragmentName){
        when(to){
            FragmentName.Games -> println("Already In Games");
            FragmentName.Playlist-> navController.navigate(R.id.action_gamesFragment_to_playlist2);
            FragmentName.Profile -> navController.navigate(R.id.action_gamesFragment_to_profileFragment2)
            else-> navController.navigate(R.id.action_gamesFragment_to_reviewsFragment2);

        }
    }
    private fun navigateFromPlaylist(to : FragmentName){
        when(to){
            FragmentName.Playlist-> println("Already in Playlist");
            FragmentName.Games ->  navController.navigate(R.id.action_playlist2_to_gamesFragment);
            FragmentName.Profile->navController.navigate(R.id.action_playlistFragment_to_profileFragment2)
            else->  navController.navigate(R.id.action_playlist2_to_reviewsFragment);

        }
    }

    private fun navigateFromReviews(to : FragmentName){
        when(to){
            FragmentName.Playlist->   navController.navigate(R.id.action_reviewsFragment_to_playlist2);
            FragmentName.Games ->  navController.navigate(R.id.action_reviewsFragment_to_gamesFragment2);
            FragmentName.Profile-> navController.navigate(R.id.action_reviewsFragment_to_profileFragment2)
            else->  println("Already In Reviews");

        }
    }
    private fun navigateFromProfile(to : FragmentName){
        when(to){
            FragmentName.Reviews-> navController.navigate(R.id.action_profileFragment2_to_reviewsFragment)
            FragmentName.Playlist->   navController.navigate(R.id.action_profileFragment2_to_playlistFragment);
            FragmentName.Games ->  navController.navigate(R.id.action_profileFragment2_to_gamesFragment);
            else->  println("Already In Reviews");

        }
    }



}


