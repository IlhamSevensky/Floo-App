object DefaultConfig {

    const val APPLICATION_ID = "com.app.floo"

    const val TEST_INSTRUMENTATION_RUNNER = "androidx.test.runner.AndroidJUnitRunner"

    object Version {
        const val compileSdk = 30
        const val minSdk = 21
        const val targetSdk = 30
        const val appVersionCode = 1
        const val appVersionName = "1.0"
    }

    object FieldType {
        const val TYPE_STRING = "String"
    }

    object FieldKey {
        const val MQTT_SERVER_URI_KEY = "MQTT_SERVER_URI_KEY"
        const val MQTT_SERVER_URI_TEST_KEY = "MQTT_SERVER_URI_TEST_KEY"
    }

    object FieldValue {
        const val MQTT_SERVER_URI_VALUE = "\"tcp://your-mqtt-server-url:1883\""
        const val MQTT_SERVER_URI_TEST_VALUE = "\"tcp://test.mosquitto.org:1883\""
    }

}