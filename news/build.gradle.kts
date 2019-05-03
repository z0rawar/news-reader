plugins{
    id(BuildPlugins.androidLibrary)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinAndroidExtensions)
    id(BuildPlugins.kotlinKapt)
}

android{
    compileSdkVersion(AndroidSdk.compile)
    defaultConfig {
        minSdkVersion(AndroidSdk.min)
        targetSdkVersion(AndroidSdk.target)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(project(ProjectModules.core))

    implementation(Libraries.appCompat)
    implementation(Libraries.dagger)
    implementation(Libraries.daggerAndroid)
    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.design)
    implementation(Libraries.appCompat)
    implementation(Libraries.coreKtx)
    testImplementation(Libraries.junit)
    testImplementation(Libraries.mockitoCore)
    kapt(Libraries.daggerCompiler)
    kapt(Libraries.daggerAndroidCompiler)
    api(Libraries.okHttp)
    api(Libraries.retrofit)
    implementation(Libraries.retrofitGson)
    implementation(Libraries.archLifecycle)
    api(Libraries.room)
    kapt(Libraries.roomCompiler)
    api(Libraries.coroutinesCore)
    api(Libraries.coroutinesAndroid)

}
repositories {
    mavenCentral()
}
