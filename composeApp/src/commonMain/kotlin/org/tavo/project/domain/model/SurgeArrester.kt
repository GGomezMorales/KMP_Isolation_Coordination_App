package org.tavo.project.domain.model

data class SurgeArrester(
    val rated: Double,
    val ratedSafety: Double,
    val npm: Double,
    val npr: Double,
    val mcov: Double,
    val tov: Double
)
