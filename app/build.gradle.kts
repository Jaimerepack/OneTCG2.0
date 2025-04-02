plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // Otros plugins específicos para este módulo si los necesitas
}

android {
    namespace = "com.example.onetcg20"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.onetcg20"
        minSdk = 24
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
    buildFeatures {
        viewBinding = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation("androidx.activity:activity-ktx:1.8.0") // Ajusta la versión si es necesario
    implementation(libs.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation("androidx.test.ext:junit:1.1.5") // Ajusta la versión si es necesario
    androidTestImplementation(libs.espresso.core)
}