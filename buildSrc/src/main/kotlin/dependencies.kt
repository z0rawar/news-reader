const val kotlinVersion = "1.3.31"

object BuildPlugins {
    object Versions {
        const val androidBuildToolsVersion = "3.5.0-alpha06"
    }

    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.androidBuildToolsVersion}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"

    const val androidApplication = "com.android.application"
    const val androidLibrary = "com.android.library"
    const val kotlinAndroid = "kotlin-android"
    const val kotlinAndroidExtensions = "kotlin-android-extensions"
    const val kotlinKapt = "kotlin-kapt"
}

object AndroidSdk {
    const val min = 21
    const val compile = 28
    const val target = compile
}

object ProjectModules {
    const val core = ":core"
    const val news = ":news"
}

object Libraries {
    private object Versions {
        const val dagger = "2.21"
        const val glide = "4.9.0"
        const val gson = "2.8.1"
        const val okHttp = "3.13.1"
        const val retrofit = "2.5.0"
        const val archLifecycle = "1.1.1"
        const val appCompat = "1.0.0"
        const val coreKtx = "1.0.1"
        const val design = "28.0.0"
        const val junit = "4.12"
        const val mockitoCore = "2.8.47"
        const val retrofitGson = "2.5.0"
        const val room = "1.1.1"
        const val roomCompiler = "1.1.1"
        const val coroutinesCore = "1.1.1"
        const val coroutinesAndroid = "1.1.1"
        const val recyclerView = "1.0.0"
    }

    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion"
    const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    const val daggerAndroid = "com.google.dagger:dagger-android-support:${Versions.dagger}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    const val daggerAndroidCompiler = "com.google.dagger:dagger-android-processor:${Versions.dagger}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val archLifecycle = "android.arch.lifecycle:extensions:${Versions.archLifecycle}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val design = "com.android.support:design:${Versions.design}"
    const val junit = "junit:junit:${Versions.junit}"
    const val mockitoCore = "org.mockito:mockito-core:${Versions.mockitoCore}"
    const val retrofitGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofitGson}"
    const val room = "android.arch.persistence.room:runtime:${Versions.room}"
    const val roomCompiler = "android.arch.persistence.room:compiler:${Versions.roomCompiler}"
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesCore}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesAndroid}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"

}
