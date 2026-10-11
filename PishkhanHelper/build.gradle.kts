plugins {
    alias(libs.plugins.android.library)
    `maven-publish`
}

android {
    namespace = "ir.ayantech.pishkhanhelper"
    compileSdk = 36

    defaultConfig {
        minSdk = 21
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
    publishing {
        singleVariant("release")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
    implementation(libs.whygoogle)
    implementation(libs.ayan.networking.library)
    implementation(libs.ayan.versioncontrol)
    implementation(libs.android.http.download.manager)
    implementation(libs.pushnotification)
    api(libs.zxing.core)
    api(libs.zxing.android.embedded) { isTransitive = false }
    coreLibraryDesugaring(libs.desugar.jdk.libs)
    api(libs.androidx.multidex)
    implementation(libs.google.play.review)
    implementation(libs.intuit.sdp)
    implementation(libs.intuit.ssp)
}

publishing {
    publications {
        register<MavenPublication>("release") {
            afterEvaluate {
                from(components["release"])
            }
            groupId = "com.github.ayantech"
            artifactId = "pishkhanhelper"
            version = "1.0.0"
        }
    }
}
