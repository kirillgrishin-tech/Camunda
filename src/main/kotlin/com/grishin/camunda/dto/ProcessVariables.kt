package com.grishin.camunda.dto

import com.fasterxml.jackson.annotation.JsonInclude
import lombok.AllArgsConstructor
import org.apache.commons.lang3.builder.MultilineRecursiveToStringStyle
import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle

@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
data class ProcessVariables(var businessKey: String?,
                            var result: Boolean? = null,
                            var products: MutableList<Product?>? = null,
                            var count: Int? = null,
                            var check: Boolean? = null
) {

    override fun toString(): String {
        return ToStringBuilder.reflectionToString(
                this,
                object : MultilineRecursiveToStringStyle() {
                    fun withShortPrefixes(): ToStringStyle {
                        this.isUseShortClassName = true
                        this.isUseIdentityHashCode = false
                        return this
                    }
                }.withShortPrefixes())
    }

}
