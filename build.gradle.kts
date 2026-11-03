import java.nio.charset.Charset

import com.diffplug.spotless.LineEnding
import net.ltgt.gradle.errorprone.errorprone

val isCiBuild = providers.environmentVariable("CI").map { it.toBoolean() }.orElse(false).get()
val isSnapshot = providers.gradleProperty("isSnapshot").map { it.toBoolean() }.orElse(false).get()

val baseVersion = project.version.toString()
project.version = if (isSnapshot) "$baseVersion-SNAPSHOT" else if (!isCiBuild) "$baseVersion.local" else baseVersion

plugins {
    alias(libs.plugins.leaf.loom)

    alias(libs.plugins.errorprone)
    alias(libs.plugins.nullaway)

    checkstyle
    alias(libs.plugins.spotless)
}

dependencies {
    zomboid(libs.zomboid)
    implementation(libs.leaf.loader)

    compileOnly(libs.jspecify)
    errorprone(libs.errorprone)
    errorprone(libs.nullaway)
}

loom {
    // Decompiles only the zombie classes instead of every file.
    decompilers {
        getByName("vineflower") {
            options.putAll(mapOf(
                "included-classes" to "zombie.*",
            ))
        }
    }

    mods {
        create(project.name) {
            sourceSet(sourceSets.main.get())
        }
    }

    runs {
        named("client") {
            programArguments.addAll(listOf("-debug"))
        }
    }
}

nullaway {
    annotatedPackages.add(project.group.toString())
    // onlyNullMarked = true
    // jspecifyMode = true
}

spotless {
    setEncoding(Charset.forName("UTF-8"))
    setLineEndings(LineEnding.UNIX)

    java {
        targetExclude("**/generated/**")

        eclipse().configFile(file("eclipse-formatter.xml"))
            .sortMembersEnabled(true)
            // SF,SI,SM,F,I,C,M,T = Static Fields, Static Initializers, Static Methods,
            //   Fields, Initializers, Constructors, Methods, (Nested) Types
            .sortMembersOrder("SF,F,SI,I,C,SM,M,T")
            .sortMembersDoNotSortFields(true)
            .sortMembersVisibilityOrderEnabled(true)
            // B,R,D,V = Public, Protected, Package, Private
            .sortMembersVisibilityOrder("B,R,D,V")

        cleanthat()
        // formatAnnotations()

        importOrder(
            "java|javax",
            "",
            "net.fabricmc",
            "\\#net.fabricmc",
            "$group",
            "\\#",
            "\\#$group",
            "zombie",
            "\\#zombie"
        )
        removeUnusedImports()
        forbidWildcardImports()
        forbidModuleImports()

        trimTrailingWhitespace()
        leadingTabsToSpaces()
        endWithNewline()

        toggleOffOn()
        licenseHeaderFile(file("HEADER")).yearSeparator("-")
    }
}

checkstyle {
    configFile = file("checkstyle.xml")
}

java {
    withSourcesJar()

    toolchain.languageVersion = JavaLanguageVersion.of(25)
}

tasks.processResources {
    val props = mapOf(
        "name" to project.name,
        "version" to project.version,
        "loader_version" to libs.versions.leaf.loader.get(),
        "zomboid_version" to libs.versions.zomboid.get(),
    )

    inputs.properties(props)
    filesMatching("leaf.mod.json") {
        expand(props)
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.errorprone {
        disableWarningsInGeneratedCode = true

        nullaway {
            error()
        }
    }
}
