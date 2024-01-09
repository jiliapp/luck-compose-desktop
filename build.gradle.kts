import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "com.bbbang.luck"
version = "1.0.0"

repositories {
    maven ("https://maven.aliyun.com/repository/public")
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

kotlin {
    jvm {
        jvmToolchain(11)
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation("org.skyscreamer:jsonassert:${property("jsonassert.version")}")
            }
        }
        val jvmTest by getting
    }
}


compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "luck-compose"
            packageVersion = "1.0.0"
            description = "luck compose desktop"
            copyright = "© 2024 Luck. All rights reserved."
            vendor = "1.0.0 vendor"
            licenseFile.set(project.file("LICENSE.txt"))
//            macOS {
//                iconFile.set(project.file("launcher/icon.icns"))
//            }
            windows {
                iconFile.set(project.file("launcher/icon.ico"))
            }
//            linux {
//                iconFile.set(project.file("launcher/icon.png"))
//            }
//            buildTypes.release.proguard {
//                obfuscate.set(false)
//                configurationFiles.from(project.file("proguard-rules.pro"))
//            }
        }
    }
}

