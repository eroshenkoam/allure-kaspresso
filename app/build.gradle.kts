plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "io.qameta.allure.kaspresso"
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunner = "io.qameta.allure.android.runners.AllureAndroidJUnitRunner"
        testInstrumentationRunnerArguments["filter"] =
            "io.qameta.allure.kaspresso.support.AllureFilter"
//        testInstrumentationRunnerArguments["clearPackageData"] = "true"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = ("1.8")
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.5.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    implementation("androidx.navigation:navigation-fragment-ktx:2.4.1")
    implementation("androidx.navigation:navigation-ui-ktx:2.4.1")

    testImplementation("junit:junit:4.13.2")

    androidTestImplementation("androidx.test.ext:junit:1.1.3")

    androidTestImplementation("io.qameta.allure:allure-kotlin-android:2.3.1")
    androidTestImplementation("io.qameta.allure:allure-test-filter:2.17.3")
    androidTestImplementation("io.github.eroshenkoam:allure-junit4-filter:1.0")
    androidTestImplementation("com.fasterxml.jackson.core:jackson-databind:2.13.1")

    androidTestImplementation("com.kaspersky.android-components:kaspresso:1.4.1")
    androidTestImplementation("com.kaspersky.android-components:kaspresso-allure-support:1.4.1")

}