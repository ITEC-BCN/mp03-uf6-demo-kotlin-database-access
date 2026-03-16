import java.util.Properties

val secretProps = Properties()
val secretFile = project.rootProject.file("secrets.properties")
if (secretFile.exists()) {
    secretProps.load(secretFile.inputStream())
}

tasks.withType<JavaExec> {
    // Passem les propietats de Gradle a la JVM de l'aplicació
    if (secretFile.exists()) {
        systemProperty("SUPABASE_URL", secretProps.getProperty("supabase.url"))
        systemProperty("SUPABASE_USER", secretProps.getProperty("supabase.user"))
        systemProperty("SUPABASE_PASS", secretProps.getProperty("supabase.pass"))
        systemProperty("SUPABASE_PORT", secretProps.getProperty("supabase.port"))
    }
}

plugins {
    kotlin("jvm") version "1.9.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation("org.xerial:sqlite-jdbc:3.36.0.3")
    implementation("org.postgresql:postgresql:42.7.3")

    // Exposed (ORM per a Kotlin)
    implementation("org.jetbrains.exposed:exposed-core:0.47.0")
    implementation("org.jetbrains.exposed:exposed-dao:0.47.0")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.47.0")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(20)
}