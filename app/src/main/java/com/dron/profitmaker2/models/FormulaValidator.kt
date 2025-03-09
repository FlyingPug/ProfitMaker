package com.dron.profitmaker2.models

object FormulaValidator {
    fun validate(formula: String): ValidationResult {
        return ValidationResult.Valid;
    }
}

sealed class ValidationResult {
    object Valid : ValidationResult()
    data class Error(val message: String) : ValidationResult()

    val isValid: Boolean
        get() = this is Valid
}