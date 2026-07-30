plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.kolappan.aarathana"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.kolappan.aarathana"
        minSdk = 31
        targetSdk = 37
        versionCode = 20000
        versionName = "2.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    androidResources {
        generateLocaleConfig = true
        localeFilters += listOf("en", "ta")
    }
    signingConfigs {
        create("release") {
            enableV1Signing = true
            enableV2Signing = true
            enableV3Signing = true
            enableV4Signing = true
        }
    }


    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

tasks.register("generateSongIndex") {
    val songsDir = file("src/main/assets/songs")
    val outputFile = file("src/main/assets/songs_index.json")

    inputs.dir(songsDir)
    outputs.file(outputFile)

    doLast {
        val songs = songsDir.listFiles { _, name -> name.endsWith(".md") }?.map { file ->
            val content = file.readText()
            val header = content.split("---")[0]
            var title = ""
            var author = ""
            var mainGod = ""
            header.lines().forEach { line ->
                when {
                    line.startsWith("title:") -> title = line.removePrefix("title:").trim()
                    line.startsWith("author:") -> author = line.removePrefix("author:").trim()
                    line.startsWith("mainGod:") -> mainGod = line.removePrefix("mainGod:").trim()
                }
            }
            "{\"title\":\"$title\",\"author\":\"$author\",\"mainGod\":\"$mainGod\",\"fileName\":\"${file.name}\"}"
        } ?: emptyList()

        outputFile.writeText("[${songs.joinToString(",")}]")
    }
}

tasks.named("preBuild") {
    dependsOn("generateSongIndex")
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.compose.material.icons.core)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}