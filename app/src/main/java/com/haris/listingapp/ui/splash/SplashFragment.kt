package com.haris.listingapp.ui.splash

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.haris.listingapp.R
import com.haris.listingapp.databinding.SplashFragmentBinding

class SplashFragment : Fragment() {

    private lateinit var binding: SplashFragmentBinding
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.splash_fragment, container, false)
        config()
        return binding.root
    }

    private fun config() {
        binding.logo.animation =
            AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up_down)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.liveSplashState.observe(viewLifecycleOwner, {
            goToNavigation()
        })
    }

    override fun onResume() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
            activity?.window?.setDecorFitsSystemWindows(false)
        else activity?.enterFullScreen()
        super.onResume()
    }

    override fun onPause() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
            activity?.window?.setDecorFitsSystemWindows(true)
        else activity?.exitFullScreen()
        super.onPause()
    }

    private fun FragmentActivity.enterFullScreen() {
        val flags =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_FULLSCREEN or (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

        window?.decorView?.systemUiVisibility = flags
    }

    private fun FragmentActivity.exitFullScreen() {
        window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun goToNavigation() {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
            else -> MaterialAlertDialogBuilder(requireContext())
                .setTitle("Notice")
                .setMessage("Your device is not supported at the moment. Thank you for your understanding.")
                .setPositiveButton("OK") { _, _ ->
                    when {
                        Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> this.activity?.finishAndRemoveTask()
                        else -> this.activity?.finishAffinity()
                    }
                }
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setCancelable(false)
                .show()
        }
    }
}