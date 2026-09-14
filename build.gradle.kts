import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("net.fabricmc.fabric-loom")
    `maven-publish`
    kotlin("jvm")
    idea
}

idea {
    module {
        isDownloadSources = true
        isDownloadJavadoc = true
    }
}

val minecraft_version = project.property("minecraft_version") as String
val loader_version = project.property("loader_version") as String
val fabric_kotlin_version = project.property("fabric_kotlin_version") as String
val mod_version = project.property("mod_version") as String
val maven_group = project.property("maven_group") as String
val mod_name = project.property("mod_name") as String
val fabric_version = project.property("fabric_version") as String

version = mod_version
group = maven_group

base { archivesName.set(mod_name) }

repositories {
    maven("https://api.modrinth.com/maven")
    maven("https://jitpack.io")
    mavenCentral()
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraft_version")
    
    implementation("net.fabricmc:fabric-loader:$loader_version")
    implementation("net.fabricmc.fabric-api:fabric-api:$fabric_version")
    implementation("net.fabricmc:fabric-language-kotlin:$fabric_kotlin_version")
    
    testImplementation(kotlin("test"))
}

tasks.withType<JavaCompile>().configureEach { options.release.set(25) }
tasks.withType<KotlinCompile>().configureEach { compilerOptions { jvmTarget.set(JvmTarget.JVM_25) } }

tasks.named<Jar>("jar") {
    from("LICENSE") {
        rename { "${it}_$mod_name" }
    }
}

publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            artifact(tasks.remapJar) {
                builtBy(tasks.remapJar)
            }
        }
    }

    repositories {
        mavenLocal()
    }
}
