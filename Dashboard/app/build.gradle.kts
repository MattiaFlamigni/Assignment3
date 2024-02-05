/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java application project to get you started.
 * For more details take a look at the 'Building Java & JVM projects' chapter in the Gradle
 * User Manual available at https://docs.gradle.org/8.0.2/userguide/building_java_projects.html
 */

plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // Use JUnit Jupiter for testing.
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")

    // This dependency is used by the application.
    implementation("com.google.guava:guava:31.1-jre")

    implementation("javax.servlet:javax.servlet-api:4.0.1")

    implementation("org.jfree:jfreechart:1.5.3")
    implementation("org.jfree:jcommon:1.0.23")
    implementation("com.google.code.gson:gson:2.8.8")
    implementation("org.apache.commons:commons-csv:1.8")
    /*http client */
    implementation("org.apache.httpcomponents:httpclient:4.5.13")
}

application {
    // Define the main class for the application.
    mainClass.set("dashboard.Main")
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}
