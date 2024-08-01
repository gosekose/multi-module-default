dependencies {
    val jjwtVersion: String by project

    implementation(project(":common:common-domain"))
    implementation(project(":common:common-extension"))

    implementation(project(":token:token-domain"))

    implementation("io.jsonwebtoken:jjwt-api:$jjwtVersion")
    implementation("io.jsonwebtoken:jjwt-impl:$jjwtVersion")
    implementation("io.jsonwebtoken:jjwt-jackson:$jjwtVersion")
}
