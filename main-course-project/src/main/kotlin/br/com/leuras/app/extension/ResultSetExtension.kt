package br.com.leuras.app.extension

import com.fasterxml.jackson.databind.ObjectMapper

inline fun <reified T> ObjectMapper.readJSONList(value: String?): List<T> {
    return value?.let {
        val type = this.typeFactory.constructCollectionType(List::class.java, T::class.java)
        this.readValue(value, type) as List<T>? ?: emptyList()

    } ?: emptyList()
}