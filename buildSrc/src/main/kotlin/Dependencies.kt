object Versions {

    const val ktx = "1.3.0"
    const val appCompat = "1.1.0"
    const val navigation = "2.3.0-rc01"
    const val material = "1.3.0-alpha01"
    const val androidXLegacy = "1.0.0"
    const val threeTen = "1.2.4"
    const val room = "2.2.5"
    const val coroutines = "1.3.7"
    const val constraintLayout = "2.0.0-beta6"
    const val fragmentKtx = "1.3.0-alpha06"
    const val lifecycle = "2.2.0"
    const val preferences = "1.1.1"
    const val appStartup = "1.0.0-alpha01"
    const val timber = "4.7.1"

    const val dagger = "2.28"
    const val daggerHiltAndroid = "2.28-alpha"
    const val daggerHilt = "1.0.0-SNAPSHOT"

    const val firebaseAnalytics = "17.4.3"
    const val firebaseUiAuth = "6.2.0"
    const val firebaseRealTimeDB = "19.3.0"

    const val fbFlipper = "0.46.0"
    const val fbSoLoader = "0.9.0"

    const val coil = "0.11.0"
}

object Libraries {

    const val coreKtx = "androidx.core:core-ktx:${Versions.ktx}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val fragmentNavigationKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    const val safeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val androidXLegacy = "androidx.legacy:legacy-support-v4:${Versions.androidXLegacy}"
    const val threeTen = "com.jakewharton.threetenabp:threetenabp:${Versions.threeTen}"
    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val preferencesKtx = "androidx.preference:preference-ktx:${Versions.preferences}"
    const val appStartup = "androidx.startup:startup-runtime:${Versions.appStartup}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    const val daggerHiltPlugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.daggerHiltAndroid}"
    const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.daggerHiltAndroid}"
    const val hiltCommon = "androidx.hilt:hilt-common:${Versions.daggerHilt}"
    const val hiltViewModel = "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.daggerHilt}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:${Versions.daggerHiltAndroid}"
    const val hiltCompiler = "androidx.hilt:hilt-compiler:${Versions.daggerHilt}"

    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragmentKtx}"
    const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
    const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"

    const val firebaseAnalytics = "com.google.firebase:firebase-analytics:${Versions.firebaseAnalytics}"
    const val firebaseUiAuth = "com.firebaseui:firebase-ui-auth:${Versions.firebaseUiAuth}"
    const val firebaseRealtimeDB = "com.google.firebase:firebase-database-ktx:${Versions.firebaseRealTimeDB}"

    const val fbFlipper = "com.facebook.flipper:flipper:${Versions.fbFlipper}"
    const val soLoader = "com.facebook.soloader:soloader:${Versions.fbSoLoader}"
    const val fbFlipperNoOp = "com.facebook.flipper:flipper-noop:${Versions.fbFlipper}"
    const val coil = "io.coil-kt:coil:${Versions.coil}"
}

object TestVersions {

    const val junit = "4.13"
    const val junitExt = "1.1.1"
    const val espresso = "3.2.0"
    const val navigationTesting = Versions.navigation
    const val threeTen = "1.4.4"
    const val assertK = "0.22"
    const val roomTesting = Versions.room
    const val coroutines = Versions.coroutines
    const val daggerHiltAndroid = Versions.daggerHiltAndroid
    const val mockitoKotlin = "2.2.0"
}

object TestLibraries {

    const val junit = "junit:junit:${TestVersions.junit}"
    const val junitExt = "androidx.test.ext:junit:${TestVersions.junitExt}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${TestVersions.espresso}"
    const val navigationTesting = "androidx.navigation:navigation-testing:${TestVersions.navigationTesting}"
    const val threeTen = "org.threeten:threetenbp:${TestVersions.threeTen}"
    const val assertK = "com.willowtreeapps.assertk:assertk-jvm:${TestVersions.assertK}"
    const val roomTesting = "androidx.room:room-testing:${TestVersions.roomTesting}"
    const val coroutinesTesting = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${TestVersions.coroutines}"
    const val hiltTesting = "com.google.dagger:hilt-android-testing:${TestVersions.daggerHiltAndroid}"
    const val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${TestVersions.mockitoKotlin}"
}