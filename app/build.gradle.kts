plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    id("module-plugin")
}

android {
    defaultConfig {
        applicationId = "com.toybethsystems.dokto"
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(":base"))
    implementation(project(":PaypalPayment"))
    implementation(project(":StripePayment"))
    implementation(project(":FlutterwavePayment"))
    implementation(project(":PayStack"))
    implementation(project(":twilio"))

    // Google Maps
    implementation("com.google.android.gms:play-services-maps:17.0.1")

    addTestDependencies()

    // mockwebserver
    testImplementation("com.squareup.okhttp3:mockwebserver:4.3.1")
}
