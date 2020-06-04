object Versions {

    const val ktx = "1.3.0"
    const val appCompat = "1.1.0"
    const val navigation = "2.3.0-beta01"
    const val material = "1.3.0-alpha01"
    const val androidXLegacy = "1.0.0"
    const val threeTen = "1.2.4"
    const val room = "2.2.5"
    const val coroutines = "1.3.7"
    const val constraintLayout = "2.0.0-beta6"
}

object Libraries {

    const val coreKtx = "androidx.core:core-ktx:${Versions.ktx}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val fragmentNavigationKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val androidXLegacy = "androidx.legacy:legacy-support-v4:${Versions.androidXLegacy}"
    const val threeTen = "com.jakewharton.threetenabp:threetenabp:${Versions.threeTen}"
    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
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
}