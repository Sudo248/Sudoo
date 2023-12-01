plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.sudoo.shipment"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.sudoo.shipment"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        //buildConfigField("String", "BASE_URL", "\"https://sudo.eastasia.cloudapp.azure.com/api/v1/\"")
        buildConfigField("String", "BASE_URL", "\"http://192.168.1.33:8080/api/v1/\"")
        buildConfigField("String", "USER_NAME", "\"admin\"")
        buildConfigField("String", "PASSWORD", "\"admin\"")
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
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding=true
        buildConfig=true
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("io.github.sudo248:base-android:1.0.0-alpha03")

    implementation ("com.google.android.gms:play-services-vision:20.1.3")
//    implementation ("info.androidhive:barcode-reader:1.1.5")

    // coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    // dagger hilt
    implementation("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-compiler:2.44")

    // retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    // JSON Parsing
    implementation("com.google.code.gson:gson:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // navigation fragment
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.3")

    // glide
    implementation("com.github.bumptech.glide:glide:4.15.0")
}

kapt {
    correctErrorTypes=true
}