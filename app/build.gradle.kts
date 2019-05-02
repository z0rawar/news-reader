plugins{
    id(BuildPlugins.androidApplication)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinKapt)
}

android{
    compileSdkVersion(AndroidSdk.compile)
    defaultConfig {
        applicationId = "com.monzo.androidtest"
        minSdkVersion(AndroidSdk.min)
        targetSdkVersion(AndroidSdk.target)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies{
    implementation(project(":core"))
    implementation(project(":news"))


    implementation(Libraries.glide)
    implementation(Libraries.dagger)
    implementation(Libraries.daggerAndroid)
    implementation(Libraries.kotlinStdLib)
    implementation("com.android.support:design:28.0.0")
    implementation("com.android.support:appcompat-v7:28.0.0")
    implementation("com.jakewharton.rxbinding2:rxbinding:2.0.0")
    implementation("io.reactivex.rxjava2:rxjava:2.1.1")
    implementation("io.reactivex.rxjava2:rxandroid:2.0.1")
    implementation("com.jakewharton.rxrelay2:rxrelay:2.0.0")
    testImplementation("junit:junit:4.12")
    testImplementation("org.mockito:mockito-core:2.8.47")
    kapt(Libraries.glideCompiler)
    kapt(Libraries.daggerCompiler)
    kapt(Libraries.daggerAndroidCompiler)



}
