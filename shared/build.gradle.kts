plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    id("app.cash.sqldelight") version "2.0.1"

}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
                implementation("app.cash.sqldelight:runtime:1.5.3")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")

            //put your multiplatform dependencies here
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        androidMain.dependencies {
            implementation("app.cash.sqldelight:android-driver:2.0.1")
        }

        iosMain.dependencies {
            implementation("app.cash.sqldelight:native-driver:2.0.1")
        }
    }
}

sqldelight {
    databases {
        create("NoteDatabase") {
            packageName.set("com.ab.notesappkmm.database")
            srcDirs.setFrom("src/main/sqldelight")
        }
    }
}

android {
    namespace = "com.ab.notesappkmm"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
