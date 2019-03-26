package personal.kotlintest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import personal.kotlintest.fragments.*
import android.content.Intent


class MainActivity : AppCompatActivity(), SignInFragment.OnSignInListener {
    lateinit var toolbar: ActionBar
    lateinit var title: TextView
    lateinit var profileButton: MenuItem
    lateinit var friendsButton: MenuItem

    // this is a redundant flag right now, but im keeping it for future use
    private var isSignedIn: Boolean = false

    private val signInFragment = SignInFragment.newInstance()
    private val homeFragment = HomeFragment.newInstance()
    private val locationFragment = LocationFragment.newInstance()
    private val musicFragment = MusicFragment.newInstance()

    private val clientID = "7a9ab82b7d6d4275bc4a2c58c91a5e18"
    private val redirectURL = "http://localhost:10.0.2.2/callback"
    private var spotifyAppRemote: SpotifyAppRemote? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_splash)
        openFragment(signInFragment, R.id.splash_container)
    }

    override fun onSignIn() {
        isSignedIn = true

        setContentView(R.layout.navigation_bottom)
        setSupportActionBar(findViewById(R.id.toolbar))
        toolbar = supportActionBar!!
        title = findViewById(R.id.toolbar_title)
        toolbar.setDisplayShowTitleEnabled(false)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.navigationView)
        bottomNavigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        title.text = getText(R.string.home_nav)
        openFragment(homeFragment, R.id.container)
    }

    private fun openFragment(fragment: Fragment, container: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    // Bottom Navigation
    private fun setHomeFragment() {
        title.text = getText(R.string.home_nav)
        profileButton.isChecked = false
        friendsButton.isChecked = false
        openFragment(homeFragment, R.id.container)
    }

    private fun setLocationFragment() {
        title.text = getText(R.string.location_nav)
        profileButton.isChecked = false
        friendsButton.isChecked = false
        openFragment(locationFragment, R.id.container)
    }

    private fun setMusicFragment() {
        title.text = getText(R.string.music_nav)
        profileButton.isChecked = false
        friendsButton.isChecked = false
        openFragment(musicFragment, R.id.container)
    }

    private var onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigationHome -> {
                setHomeFragment()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigationLocation -> {
                setLocationFragment()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigationMusic -> {
                setMusicFragment()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    // Spotify
    override fun onStart() {
        super.onStart()
        val connectionParams = ConnectionParams.Builder(clientID)
                .setRedirectUri(redirectURL)
                .showAuthView(true)
                .build()

        SpotifyAppRemote.connect(this, connectionParams,
                object : Connector.ConnectionListener {
                    override fun onConnected(appRemote: SpotifyAppRemote) {
                        spotifyAppRemote = appRemote
                        connected()
                    }

                    override fun onFailure(throwable: Throwable) {
                        Log.e("MainActivity_Spotify", throwable.message, throwable)
                        // Handle errors here
                    }
                })
    }

    override fun onStop() {
        super.onStop()
        SpotifyAppRemote.disconnect(spotifyAppRemote)
    }

    private fun connected() {
        // Play a playlist
        spotifyAppRemote!!.playerApi.play("spotify:track:0MX6VvRcqrvJXRLzfZUOmk")

        // Subscribe to PlayerState
        spotifyAppRemote!!.playerApi
                .subscribeToPlayerState()
                .setEventCallback { playerState ->
                    val track = playerState.track
                    if (track != null) {
                        Log.d("MainActivity", track.name + " by " + track.artist.name)
                    }
                }
    }

    // Toolbar
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        profileButton = menu.findItem(R.id.toolbar_profile)
        friendsButton = menu.findItem(R.id.toolbar_friends)
        return true
    }

    private fun setProfileActivity() {
        title.text = getText(R.string.profile_nav)
        profileButton.isChecked = true
        friendsButton.isChecked = false
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }

    private fun setFriendsActivity() {
        title.text = getText(R.string.friends_nav)
        profileButton.isChecked = false
        friendsButton.isChecked = true
        val intent = Intent(this, FriendsActivity::class.java)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.toolbar_profile -> {
                setProfileActivity()
                return true
            }
            R.id.toolbar_friends -> {
                setFriendsActivity()
                return true
            }
        }
        return false
    }
}
