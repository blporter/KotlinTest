package personal.kotlintest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button

class ProfileActivity : AppCompatActivity() {
    private lateinit var cancelButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_profile)

        cancelButton = findViewById(R.id.cancelButton)
        cancelButton.setOnClickListener { onCancel() }
    }

    private fun onCancel() {
        finish()
    }
}