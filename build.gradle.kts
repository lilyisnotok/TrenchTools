plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("io.papermc.paperweight.userdev") version "1.7.1"
}

group = "org.lils"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://lib.alpn.cloud/alpine-public/")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://repo.codemc.io/repository/maven-public/")
}

dependencies {
    paperweight.paperDevBundle(project.property("paper_version") as String)
    compileOnly(group = "co.crystaldev", name = "alpinecore", version = "0.4.1")
    compileOnly(group = "co.crystaldev", name = "itemize-api", version = "0.1.0")

    compileOnly(group = "org.projectlombok", name = "lombok", version = "1.18.30")
    annotationProcessor(group = "org.projectlombok", name = "lombok", version = "1.18.30")
}

val targetJavaVersion = 17

tasks.build {
    dependsOn("shadowJar")
}

tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand(props)
    }
}
