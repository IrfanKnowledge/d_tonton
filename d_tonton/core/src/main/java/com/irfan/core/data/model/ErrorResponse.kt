package com.irfan.core.data.model

import com.google.gson.annotations.SerializedName

data class ErrorResponse(

    @field:SerializedName("status_code")
    val statusCode: String? = null,

    @field:SerializedName("status_message")
    val message: String? = null,

    @field:SerializedName("success")
    val success: Boolean? = null,
)
