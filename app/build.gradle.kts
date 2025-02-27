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
    implementation(project(ProjectModules.core))
    implementation(project(ProjectModules.news))

    implementation(Libraries.dagger)
    implementation(Libraries.daggerAndroid)
    implementation(Libraries.kotlinStdLib)
    implementation(Libraries.glide)
    implementation(Libraries.design)
    implementation(Libraries.appCompat)
    implementation(Libraries.retrofitGson)
    testImplementation(Libraries.junit)
    testImplementation(Libraries.mockitoCore)
    kapt(Libraries.glideCompiler)
    kapt(Libraries.daggerCompiler)
    kapt(Libraries.daggerAndroidCompiler)

}
