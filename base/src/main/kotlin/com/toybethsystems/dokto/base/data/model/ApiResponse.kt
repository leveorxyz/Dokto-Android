package com.toybethsystems.dokto.base.data.model

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @SerializedName("result")
    val result: T
) : BaseResponse()
