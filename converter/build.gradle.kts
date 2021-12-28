import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    application
}

group = "de.jensklingenberg"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }

}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
    implementation("org.jsoup:jsoup:1.14.3")
    implementation("com.helger:ph-css:6.4.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}