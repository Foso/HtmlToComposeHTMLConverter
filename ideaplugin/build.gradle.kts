import org.jetbrains.compose.compose

plugins {
    id("org.jetbrains.intellij") version "1.3.0"
    java
    kotlin("jvm")
    id("org.jetbrains.compose")
    id("idea")
}

group = "de.jensklingenberg"
version = "1.0.1"

repositories {
    mavenCentral()
    google()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(project(":converter", "default"))
    testImplementation("junit", "junit", "4.12")
}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    version.set("2021.2")
}

tasks{
    withType<org.jetbrains.intellij.tasks.PublishPluginTask> {
        //token.set("XXX")
    }

    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }

}

