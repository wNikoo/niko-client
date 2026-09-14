plugins {
    id("fabric-loom") version "1.8.10"
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
    options.release.set(25)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_25)
    }
}

java.sourceCompatibility = JavaVersion.VERSION_25
java.targetCompatibility = JavaVersion.VERSION_25

tasks.processResources {
    inputs.property("version", project.version)
    inputs.property("mod_name", project.property("mod_name").toString())
    inputs.property("mod_description", project.property("mod_description").toString())

    filesMatching("fabric.mod.json") {
        expand(
            "version" to project.version,
            "mod_name" to project.property("mod_name").toString(),
            "mod_description" to project.property("mod_description").toString()
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
        }
    }

    repositories {
        mavenLocal()
    }
}

tasks.build {
    dependsOn(tasks.remapJar)
}
