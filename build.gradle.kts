

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "dev.rohrjaspi"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    compileOnly("org.projectlombok:lombok:1.18.38")
    annotationProcessor("org.projectlombok:lombok:1.18.38")
    implementation("net.dv8tion:JDA:5.6.1")
    implementation("com.squareup.okhttp3:okhttp:5.1.0")
    implementation("com.google.code.gson:gson:2.13.1")
    implementation("ch.qos.logback:logback-classic:1.4.14")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "dev.rohrjaspi.SpotifyBot"
    }
}
tasks.shadowJar {
    archiveBaseName.set("DiscordSpotifyBot")  // Name des JARs
    archiveVersion.set("1.0-SNAPSHOT") // Version
    archiveClassifier.set("")           // Keine zusätzliche Kennung wie "all" oder "fat"
}

tasks.test {
    useJUnitPlatform()
}