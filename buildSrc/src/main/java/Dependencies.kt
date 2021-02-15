object Dependencies {

    object Version {
        const val ANDROID_GRADLE_PLUGIN = "4.1.2"
        const val KOTLIN_GRADLE_PLUGIN = "1.4.30"
        const val SUPPORT_LEGACY = "1.0.0"
        const val SUPPORT_LIBRARY = "1.2.0"
        const val CONSTRAINT_LAYOUT = "2.0.4"
        const val MATERIAL = "1.2.1"
        const val JUNIT = "4.+"
        const val ANDROID_TEST_JUNIT = "1.1.2"
        const val ANDROID_TEST_ESPRESSO = "3.3.0"
        const val KTX_CORE = "1.3.2"
        const val KTX_FRAGMENT = "1.2.5"
        const val KTX_ACTIVITY = "1.1.0"
        const val LIFECYCLE = "2.2.0"
        const val HILT = "1.0.0-alpha03"
        const val DAGGER_HILT = "2.31.2-alpha"
        const val COROUTINES = "1.4.2"
        const val PAHO_MQTT_CLIENT = "1.1.0"
        const val PAHO_MQTT_SERVICE = "1.1.1"
        const val TIMBER = "4.7.1"
        const val MP_ANDROID_CHART = "3.1.0"
    }

    object AndroidX {
        const val APPCOMPAT = "androidx.appcompat:appcompat:${Version.SUPPORT_LIBRARY}"
        const val SUPPORT_LEGACY = "androidx.legacy:legacy-support-v4:${Version.SUPPORT_LEGACY}"
        const val CONSTRAINT_LAYOUT: String =
            "androidx.constraintlayout:constraintlayout:${Version.CONSTRAINT_LAYOUT}"
    }

    const val TIMBER = "com.jakewharton.timber:timber:${Version.TIMBER}"

    const val KOTLIN_STANDARD_LIBRARY =
        "org.jetbrains.kotlin:kotlin-stdlib:${Version.KOTLIN_GRADLE_PLUGIN}"

    const val ANDROID_GRADLE_PLUGIN =
        "com.android.tools.build:gradle:${Version.ANDROID_GRADLE_PLUGIN}"

    const val KOTLIN_GRADLE_PLUGIN =
        "org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.KOTLIN_GRADLE_PLUGIN}"

    const val KTX_CORE = "androidx.core:core-ktx:${Version.KTX_CORE}"

    const val KTX_ACTIVITY = "androidx.activity:activity-ktx:${Version.KTX_ACTIVITY}"

    const val KTX_FRAGMENT = "androidx.fragment:fragment-ktx:${Version.KTX_FRAGMENT}"

    const val MP_ANDROID_CHART = "com.github.PhilJay:MPAndroidChart:${Version.MP_ANDROID_CHART}"

    object Test {
        const val JUNIT = "junit:junit:${Version.JUNIT}"
    }

    object AndroidTest {
        const val JUNIT = "androidx.test.ext:junit:${Version.ANDROID_TEST_JUNIT}"
        const val ESPRESSO = "androidx.test.espresso:espresso-core:${Version.ANDROID_TEST_ESPRESSO}"
    }

    object Google {
        const val MATERIAL = "com.google.android.material:material:${Version.MATERIAL}"
    }

    object Lifecycle {
        const val VIEWMODEL = "androidx.lifecycle:lifecycle-viewmodel:${Version.LIFECYCLE}"
        const val VIEWMODEL_KTX = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.LIFECYCLE}"
        const val LIVEDATA_KTX = "androidx.lifecycle:lifecycle-livedata-ktx:${Version.LIFECYCLE}"
        const val COMMON = "androidx.lifecycle:lifecycle-common-java8:${Version.LIFECYCLE}"
        const val EXTENSIONS = "androidx.lifecycle:lifecycle-extensions:${Version.LIFECYCLE}"
    }

    object DaggerHilt {
        const val GRADLE_PLUGIN =
            "com.google.dagger:hilt-android-gradle-plugin:${Version.DAGGER_HILT}"
        const val ANDROID = "com.google.dagger:hilt-android:${Version.DAGGER_HILT}"
        const val COMPILER = "com.google.dagger:hilt-android-compiler:${Version.DAGGER_HILT}"
    }

    object Hilt {
        const val VIEWMODEL = "androidx.hilt:hilt-lifecycle-viewmodel:${Version.HILT}"
        const val COMPILER = "androidx.hilt:hilt-compiler:${Version.HILT}"
    }

    object Coroutines {
        const val CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.COROUTINES}"
        const val ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.COROUTINES}"
    }

    object PahoMqtt {
        const val PAHO_MQTT_CLIENT = "org.eclipse.paho:org.eclipse.paho.client.mqttv3:${Version.PAHO_MQTT_CLIENT}"
        const val PAHO_MQTT_SERVICE = "org.eclipse.paho:org.eclipse.paho.android.service:${Version.PAHO_MQTT_SERVICE}"
    }
}