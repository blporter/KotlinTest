package personal.kotlintest.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import personal.kotlintest.R

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // set welcome text
        val textView = getView()!!.findViewById(R.id.welcomeText) as TextView
        val welcome = "${getString(R.string.welcome)} back, ${getString(R.string.user_name)}!"
        textView.text = welcome
    }

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }
}