import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar


var kotlinVersion = "1.4.10"
plugins {
    kotlin("jvm") version "1.4.10"
    id("com.github.johnrengelman.shadow") version "6.1.0"
    application
}
group = "de.swiesmann"
version = "0.1"

repositories {
    jcenter()
    mavenCentral()
}
dependencies {
    implementation("khttp:khttp:1.0.0" )
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    testImplementation(kotlin("test-testng"))
}
tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}
application {
    mainClassName = "MainKt"
}

tasks.withType<ShadowJar>() {
    manifest {
        attributes["Main-Class"] = "MainKt"
    }
}