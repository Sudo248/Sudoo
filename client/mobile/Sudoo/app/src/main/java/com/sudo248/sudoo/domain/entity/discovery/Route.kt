package com.sudo248.sudoo.domain.entity.discovery

data class Route(
    val weight: Double = 0.0,
    val duration: Value = Value(),
    val distance: Value = Value()
) : java.io.Serializable
