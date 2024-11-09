import org.jetbrains.compose.ExperimentalComposeLibrary

plugins {
    with(libs) {
        with(plugins) {
            alias(multiplatform)
            alias(compose.compiler)
            alias(compose)
            alias(android.application)
            alias(buildConfig)
            alias(kotlinx.serialization)

            with(convention) {
                alias(android.base)
            }
        }
    }
}

kotlin {
    val iosPlatforms = listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    )
    iosPlatforms.forEach {
        it.binaries.framework {
            baseName = "composeApp"
            isStatic = true
        }
    }

    sourceSets {

        commonMain.dependencies {
            with(compose) {
                implementation(runtime)
                implementation(foundation)
                implementation(material3)

                with(components) {
                    implementation(resources)
                    implementation(uiToolingPreview)
                }
            }
            with(libs) {
                with(kotlinx) {
                    implementation(serialization.json)
                    implementation(datetime)
                    implementation(coroutines.core)
                }

                implementation(coil)
                implementation(coil.network.ktor)
                implementation(ktor.core)
                implementation(composeIcons.featherIcons)
                implementation(multiplatformSettings)
            }
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.uiTest)
            implementation(libs.kotlinx.coroutines.test)
        }

        androidMain.dependencies {
            with(libs) {
                implementation(androidx.activityCompose)
                implementation(kotlinx.coroutines.android)
                implementation(ktor.client.okhttp)
            }
            implementation(compose.uiTooling)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

android {
    namespace = "ru.khinkal.markdown_editer"

    defaultConfig {
        targetSdk = libs.versions.android.sdk.target.get().toInt()

        applicationId = "ru.khinkal.markdown_editer"
        versionCode = 1
        versionName = "1.0.0"
    }
}

//https://developer.android.com/develop/ui/compose/testing#setup
dependencies {
    androidTestImplementation(libs.androidx.uitest.junit4)
    debugImplementation(libs.androidx.uitest.testManifest)
    //temporary fix: https://youtrack.jetbrains.com/issue/CMP-5864
    androidTestImplementation("androidx.test:monitor") {
        version { strictly("1.6.1") }
    }
}

buildConfig {
    // BuildConfig configuration here.
    // https://github.com/gmazzo/gradle-buildconfig-plugin#usage-in-kts
}
