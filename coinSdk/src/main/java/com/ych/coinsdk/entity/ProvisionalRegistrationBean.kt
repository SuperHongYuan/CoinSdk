package com.ych.coinsdk.entity
/**
 * 临时注册
 *
 * */
data class ProvisionalRegistrationBean(
    val AppID: String,
    val IsReg: Boolean,
    val Key: String,
    val NextWaitSec: Int,
    val PortNum: Int
)