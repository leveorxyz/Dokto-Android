package com.toybeth.docto.ui.features.login

import android.os.Bundle
import android.view.View
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.toybeth.docto.base.ui.BaseFragment
import com.toybeth.docto.base.utils.extensions.setContentView
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalAnimationApi
@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginViewModel>() {

    override val viewModel: LoginViewModel by viewModels()

    override val composeView: ComposeView
        get() = ComposeView(requireContext()).apply {
            setContentView {
                LoginScreen(viewModel)
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.navigateToForgetPasswordFlow.observeOn(viewLifecycleOwner) {
            navigateToForgetPasswordFlow()
        }
        viewModel.navigateToRegistrationFlow.observeOn(viewLifecycleOwner) {
            navigateToRegistrationFlow()
        }
        viewModel.enterToLoginScreen()
    }

    private fun navigateToForgetPasswordFlow() {
        viewModel.exitFromLoginScreen()
        findNavController().navigate(
            LoginFragmentDirections.actionLoginFragmentToForgetPasswordEnterEmailFragment()
        )
    }

    private fun navigateToRegistrationFlow() {
        viewModel.exitFromLoginScreen()
        findNavController().navigate(
            LoginFragmentDirections.actionLoginFragmentToSelectRegistrationUserTypeFragment()
        )
    }
}