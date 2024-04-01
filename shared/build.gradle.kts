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
        val commonMain by getting {
            dependencies {
                implementation(libs.runtime)
                implementation(libs.kotlinx.datetime)
            }


            //put your multiplatform dependencies here
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.android.driver)
            }
        }

        val iosMain by creating {
            dependencies {
                implementation("app.cash.sqldelight:native-driver:2.0.1")
            }

//        val androidTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting


            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }

    task("testClasses")
}

sqldelight {
    databases {
        create("NotesDatabase") {
            packageName.set("com.ab.notesappkmm.db")
        }
        linkSqlite = true
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
