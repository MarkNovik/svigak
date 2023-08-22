package me.mark.svigak

import kotlin.properties.PropertyDelegateProvider
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

operator fun <THIS, T : Any> Attributes.invoke(init: T, name: String? = null): Attribute<THIS, T> =
    Attribute(this, init, name)

fun <THIS, T : Any> Attributes.nullable(name: String? = null): Attribute<THIS, T?> =
    Attribute(this, null, name)


class Attribute<THIS, T> internal constructor(
    private val attrs: Attributes,
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