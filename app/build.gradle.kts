plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.room)
    alias(libs.plugins.ksp)
    alias(libs.plugins.serialization)
}

android {
    namespace = "com.example.vitesse"
    compileSdk = 35

    room {
        schemaDirectory("$projectDir/schemas")
    }

    defaultConfig {
        applicationId = "com.example.vitesse"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        ksp.apply {
            // Enables exporting database schemas into JSON files in the given directory.
            // arg("room.schemaLocation", "$projectDir/schemas")
            // Enables Gradle incremental annotation processor. Default value is true.
            arg("room.incremental", "true")
            // Generate Kotlin source files instead of Java. Requires KSP. Default value is true as of version 2.7.0.
            arg("room.generateKotlin", "true")
        }

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    //material3 for custom ripple effect
    implementation("androidx.compose.material3:material3:1.3.2")

    // activity dependency for Google ImagePicker
    implementation(libs.androidx.activity.ktx)
    // Coil for image viewing
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)
    implementation(libs.coil.video)

    // Google accompanist for SystemUiController
    implementation(libs.google.accompanist.systemuicontroller)

    // google material for icons
    implementation(libs.material)

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // room
    implementation(libs.androidx.room.runtime)
    // If this project uses any Kotlin source, use Kotlin Symbol Processing (KSP)
    ksp(libs.androidx.room.compiler)
    // If this project only uses Java source, use the Java annotationProcessor
    // No additional plugins are necessary
    // annotationProcessor(libs.androidx.room.compiler)
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation(libs.androidx.room.ktx)

    // Retrofit for currency conversion API
    // Retrofit
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.retrofit)
    implementation(libs.okhttp)
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // coroutine test (runTest)
    testImplementation(libs.kotlinx.coroutines.test)
    // Flow test
    androidTestImplementation(libs.turbine)
    // MockWebServer for API testing
    testImplementation(libs.okhttp3.mockwebserver)
    // Mockito
    testImplementation(libs.mockito.kotlin)
}

/**
 * Helper function to force application run to be performed on physical device.
 */
val targetDevice = "adb-cb4d0d70-D3BuA7._adb-tls-connect._tcp" // ← Replace with your actual device ID
val checkPhysicalDevice = tasks.register("checkPhysicalDevice") {
    doFirst {
        val adbOutput = ProcessBuilder("adb", "devices")
            .redirectErrorStream(true)
            .start()
            .inputStream
            .bufferedReader()
            .readText()

        val connectedDevices = adbOutput.lines().filter { line ->
            line.isNotBlank() &&
                    !line.startsWith("List") &&
                    line.contains("device") &&
                    !line.startsWith("emulator-")
        }

        if (connectedDevices.none { it.startsWith(targetDevice) }) {
            throw GradleException("ERROR: Required physical device ($targetDevice) is not connected.")
        } else {
            println("✅ Physical device ($targetDevice) is connected. Proceeding with build.")
        }
    }
}
//// Attach the check to install tasks
//tasks.configureEach {
//    if (name == "installDebug" || name == "installRelease") {
//        dependsOn(checkPhysicalDevice)
//    }
//}