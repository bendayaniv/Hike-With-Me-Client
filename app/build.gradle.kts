plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.hike_with_me_client"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.hike_with_me_client"
        minSdk = 26
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

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:32.8.1"))

    // TODO: Add the dependencies for Firebase products you want to use
    // When using the BoM, don't specify versions in Firebase dependencies
    // https://firebase.google.com/docs/android/setup#available-libraries

//    implementation 'com.google.firebase:firebase-analytics'
//
//    //RealtimeDatabase
//    implementation 'com.google.firebase:firebase-database'
//
    //Firebase Authentication
    implementation("com.google.firebase:firebase-auth:22.3.1")
//    implementation 'com.google.firebase:firebase-auth'
//    implementation 'com.firebaseui:firebase-ui-auth:8.0.2'
//
//    //Firebase Storage
//    implementation 'com.google.firebase:firebase-storage:20.1.0'

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")

    // Fragment
    val fragment_version = "1.6.1"
    implementation("androidx.fragment:fragment:$fragment_version")
}