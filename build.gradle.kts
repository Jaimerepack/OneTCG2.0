// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id("org.jetbrains.dokka") version "1.9.20"
}

// Optional: Add buildscript block if needed for additional configuration
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        // Add any additional build dependencies if required
    }
}

// Optional: Configure project-wide settings
allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

// Optional: Add task for cleaning the build
tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}