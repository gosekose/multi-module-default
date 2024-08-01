dependencies {
    implementation(project(":common:common-domain"))
    implementation(project(":common:common-extension"))
    implementation(project(":common:common-application"))
    implementation(project(":token:token-domain"))
    implementation(project(":member:member-domain"))

    implementation("org.springframework.boot:spring-boot-starter-data-redis")
}
