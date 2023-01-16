## Jetpack

[https://developer.android.google.cn/jetpack](https://developer.android.google.cn/jetpack)

> Jetpack 是一套组件库，可帮助开发人员遵循最佳实践，减少样板代码并编写可在 Android 版本和设备上一致工作的代码，以便开发人员可以专注于他们关心的代码。(摘于https://developer.android.google.cn/jetpack)

### AndroidX  
androidx 命名空间包含 Android Jetpack 库。与支持库（android.support.v4、v7等）一样，androidx 命名空间中的库与 Android 平台分开提供，并向后兼容各个 Android 版本。

AndroidX 对原始 Android 支持库进行了重大改进，后者现在已不再维护。androidx 软件包完全取代了支持库，不仅提供与支持库同等的功能，而且还提供了新的库。

### Jetpack分类
[https://developer.android.google.cn/jetpack/androidx/explorer](https://developer.android.google.cn/jetpack/androidx/explorer?case=all)

### Compose

```
android {
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
}
```

```
dependencies {

    val composeBom = platform("androidx.compose:compose-bom:2022.12.00")
    implementation composeBom
    androidTestImplementation composeBom

    // Choose one of the following:
    // Material Design 3
    implementation("androidx.compose.material3:material3")
    // or Material Design 2
    implementation("androidx.compose.material:material")
    // or skip Material Design and build directly on top of foundational components
    implementation("androidx.compose.foundation:foundation")
    // or only import the main APIs for the underlying toolkit systems,
    // such as input and measurement/layout
    implementation("androidx.compose.ui:ui")

    // Android Studio Preview support
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")

    // UI Tests
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Optional - Included automatically by material, only add when you need
    // the icons but not the material library (e.g. when using Material3 or a
    // custom design system based on Foundation)
    implementation("androidx.compose.material:material-icons-core")
    // Optional - Add full set of material icons
    implementation("androidx.compose.material:material-icons-extended")
    // Optional - Add window size utils
    implementation("androidx.compose.material3:material3-window-size-class")

    // Optional - Integration with activities
    implementation("androidx.activity:activity-compose:1.5.1")
    // Optional - Integration with ViewModels
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")
    // Optional - Integration with LiveData
    implementation("androidx.compose.runtime:runtime-livedata")
    // Optional - Integration with RxJava
    implementation("androidx.compose.runtime:runtime-rxjava2")

}
```
