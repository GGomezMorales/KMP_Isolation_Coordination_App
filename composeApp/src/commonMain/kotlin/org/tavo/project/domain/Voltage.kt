package org.tavo.project.domain

data class Voltage(
    val name: String,
    val description: String = "",
    val value: Double,
    val unit: String = "kV"
)
