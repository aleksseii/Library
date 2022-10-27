plugins {
    id("java")
}

repositories {
    mavenCentral()
}

subprojects {
    apply(plugin = "java")
}

allprojects {

    group = "ru.aleksseii"
    version = "1.0-SNAPSHOT"

    dependencies {

        testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.1")
        testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.1")
        testImplementation("org.junit.jupiter:junit-jupiter-params:5.9.1")

        testImplementation("org.mockito:mockito-inline:4.8.1")
        testImplementation("org.mockito:mockito-junit-jupiter:4.8.1")

        testImplementation("org.hamcrest:hamcrest-all:1.3")

        implementation("org.jetbrains:annotations:23.0.0")

        compileOnly("org.projectlombok:lombok:1.18.24")
        annotationProcessor("org.projectlombok:lombok:1.18.24")

        implementation("com.google.code.gson:gson:2.10")
    }

    tasks {
        test {
            useJUnitPlatform()
        }
    }
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
