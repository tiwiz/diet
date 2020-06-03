object Versions {

    const val ktx = "1.3.0"
    const val appCompat = "1.1.0"
    const val navigation = "2.3.0-beta01"
    const val material = "1.3.0-alpha01"
    const val androidXLegacy = "1.0.0"
    const val threeTen = "1.2.4"
}

object Libraries {

    const val coreKtx = "androidx.core:core-ktx:${Versions.ktx}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val fragmentNavigationKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val androidXLegacy = "androidx.legacy:legacy-support-v4:${Versions.androidXLegacy}"
    const val threeTen = "com.jakewharton.threetenabp:threetenabp:${Versions.threeTen}"
}

object TestVersions {

    const val junit = "4.13"
    const val junitExt = "1.1.1"
    const val espresso = "3.2.0"
    const val navigationTesting = Versions.navigation
    const val threeTen = "1.4.4"
    const val assertK = "0.22"
}

object TestLibraries {

    const val junit = "junit:junit:${TestVersions.junit}"
    const val junitExt = "androidx.test.ext:junit:${TestVersions.junitExt}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${TestVersions.espresso}"
    const val navigationTesting = "androidx.navigation:navigation-testing:${TestVersions.navigationTesting}"
    const val threeTen = "org.threeten:threetenbp:${TestVersions.threeTen}"
    const val assertK = "com.willowtreeapps.assertk:assertk-jvm:${TestVersions.assertK}"
}