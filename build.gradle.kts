plugins {
    `java-library`
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("io.freefair.lombok") version "8.6"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.3"
    id("xyz.jpenilla.run-paper") version "2.2.0"
}

repositories {
    mavenLocal()
    mavenCentral()
    maven(url = "https://papermc.io/repo/repository/maven-public/")
    maven(url = "https://jitpack.io")
}

dependencies {
    api("io.github.mooy1:InfinityLib:1.3.7")
    compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
    compileOnly("io.github.TheBusyBiscuit:Slimefun4:RC-37")
    compileOnly("com.google.code.findbugs:jsr305:3.0.2")

    testImplementation("com.github.seeseemelk:MockBukkit-v1.18:2.85.2")
}

group = "io.github.addoncommunity.galactifun"
version = "MODIFIED"
description = "Galactifun"
java.sourceCompatibility = JavaVersion.VERSION_17

tasks.shadowJar {
    relocate("io.github.mooy1.infinitylib", "io.github.addoncommunity.galactifun.infinitylib")

    archiveFileName = "galactifun.jar"
}

bukkit {
    name = rootProject.name
    description = "Space addon for Slimefun"
    main = "io.github.addoncommunity.galactifun.Galactifun"
    version = project.version.toString()
    authors = listOf("Seggan", "Mooy1", "GallowsDove", "ProfElements")
    apiVersion = "1.18"
    softDepend = listOf("ClayTech")
    loadBefore = listOf("Multiverse-Core")
    depend = listOf("Slimefun")

    commands {
        register("galactifun") {
            description = "Galactifun main command"
            usage = "/galactifun <subcommand>"
            aliases = listOf("gf", "galactic")
        }
    }
}

tasks.runServer {
    downloadPlugins {
        url("https://blob.build/dl/Slimefun4/Dev/1116")
    }
    maxHeapSize = "4G"
    minecraftVersion("1.20.4")
}