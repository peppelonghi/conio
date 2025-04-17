plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
}


val resourcesToManage = listOf(
    Pair("strings.xml", "values"),
    Pair("dimens.xml", "values"),
    Pair("", "drawable"),
)

val copyTasks = resourcesToManage.map { (fileName, directory) ->
    val copyTaskName = "duplicate${directory.capitalize()}${
        if (fileName.isNotEmpty()) fileName.capitalize().removeSuffix(".xml") else ""
    }"
    tasks.register<Copy>(copyTaskName) {
        group = "verification"
        description = "Duplicates $fileName from main to androidTest resources."
        from("src/main/res/$directory${if (fileName.isNotEmpty()) "/$fileName" else ""}")
        into("src/androidTest/res/$directory")
        if (fileName.isNotEmpty()) {
            rename { _ -> fileName }
        }
    }
}

val deleteTasks = resourcesToManage.map { (fileName, directory) ->
    val deleteTaskName = "removeDuplicate${directory.capitalize()}${
        if (fileName.isNotEmpty()) fileName.capitalize().removeSuffix(".xml") else ""
    }"
    tasks.register<Delete>(deleteTaskName) {
        group = "verification"
        description = "Removes duplicated $fileName from androidTest resources."
        if (fileName.isNotEmpty()) {
            delete("src/androidTest/res/$directory/$fileName")
        } else {
            delete(fileTree("src/androidTest/res/$directory"))
        }
    }
}

tasks.register("copyTestResources") {
    group = "verification"
    description = "Copies test resources (strings, drawables, dimens)."
    dependsOn(copyTasks)
}

tasks.register("removeTestResources") {
    group = "verification"
    description = "Removes test resources (strings, drawables, dimens)."
    dependsOn(deleteTasks)
}


android {
    namespace = "com.giuseppe_longhitano.features.coin"
    compileSdk = 35

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

    //android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    //ui
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.koin.compose)
    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.mpandroidchart)
    //DI
    implementation(libs.koin.core)

    //Internal
    implementation(project(":repositories"))
    implementation(project(":domain"))
    implementation(project(":core:ui"))
    implementation(project(":core:arch"))

    //TEST
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.runner)
    testImplementation(libs.androidx.core.testing)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}