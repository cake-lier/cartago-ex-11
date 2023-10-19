import com.bmuschko.gradle.docker.tasks.image.*

plugins {
    java
    application
    id("com.bmuschko.docker-remote-api") version "9.3.4"
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

defaultTasks = mutableListOf("run")

group = "io.github.cake-lier"
version = "0.1.0"

repositories {
    mavenCentral()
    maven {
        url = uri("https://raw.github.com/jacamo-lang/mvn-repo/master")
    }
    maven {
        url = uri("https://repo.gradle.org/gradle/libs-releases/")  // For current version of Gradle tooling API
    }
    maven {
        url = uri("https://repo.gradle.org/gradle/libs-releases-local/") // For older versions of Gradle tooling API
    }
}

dependencies {
    implementation("org.jacamo:jacamo:1.2.2")
}

sourceSets {
    main {
        java {
            srcDir("src/main/java")
            srcDir("src/main/jade")
            srcDir("src/main/cartago")
        }
        resources {
            srcDir("src/main/resources")
            srcDir("src/main/jason")
            srcDir("src/main/moise")
        }
    }
    test {
        java {
            srcDir("test/main/java")
            srcDir("test/main/jade")
            srcDir("test/main/cartago")
        }
        resources {
            srcDir("test/main/resources")
            srcDir("test/main/jason")
            srcDir("test/main/moise")
        }
    }
}

tasks {
    val pullImage = create<DockerPullImage>("pullCartagoImage") {
        image = "matteocastellucci3/cartago:latest"
    }
    create<Exec>("startCartagoNode") {
        description = "Runs the CArtAgO node to be used as a remote node"
        dependsOn(pullImage)
        commandLine = mutableListOf("docker", "run", "-p", "20100:20100", "matteocastellucci3/cartago:latest")
    }
    getByName<JavaExec>("run") {
        description = "Runs the JaCaMo application"
        dependsOn("classes")
        doFirst {
            mkdir("log")
        }
        mainClass = "jacamo.infra.JaCaMoLauncher"
        args = listOf("config.jcm")
        classpath = sourceSets.main.get().runtimeClasspath
    }
}
