import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "ru.khinkal.markdown_editor.convention_plugins.base"

dependencies {
    with(libs) {
        with(gradleplugin) {
            implementation(android)
            implementation(compose)
            implementation(composeCompiler)
            implementation(kotlin)
        }
        // Workaround for version catalog working inside precompiled scripts
        // Issue - https://github.com/gradle/gradle/issues/15383
        implementation(files(this.javaClass.superclass.protectionDomain.codeSource.location))
    }
}

private val projectJavaVersion: JavaVersion = JavaVersion.toVersion(libs.versions.java.get())

java {
    sourceCompatibility = projectJavaVersion
    targetCompatibility = projectJavaVersion
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions.jvmTarget.set(JvmTarget.fromTarget(projectJavaVersion.toString()))
}