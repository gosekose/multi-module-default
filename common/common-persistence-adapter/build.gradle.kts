dependencies {
    val gsonVersion: String by project

    implementation(project(":common:common-domain"))
    implementation("com.google.code.gson:gson:$gsonVersion")
}
