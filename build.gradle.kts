plugins {
    id("java")
}

group = "um.edu.uy"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.csvreader:CsvReader:2.1")
    implementation("com.opencsv:opencsv:5.8")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}