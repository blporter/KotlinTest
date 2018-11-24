package personal.kotlintest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button

class FriendsActivity : AppCompatActivity() {
    private lateinit var cancelButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_friends)

        cancelButton = findViewById(R.id.cancelButton)
        cancelButton.setOnClickListener { onCancel() }
    }

    private fun onCancel() {
        finish()
    }
}