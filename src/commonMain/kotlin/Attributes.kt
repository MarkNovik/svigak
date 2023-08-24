package me.mark.svigak

import kotlin.properties.PropertyDelegateProvider
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class Attributes : Iterable<Map.Entry<String, Any>> {
    private val attrs: MutableMap<String, Any?> = mutableMapOf()

    operator fun <THIS, T : Any> invoke(init: T, name: String? = null): Attribute<THIS, T> =
        Attribute(init, name)

    fun <THIS, T : Any> lazy(value: T, name: String? = null): LazyAttribute<THIS, T> = LazyAttribute(value, name)

    fun <THIS, T : Any> nullable(name: String? = null): Attribute<THIS, T?> =
        Attribute(null, name)

    override fun iterator(): Iterator<Map.Entry<String, Any>> = iterator {
        yieldAll(attrs.asIterable().filterIsInstance<Map.Entry<String, Any>>())
    }

    inner class Attribute<THIS, T>(
        private var init: T?,
        private val name: String? = null
    ) : ReadWriteProperty<THIS, T>,
        PropertyDelegateProvider<THIS, Attribute<THIS, T>> {

        @Suppress("UNCHECKED_CAST")
        override fun getValue(thisRef: THIS, property: KProperty<*>): T = attrs[property.name] as T

        override fun setValue(thisRef: THIS, property: KProperty<*>, value: T) {
            attrs[name ?: property.name] = value
        }

        override fun provideDelegate(thisRef: THIS, property: KProperty<*>): Attribute<THIS, T> {
            init?.let { attrs[name ?: property.name] = it }
            init = null
            return this
        }
    }

    inner class LazyAttribute<THIS, T>(
        private var value: T,
        private val name: String? = null
    ) : ReadWriteProperty<THIS, T> {
        override fun getValue(thisRef: THIS, property: KProperty<*>): T = value

        override fun setValue(thisRef: THIS, property: KProperty<*>, value: T) {
            this.value = value
            attrs[name ?: property.name] = value
        }
    }
}