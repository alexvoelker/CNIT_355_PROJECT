plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.aeondynamics.cnit_355_project"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.aeondynamics.cnit_355_project"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    // https://github.com/Applandeo/Material-Calendar-View?tab=readme-ov-file
    implementation("com.applandeo:material-calendar-view:1.9.2")

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // External Calendar repo
    // https://github.com/kizitonwose/Calendar
    // The view calendar library for Android
//    implementation("com.kizitonwose.calendar:view:<latest-version>")
    // The compose calendar library for Android
//    implementation("com.kizitonwose.calendar:compose:<latest-version>")


// Third party Calendar Library
    // https://github.com/roomorama/Caldroid
//    implementation(libs.caldroid)
    // Third Party Graph Library
//    implementation(libs.mpandroidchart)
}