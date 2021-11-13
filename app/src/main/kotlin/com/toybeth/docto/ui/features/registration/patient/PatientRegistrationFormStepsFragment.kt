package com.toybeth.docto.ui.features.registration.patient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.fragment.app.viewModels
import com.orhanobut.logger.Logger
import com.toybeth.docto.R
import com.toybeth.docto.base.ui.BaseViewBindingFragment
import com.toybeth.docto.databinding.FragmentRegistrationFormBinding
import com.toybeth.dokto.stepper.StepperLayout
import com.toybeth.dokto.stepper.VerificationError
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalUnitApi
@ExperimentalMaterialApi
@AndroidEntryPoint
class PatientRegistrationFormStepsFragment  :
    BaseViewBindingFragment<PatientRegistrationViewModel, FragmentRegistrationFormBinding>() {

    override val viewModel: PatientRegistrationViewModel by viewModels()

    override val inflater: (inflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean) -> FragmentRegistrationFormBinding
        get() = FragmentRegistrationFormBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        makePageNormal()
        val pageTitles =
            resources.getStringArray(R.array.patient_registration_form_page_titles).toList()
        binding.registrationStepper.setAdapter(PatientRegistrationFormStepsAdapter(pageTitles, this))
        binding.registrationStepper.setListener(object: StepperLayout.StepperListener {
            override fun onCompleted(completeButton: View?) {
                viewModel.registerPatient()
            }

            override fun onError(verificationError: VerificationError?) {

            }

            override fun onStepSelected(newStepPosition: Int) {

            }

            override fun onReturn() {

            }
        })

        viewModel.moveNext.observeOn(viewLifecycleOwner) {
            binding.registrationStepper.proceed()
        }
    }
}