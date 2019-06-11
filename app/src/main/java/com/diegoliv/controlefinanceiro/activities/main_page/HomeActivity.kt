package com.diegoliv.controlefinanceiro.activities.main_page

import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.widget.TextView
import com.diegoliv.controlefinanceiro.R
import com.diegoliv.controlefinanceiro.activities.authentication.MainActivity
import com.diegoliv.controlefinanceiro.infrastructure.firebase.FirebaseDbHelper
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var auth: FirebaseAuth
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener
    private lateinit var dbHelper: FirebaseDbHelper

    private lateinit var userNameText: TextView
    private lateinit var userEmailText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)

        dbHelper = FirebaseDbHelper()
        auth = FirebaseAuth.getInstance()
        setFirebaseAuthListener()


    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            //super.onBackPressed()
            MaterialAlertDialogBuilder(this)
                .setTitle("Deseja sair do aplicativo?")
                .setPositiveButton("Sim", { dialog, which ->
                    this.finishAffinity()
                    System.exit(1)
                })
                .setNegativeButton("Não", { dialog, which ->
                    dialog.cancel()
                })
                .show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        setUserData()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the HomeActivity/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_update_balance -> {
                startActivity(Intent(this@HomeActivity, SaveBalance::class.java))
            }
            R.id.nav_transactions -> {

            }
            R.id.nav_signout -> {
                auth.signOut()
            }
        }

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onStart() {
        super.onStart()
        auth.addAuthStateListener(authStateListener)
    }

    override fun onStop() {
        super.onStop()
        auth.removeAuthStateListener(authStateListener)
    }

    private fun setFirebaseAuthListener() {
        authStateListener = FirebaseAuth.AuthStateListener { auth ->
            var user = auth.currentUser

            if (user == null) {
                var intent = Intent(this@HomeActivity, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK shl Intent.FLAG_ACTIVITY_CLEAR_TASK)

                startActivity(intent)
            }
        }
    }

    private fun setUserData() {
        userNameText = findViewById(R.id.userNameText)
        userEmailText = findViewById(R.id.userEmailText)

        dbHelper.get("users", "authId", auth.currentUser!!.uid)
            .addOnSuccessListener { documents ->
                val documentData = documents.first()
                userNameText.text = "Olá, ${documentData["name"].toString()}"
                userEmailText.text = documentData["email"].toString()
            }
    }
}
