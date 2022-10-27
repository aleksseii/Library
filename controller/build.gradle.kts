plugins {
    id("java")
    application
}

application {
    mainClass.set("ru.aleksseii.Application")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":models"))
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}