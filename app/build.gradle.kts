plugins {
    id(Plugins.ANDROID_APPLICATION)
    id(Plugins.KOTLIN_ANDROID)
    id(Plugins.KOTLIN_KAPT)
    id(Plugins.DAGGER_HILT_ANDROID)
}

android {

    compileSdkVersion(DefaultConfig.Version.compileSdk)

    defaultConfig {
        applicationId = DefaultConfig.APPLICATION_ID
        minSdkVersion(DefaultConfig.Version.minSdk)
        targetSdkVersion(DefaultConfig.Version.targetSdk)
        versionCode = DefaultConfig.Version.appVersionCode
        versionName = DefaultConfig.Version.appVersionName
        testInstrumentationRunner = DefaultConfig.TEST_INSTRUMENTATION_RUNNER

        buildConfigField(
            DefaultConfig.FieldType.TYPE_STRING,
            DefaultConfig.FieldKey.MQTT_SERVER_URI_KEY,
            DefaultConfig.FieldValue.MQTT_SERVER_URI_VALUE
        )

        buildConfigField(
            DefaultConfig.FieldType.TYPE_STRING,
            DefaultConfig.FieldKey.MQTT_SERVER_URI_TEST_KEY,
            DefaultConfig.FieldValue.MQTT_SERVER_URI_TEST_VALUE
        )

    }

    buildTypes {
        getByName("release") {
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
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(Dependencies.KOTLIN_STANDARD_LIBRARY)
    implementation(Dependencies.AndroidX.APPCOMPAT)
    implementation(Dependencies.Google.MATERIAL)
    implementation(Dependencies.AndroidX.CONSTRAINT_LAYOUT)
    implementation(Dependencies.AndroidX.SUPPORT_LEGACY)

    implementation(Dependencies.KTX_CORE)
    implementation(Dependencies.KTX_ACTIVITY)
    implementation(Dependencies.KTX_FRAGMENT)

    // MP ANDROID CHART
    implementation(Dependencies.MP_ANDROID_CHART)

    // Timber
    implementation(Dependencies.TIMBER)

    // Android Test
    testImplementation(Dependencies.Test.JUNIT)
    androidTestImplementation(Dependencies.AndroidTest.JUNIT)
    androidTestImplementation(Dependencies.AndroidTest.ESPRESSO)

    // Lifecycle
    implementation(Dependencies.Lifecycle.VIEWMODEL)
    implementation(Dependencies.Lifecycle.VIEWMODEL_KTX)
    implementation(Dependencies.Lifecycle.COMMON)
    implementation(Dependencies.Lifecycle.EXTENSIONS)
    implementation(Dependencies.Lifecycle.LIVEDATA_KTX)

    // Coroutines
    implementation(Dependencies.Coroutines.CORE)
    implementation(Dependencies.Coroutines.ANDROID)

    // Hilt
    implementation(Dependencies.DaggerHilt.ANDROID)
    kapt(Dependencies.DaggerHilt.COMPILER)
    implementation(Dependencies.Hilt.VIEWMODEL)
    kapt(Dependencies.Hilt.COMPILER)

    // PAHO MQTT
    implementation(Dependencies.PahoMqtt.PAHO_MQTT_CLIENT)
    implementation(Dependencies.PahoMqtt.PAHO_MQTT_SERVICE)
}