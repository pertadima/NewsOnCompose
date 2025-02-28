package com.codingle.convention

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

object Extensions {
    fun Project.libs(alias: String): Any? {
        val catalogs = extensions.getByType<VersionCatalogsExtension>()
        val libs = catalogs.named("libs")
        return libs.findLibrary(alias).orElse(null)?.get()
    }
}