plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = Versions.Android.sdk

    defaultConfig {
        applicationId = Versions.App.id
        minSdk = Versions.Android.minSdk
        targetSdk = Versions.Android.sdk
        versionCode = Versions.App.versionCode
        versionName = Versions.App.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.schemaLocation" to "$projectDir/schemas",
                    "room.incremental" to "true",
                    "room.expandProjection" to "true"
                )
            }
        }

        buildConfigField("String", "API_ROOT", API_ROOT)
        buildConfigField("String", "IMAGE_API_ROOT", IMAGE_API_ROOT)
        buildConfigField("String", "TMDB_API_TOKEN", TMDB_API_TOKEN)
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.JetpackCompose.jetpackCompose
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(Dependencies.AndroidX.coreKtx)
    implementation(Dependencies.AndroidX.appCompat)
    implementation(Dependencies.AndroidX.lifecycleRuntime)
    implementation(Dependencies.AndroidX.legacy)
    implementation(Dependencies.AndroidX.liveData)
    implementation(Dependencies.AndroidX.viewModel)
    implementation(Dependencies.Logging.timber)

    // API
    implementation(Dependencies.gson)
    implementation(Dependencies.Retrofit.core)
    implementation(Dependencies.Retrofit.gsonConverter)
    implementation(Dependencies.okhttp)
    implementation(Dependencies.okhttpLogging)

    // Ui Component
    implementation(Dependencies.material)
    implementation(Dependencies.JetpackCompose.ui)
    implementation(Dependencies.JetpackCompose.material)
    implementation(Dependencies.JetpackCompose.tooling)
    implementation(Dependencies.JetpackCompose.activity)
    implementation(Dependencies.JetpackCompose.liveData)
    implementation(Dependencies.Coin.coil)
    implementation(Dependencies.Coin.coilCompose)

    // DI
    implementation(Dependencies.Koin.core)
    implementation(Dependencies.Koin.android)

    // Room
    implementation(Dependencies.Room.runtime)
    implementation(Dependencies.Room.ktx)
    kapt(Dependencies.Room.annotation)

    // Unit Test
    testImplementation(Dependencies.Test.junit)
    androidTestImplementation(Dependencies.Test.runner)
    androidTestImplementation(Dependencies.Test.espressoCore)
}