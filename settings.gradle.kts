rootProject.name = "kotlin-spring-multimodule-default"

pluginManagement {
    val springBootVersion: String by settings
    val springDependencyManagementVersion: String by settings
    val kotlinVersion: String by settings
    val kotlinAllOpenVersion: String by settings
    val kotlinNoArgVersion: String by settings
    val kaptVersion: String by settings
    val klintVersion: String by settings

    plugins {
        id("org.springframework.boot") version springBootVersion
        id("io.spring.dependency-management") version springDependencyManagementVersion
        id("org.jlleitschuh.gradle.ktlint") version klintVersion
        kotlin("jvm") version kotlinVersion
        kotlin("plugin.spring") version kotlinVersion
        kotlin("plugin.noarg") version kotlinNoArgVersion
        kotlin("plugin.allopen") version kotlinAllOpenVersion
        kotlin("kapt") version kaptVersion
    }
}

include("common")
include("common:common-domain")
include("common:common-extension")
include("common:common-application")
include("common:common-redis-adapter")
include("common:common-persistence-adapter")

include("file")
include("file:file-domain")
include("file:file-application")
include("file:file-management-adapter")
include("file:file-persistence-adapter")

include("token")
include("token:token-domain")
include("token:token-jwt-adapter")

include("member")
include("member:member-domain")
include("member:member-application")
include("member:member-persistence-adapter")

include("api")
