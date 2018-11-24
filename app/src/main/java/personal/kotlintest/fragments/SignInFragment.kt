package personal.kotlintest.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import personal.kotlintest.R
import android.content.Context


class SignInFragment : Fragment() {
    private var callback: OnSignInListener? = null

    interface OnSignInListener {
        fun onSignIn()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_sign_in, container, false)

    companion object {
        fun newInstance(): SignInFragment = SignInFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val signInButton: Button = getView()!!.findViewById(R.id.signInButton) as Button

        val listener = View.OnClickListener {
            callback?.onSignIn()
        }

        signInButton.setOnClickListener(listener)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        try {
            callback = activity as OnSignInListener?
        } catch (e: ClassCastException) {
            throw ClassCastException(activity!!.toString() + " must implement OnSignInListener")
        }
    }
}