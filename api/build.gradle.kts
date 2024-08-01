dependencies {
    val swaggerVersion: String by project
    val swaggerUIVersion: String by project

    implementation(project(":common"))
    implementation(project(":common:common-domain"))
    implementation(project(":common:common-extension"))
    implementation(project(":common:common-application"))
    implementation(project(":common:common-redis-adapter"))
    implementation(project(":common:common-persistence-adapter"))

    implementation(project(":file"))
    implementation(project(":file:file-domain"))
    implementation(project(":file:file-application"))
    implementation(project(":file:file-management-adapter"))
    implementation(project(":file:file-persistence-adapter"))

    implementation(project(":token"))
    implementation(project(":token:token-domain"))
    implementation(project(":token:token-jwt-adapter"))

    implementation(project(":member"))
    implementation(project(":member:member-domain"))
    implementation(project(":member:member-application"))
    implementation(project(":member:member-persistence-adapter"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("io.swagger.core.v3:swagger-annotations:$swaggerVersion")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$swaggerUIVersion")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("io.micrometer:micrometer-registry-prometheus")
}
