import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") apply false
    id("io.spring.dependency-management")
    id("org.jlleitschuh.gradle.ktlint")
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.noarg")
    kotlin("plugin.allopen")
    kotlin("kapt")
}

allprojects {
    apply(plugin = "java")

    group = project.findProperty("projectGroup") as String
    version = project.findProperty("applicationVersion") as String

    extensions.configure<JavaPluginExtension>("java") {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }

    repositories {
        mavenCentral()
    }

    tasks.withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "17"
            freeCompilerArgs += "-Xjsr305=strict"
        }
    }

    if (hasProperty("buildScan")) {
        extensions.findByName("buildScan")?.withGroovyBuilder {
            setProperty("termsOfServiceUrl", "https://gradle.com/terms-of-service")
            setProperty("termsOfServiceAgree", "yes")
        }
    }
}

subprojects {
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(plugin = "kotlin")
    apply(plugin = "kotlin-spring")
    apply(plugin = "kotlin-noarg")
    apply(plugin = "kotlin-allopen")

    dependencies {
        val kotestVersion: String by project
        val kotestSpringVersion: String by project
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
        testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
        testImplementation("io.kotest:kotest-property:$kotestVersion")
        testImplementation("io.kotest:kotest-extensions-spring:$kotestSpringVersion")
    }

    tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
        enabled = false
    }
    tasks.withType<Jar> {
        enabled = true
    }

    tasks.register("prepareKotlinBuildScriptModel") {}

    if (name == "api") {
        tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
            enabled = true
        }
    }

    if (!name.contains("domain")) {
        dependencies {
            implementation("org.springframework.boot:spring-boot-starter")
            testImplementation("org.springframework.boot:spring-boot-starter-test")
        }
    }

    if (name.contains("persistence") || name.contains("api")) {
        apply(plugin = "kotlin-kapt")

        val kaptVersion: String by project
        val mysqlConnectorVersion: String by project
        val queryDslVersion: String by project

        dependencies {
            implementation("org.springframework.boot:spring-boot-starter-data-jpa")
            kapt("com.querydsl:querydsl-apt:$queryDslVersion:jakarta")
//            runtimeOnly("mysql:mysql-connector-java:$mysqlConnectorVersion")
            runtimeOnly("com.mysql:mysql-connector-j")

            implementation("com.querydsl:querydsl-jpa:$queryDslVersion:jakarta")
        }

        noArg {
            invokeInitializers = true
            annotation("javax.persistence.Entity")
            annotation("javax.persistence.MappedSuperclass")
            annotation("javax.persistence.Embeddable")
            annotation("jakarta.persistence.Entity")
            annotation("jakarta.persistence.MappedSuperclass")
            annotation("jakarta.persistence.Embeddable")
        }
    }
}
