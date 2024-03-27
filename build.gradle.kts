plugins {
    `java-library`
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("io.freefair.lombok") version "8.6"
}

repositories {
    mavenLocal()
    mavenCentral()
    maven(url = "https://papermc.io/repo/repository/maven-public/")
    maven(url = "https://jitpack.io")
}

dependencies {
    api("io.github.mooy1:InfinityLib:1.3.7")
    compileOnly("io.papermc.paper:paper-api:1.18-R0.1-SNAPSHOT")
    compileOnly("io.github.TheBusyBiscuit:Slimefun4:RC-37")
    compileOnly("com.google.code.findbugs:jsr305:3.0.2")

    testImplementation("com.github.seeseemelk:MockBukkit-v1.18:2.85.2")
}

group = "io.github.addoncommunity.galactifun"
version = "MODIFIED"
description = "Galactifun"
java.sourceCompatibility = JavaVersion.VERSION_16

tasks.shadowJar {
    relocate("io.github.mooy1.infinitylib", "io.github.addoncommunity.galactifun.infinitylib")

    archiveFileName = "Galactifun.jar"
}