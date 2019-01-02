package com.algolia.search.saas.query

import com.algolia.search.saas.data.Attribute


sealed class Filter(
    open val attribute: Attribute
) {

    var not = false
        private set

    abstract val expression: String

    fun build() = if (not) "NOT $expression" else expression

    fun not(value: Boolean = true): Filter {
        not = value
        return this
    }
}

data class FilterTag(
    val value: String
) : Filter(Attribute("_tags")) {

    override val expression = "$attribute:\"$value\""
}

sealed class FilterNumeric(
    override val attribute: Attribute
) : Filter(attribute)

data class FilterComparison(
    override val attribute: Attribute,
    val operator: NumericOperator,
    val value: Double
) : FilterNumeric(attribute) {

    override val expression = "\"$attribute\" ${operator.raw} $value"
}

data class FilterRange(
    override val attribute: Attribute,
    val lowerBound: Double,
    val upperBound: Double
) : FilterNumeric(attribute) {

    override val expression = "\"$attribute\":$lowerBound TO $upperBound"
}

data class FilterFacet internal constructor(
    override val attribute: Attribute,
    private val value: FacetValue<*>,
    private val score: Int? = null
) : Filter(attribute) {

    constructor(attribute: Attribute, value: String, score: Int? = null) : this(
        attribute,
        FacetValue.String(value),
        score
    )

    constructor(attribute: Attribute, value: Boolean, score: Int? = null) : this(
        attribute,
        FacetValue.Boolean(value),
        score
    )

    constructor(attribute: Attribute, value: Number, score: Int? = null) : this(
        attribute,
        FacetValue.Number(value),
        score
    )

    override val expression: String = "\"$attribute\":${value.escape()}" + if (score != null) "<score=$score>" else ""
}

sealed class FacetValue<T> {

    abstract val value: T

    internal fun escape(): Any {
        return when (this) {
            is String -> "\"$value\""
            is Boolean -> value
            is Number -> value
        }
    }

    data class String(override val value: kotlin.String) : FacetValue<kotlin.String>()

    data class Boolean(override val value: kotlin.Boolean) : FacetValue<kotlin.Boolean>()

    data class Number(override val value: kotlin.Number) : FacetValue<kotlin.Number>()
}

fun String.toFacetValue() = FacetValue.String(this)

fun Boolean.toFacetValue() = FacetValue.Boolean(this)

fun Number.toFacetValue() = FacetValue.Number(this)