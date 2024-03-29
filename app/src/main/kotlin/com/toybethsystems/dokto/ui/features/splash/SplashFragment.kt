package com.toybethsystems.dokto.ui.features.splash

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.toybethsystems.dokto.R
import com.toybethsystems.dokto.base.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<SplashViewModel>() {

    override val viewModel: SplashViewModel by viewModels()

    override val composeView: ComposeView
        get() = ComposeView(requireContext()).apply {
            setContent { splashView() }
        }

    override val showAppBar: Boolean
        get() = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.navigateToLogin.observe(viewLifecycleOwner) {
            if(it) {
                navigateToLogin()
            } else {
                navigateToDashboard()
            }
        }

        viewModel.navigateToOnBoarding.observeOn(viewLifecycleOwner) {
            navigateToOnBoarding()
        }
        viewModel.initialize()
    }

    private fun navigateToLogin() {
        findNavController().navigate(
            SplashFragmentDirections.actionSplashFragmentToLoginFragment()
        )
    }

    private fun navigateToOnBoarding() {
        findNavController().navigate(
            SplashFragmentDirections.actionSplashFragmentToOnBoarding()
        )
    }

    private fun navigateToDashboard() {
        findNavController().navigate(
            SplashFragmentDirections.actionSplashFragmentToMainFragment()
        )
    }


    @Composable
    @Preview
    fun splashView() {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.img_splash_background),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier.width(200.dp),
                    painter = painterResource(id = R.drawable.ic_dokto),
                    contentDescription = null,
                )
            }
        }
    }
}