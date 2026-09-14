plugins {
    id("fabric-loom") version "1.8.+"
    id("maven-publish")
    kotlin("jvm") version "2.0.20"
}

base.archivesName.set(project.property("archives_base_name").toString())
version = project.property("mod_version").toString()
group = project.property("maven_group").toString()

repositories {
    mavenCentral()
    maven("https://api.modrinth.com/maven") {
        name = "Modrinth"
    }
}

dependencies {
    minecraft("com.mojang:minecraft:${project.property("minecraft_version")}")
    mappings(loom.officialMojangMappings())
    modImplementation("net.fabricmc:fabric-loader:${project.property("loader_version")}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${project.property("fabric_version")}")
    modImplementation("net.fabricmc:fabric-language-kotlin:${project.property("fabric_kotlin_version")}")
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(21)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "21"
    }
}

java.sourceCompatibility = JavaVersion.VERSION_21
java.targetCompatibility = JavaVersion.VERSION_21

tasks.processResources {
    inputs.property("version", project.version)
    inputs.property("mod_name", project.property("mod_name"))
    inputs.property("mod_description", project.property("mod_description"))

    filesMatching("fabric.mod.json") {
        expand(
            "version" to project.version,
            "mod_name" to project.property("mod_name"),
            "mod_description" to project.property("mod_description")
        )
    }
}

tasks.jar {
    from("LICENSE") {
        rename { "${it}_${base.archivesName.get()}" }
    }
}

publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            artifact(tasks.remapJar) {
                builtBy(tasks.remapJar)
            }
            artifact(tasks.sourcesJar) {
                builtBy(tasks.sourcesJar)
            }
        }
    }

    repositories {
        mavenLocal()
    }
}

tasks.build {
    dependsOn(tasks.remapJar)
}
