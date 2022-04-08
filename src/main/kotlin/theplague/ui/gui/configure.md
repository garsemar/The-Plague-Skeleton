## settings.gradle
```kotlin
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
    
}
```

## build.gradle
```kotlin
import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm") version "1.6.10"
    id("org.jetbrains.compose") version "1.1.0"
}



repositories {
google()
mavenCentral()
maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}


dependencies {
implementation(compose.desktop.currentOs)
}


compose.desktop {
application {
mainClass = "MainKt"
nativeDistributions {
targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
packageName = "ThePlague"
packageVersion = "1.0.0"
}
}
}
```
