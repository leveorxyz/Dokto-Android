package com.toybeth.docto.ui.features.registration.doctor.form.first

import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.ManageSearch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toybeth.docto.R
import com.toybeth.docto.base.theme.DoktoError
import com.toybeth.docto.base.theme.DoktoSecondary
import com.toybeth.docto.ui.common.components.DoktoButton
import com.toybeth.docto.ui.common.components.DoktoTextFiled
import com.toybeth.docto.ui.features.registration.doctor.form.RadioGroup
import com.toybeth.docto.ui.features.registration.doctor.form.RegistrationViewModel

@ExperimentalMaterialApi
@Composable
fun DoctorRegistrationFirstScreen(
    viewModel: RegistrationViewModel,
    datePicker: () -> Unit,
    showCountrySelectionDialog: () -> Unit
) {

    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val genderOptions = context.resources.getStringArray(R.array.gender).toList()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        viewModel.profileImageUri.state.value = uri
        viewModel.profileImageUri.state.value ?.let {
            if (Build.VERSION.SDK_INT < 28) {
                viewModel.profileImage.state.value = MediaStore.Images.Media.getBitmap(
                    context.contentResolver, it
                )

            } else {
                val source = ImageDecoder.createSource(context.contentResolver, it)
                viewModel.profileImage.state.value = ImageDecoder.decodeBitmap(source)
            }
        }
    }

    Column(
        modifier = Modifier
            .verticalScroll(
                state = scrollState,
                enabled = true
            )
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // ------------------------- PROFILE IMAGE ----------------------- //

            if (viewModel.profileImage.state.value != null) {
                Image(
                    bitmap = viewModel.profileImage.state.value!!.asImageBitmap(),
                    contentDescription = "avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(140.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.Gray, CircleShape)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.ic_user_profile),
                    contentDescription = "avatar",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .size(140.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.White, CircleShape)
                )
            }
            AnimatedVisibility(visible = viewModel.profileImage.error.value != null) {
                Text(
                    text = viewModel.profileImage.error.value!!,
                    color = DoktoError,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 15.dp, top = 3.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            // ---------------------- CHOOSE IMAGE ---------------------- //

            DoktoButton(textResourceId = R.string.choose_photo) {
                launcher.launch("image/*")
            }
        }
        Spacer(modifier = Modifier.height(30.dp))

        // --------------------------- USER ID -------------------------- //
        DoktoTextFiled(
            modifier = Modifier.onFocusChanged {
                if(!it.hasFocus) {
                    viewModel.checkIfUserNameAvailable()
                }
            },
            textFieldValue = viewModel.userId.state.value ?: "",
            hintResourceId = R.string.hint_userid,
            labelResourceId = R.string.label_userid,
            trailingIcon = {
                IconButton(onClick = {
                    viewModel.checkIfUserNameAvailable()
                }) {
                    Icon(
                        imageVector = Icons.Filled.ManageSearch,
                        contentDescription = stringResource(id = R.string.check_availability),
                        tint = DoktoSecondary
                    )
                }
            },
            errorMessage = viewModel.userId.error.value,
            onValueChange = {
                viewModel.userId.state.value = it
                viewModel.userId.error.value = null
            }
        )
        Spacer(modifier = Modifier.height(30.dp))

        // --------------------------- NAME ----------------------- //
        DoktoTextFiled(
            textFieldValue = viewModel.name.state.value ?: "",
            hintResourceId = R.string.hint_name,
            labelResourceId = R.string.label_name,
            errorMessage = viewModel.name.error.value,
            onValueChange = {
                viewModel.name.state.value = it
                viewModel.name.error.value = null
            }
        )
        Spacer(modifier = Modifier.height(30.dp))

        // -------------------- MOBILE NUMBER ------------------ //

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                stringResource(id = R.string.label_mobile_number),
                color = Color.White
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            DoktoTextFiled(
                modifier = Modifier.width(120.dp),
                textFieldValue = viewModel.getSelectedCountryPhoneCode(),
                hintResourceId = R.string.hint_country_code,
                errorMessage = viewModel.country.error.value,
                onClick = { showCountrySelectionDialog.invoke() },
                onValueChange = {
                    viewModel.country.error.value = null
                }
            )

            DoktoTextFiled(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 20.dp),
                textFieldValue = viewModel.mobileNumber.state.value ?: "",
                hintResourceId = R.string.hint_mobile_number,
                errorMessage = viewModel.mobileNumber.error.value,
                onValueChange = {
                    viewModel.mobileNumber.state.value = it
                    viewModel.mobileNumber.error.value = null
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        // -------------------------- EMAIL ------------------------ //

        DoktoTextFiled(textFieldValue = viewModel.email.state.value ?: "",
            hintResourceId = R.string.hint_email,
            labelResourceId = R.string.label_email,
            errorMessage = viewModel.email.error.value,
            onValueChange = {
                viewModel.email.state.value = it
                viewModel.email.error.value = null
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )
        Spacer(modifier = Modifier.height(30.dp))

        // ------------------------ PASSWORD --------------------- //

        DoktoTextFiled(textFieldValue = viewModel.password.state.value ?: "",
            hintResourceId = R.string.hint_password,
            labelResourceId = R.string.label_password,
            visualTransformation = PasswordVisualTransformation(),
            errorMessage = viewModel.password.error.value,
            onValueChange = {
                viewModel.password.state.value = it
                viewModel.password.error.value = null
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            )
        )
        Spacer(modifier = Modifier.height(30.dp))

        // --------------------- CONFIRM PASSWORD ---------------------- //

        DoktoTextFiled(textFieldValue = viewModel.confirmPassword.state.value ?: "",
            hintResourceId = R.string.hint_confirm_password,
            labelResourceId = R.string.label_confirm_password,
            visualTransformation = PasswordVisualTransformation(),
            errorMessage = viewModel.confirmPassword.error.value,
            onValueChange = {
                viewModel.confirmPassword.state.value = it
                viewModel.confirmPassword.error.value = null
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            )
        )
        Spacer(modifier = Modifier.height(30.dp))

        // ---------------------------- GENDER ------------------------- //

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                stringResource(id = R.string.hint_gender),
                color = Color.White,
            )
        }
        RadioGroup(
            radioOptions = genderOptions,
        ) {
            viewModel.gender.state.value = it
        }
        Spacer(modifier = Modifier.height(30.dp))

        // ----------------------------- DATE OF BIRTH --------------------- //

        DoktoTextFiled(
            textFieldValue = viewModel.dateOfBirth.state.value ?: "",
            hintResourceId = R.string.hint_date_of_birth,
            labelResourceId = R.string.label_date_of_birth,
            errorMessage = viewModel.dateOfBirth.error.value,
            onClick = { datePicker.invoke() },
            onValueChange = {
                viewModel.dateOfBirth.state.value = it
                viewModel.dateOfBirth.error.value = null
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.CalendarToday,
                    contentDescription = "Pick Date",
                    tint = Color.Black.copy(alpha = .7f)
                )
            }
        )
        Spacer(modifier = Modifier.height(30.dp))

        // ------------------------ NEXT BUTTON -------------------- //

        DoktoButton(textResourceId = R.string.next) {
            viewModel.moveNext()
        }

        Spacer(modifier = Modifier.height(50.dp))
    }
}