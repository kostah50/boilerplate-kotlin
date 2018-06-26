package com.denwehrle.boilerplate.injection.module

import android.arch.lifecycle.ViewModel

import java.lang.annotation.Documented
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import kotlin.reflect.KClass

import dagger.MapKey

/**
 * Used in the View Model Factory
 *
 * @author Miguel Costa
 */
@Documented
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(RetentionPolicy.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)