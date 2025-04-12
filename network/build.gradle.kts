plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
  }

android {
    namespace = "com.giuseppe_longhitano.network"
    compileSdk = 35

    buildFeatures {
        buildConfig =  true
    }

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            buildConfigField("String", "BASE_URL", "\"https://api.coingecko.com/\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    //NETWORK
    implementation(libs.retrofit)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.network.converter)
    implementation(libs.network.okhttp3)
    //DI
    implementation(libs.koin.core)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}