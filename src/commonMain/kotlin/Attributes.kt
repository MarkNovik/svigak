package me.mark.svigak

import kotlin.jvm.JvmInline
import kotlin.properties.PropertyDelegateProvider
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


operator fun <THIS, T : Any> Attributes.invoke(init: T, name: String? = null) = Attribute<THIS, T>(init, this, name)

class Attribute<THIS, T : Any> internal constructor(init: T, private val attrs: Attributes, private val name: String? = null) : ReadWriteProperty<THIS, T>,
    PropertyDelegateProvider<THIS, Attribute<THIS, T>> {
    private var init: T? = init

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: THIS, property: KProperty<*>): T =
        (attrs[property.name] ?: error("Unexpectedly attribute ${name ?: property.name} was null")) as T

    override fun setValue(thisRef: THIS, property: KProperty<*>, value: T) {
        attrs[name ?: property.name] = value
    }

    override fun provideDelegate(thisRef: THIS, property: KProperty<*>): Attribute<THIS, T> {
        val i = init!!
        attrs[name ?: property.name] = i
        init = null
        return this
    }
}