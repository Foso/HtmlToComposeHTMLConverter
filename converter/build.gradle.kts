import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
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
    implementation("org.jsoup:jsoup:1.14.3")
    implementation("org.slf4j:slf4j-api:2.0.0") {
        because("ph-css")
    }
    implementation("com.helger:ph-css:6.5.0")

}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
val mainClass = "de.jensklingenberg.htmltocfw.converter.MainKt"
tasks {
    register("fatJar", Jar::class.java) {
        archiveClassifier.set("all")
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        manifest {
            attributes("Main-Class" to mainClass)
        }

        from(configurations.compileClasspath.get()
            .onEach { println("add from dependencies: ${it.name}") }
            .map { if (it.isDirectory) it else zipTree(it) })
        from(configurations.runtimeClasspath.get()
            .onEach { println("add from dependencies: ${it.name}") }
            .map { if (it.isDirectory) it else zipTree(it) })
        val sourcesMain = sourceSets.main.get()
        sourcesMain.allSource.forEach { println("add from sources: ${it.name}") }
        from(sourcesMain.output)
    }
}

application {
    mainClass.set("MainKt")
}