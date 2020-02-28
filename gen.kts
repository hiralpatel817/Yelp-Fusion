#!/usr/bin/env kscript

@file:DependsOn("com.offbytwo:docopt:0.6.0.20150202")
@file:DependsOn("com.github.jknack:handlebars:4.1.0")
@file:DependsOn("org.slf4j:slf4j-nop:1.6.4")

import com.github.jknack.handlebars.Handlebars
import com.github.jknack.handlebars.io.FileTemplateLoader
import org.docopt.Docopt
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.nio.charset.Charset
import java.nio.file.Paths
import java.util.*

var isTestMode: Boolean = true
var basePackage = "com.example"

val loader = FileTemplateLoader(File("."))
loader.prefix = "./templates"
loader.suffix = ".hbs"
val handlebars = Handlebars(loader)

fun createTemplate(templateName: String, nameMappings: Any): String =
    handlebars.compile(templateName).apply(nameMappings)

fun generatePresentationTemplate(
    componentName: String,
    viewType: String,
    viewDefinition: String,
    layoutDefinition: String
) {
    val (componentPath, component, nameMappings) = produceViewNameMappings(componentName, viewType)

    write(
        template = createTemplate("Module", nameMappings),
        to = kotlinFile(
            directoryPrefix = "app/src/main/java/",
            fileName = "Module.kt",
            packageName = "$basePackage.presentation.$viewDefinition.$componentPath.module"
        )
    )
    write(
        template = createTemplate("Scope", nameMappings),
        to = kotlinFile(
            directoryPrefix = "app/src/main/java/",
            fileName = "Scope.kt",
            packageName = "$basePackage.presentation.$viewDefinition.$componentPath.module"
        )
    )
    write(
        template = createTemplate(viewDefinition, nameMappings),
        to = kotlinFile(
            directoryPrefix = "app/src/main/java/",
            fileName = "$component$viewType.kt",
            packageName = "$basePackage.presentation.$viewDefinition.$componentPath"
        )
    )
    write(
        template = createTemplate("ViewModel", nameMappings),
        to = kotlinFile(
            directoryPrefix = "app/src/main/java/",
            fileName = "${component}ViewModel.kt",
            packageName = "$basePackage.presentation.$viewDefinition.$componentPath"
        )
    )
    write(
        template = createTemplate("State", nameMappings),
        to = kotlinFile(
            directoryPrefix = "app/src/main/java/",
            fileName = "${component}State.kt",
            packageName = "$basePackage.presentation.$viewDefinition.$componentPath"
        )
    )
    write(
        template = createTemplate(layoutDefinition, nameMappings),
        to = resourceFile(
            directoryPrefix = "app/src/main/res/",
            fileName = "layout/${viewType.toLowerCase()}_${nameMappings["SNAKECOMPONENTPATH"]}.xml"
        )
    )
}

fun produceViewNameMappings(
    componentPath: String,
    viewType: String
): Triple<String, String, Map<String, String>> {

    val topLevel = componentPath.indexOf(".") == -1
    val componentName = componentPath.split(".").last()
    var path = componentPath.replace(".$componentName", "")
    var saneComponentPath = componentPath

    // Need a little fixup
    if (topLevel) {
        path = ""
        saneComponentPath = componentName
    }

    @Suppress("LocalVariableName")
    val snake_component_path = saneComponentPath.replace(".", "_").toLowerCase()

    val component = componentName.toLowerCase()
    val nameMappings = mapOf(
        "PACKAGE" to "$basePackage.presentation.$path",
        "BASEPACKAGE" to basePackage,
        "COMPONENT" to component,
        "SNAKECOMPONENTPATH" to snake_component_path,
        "VIEWMODEL" to "${componentName}ViewModel",
        "VIEWSTATE" to "${componentName}State",
        "PRESENTER" to "${componentName}Presenter",
        "VIEW" to "$componentName$viewType"
    )

    if (isTestMode) {
        println("Mappings: $nameMappings")
    }

    val finalComponentPath = if (topLevel) component else "$path.$component"

    return Triple(finalComponentPath, componentName, nameMappings)
}

fun kotlinFile(directoryPrefix: String, fileName: String, packageName: String): File {
    val packageDirectory = packageName.replace(".", "/")
    return Paths.get("$directoryPrefix$packageDirectory", fileName).toFile()
}

fun resourceFile(directoryPrefix: String, fileName: String): File {
    return Paths.get(directoryPrefix, fileName).toFile()
}

fun write(template: String, to: File) {

    if (!to.exists()) {
        if (!isTestMode) {
            to.parentFile.mkdirs()
        }
    } else {
        println("Skipped ${to.path}: file exists")
        return
    }

    if (!isTestMode) {
        to.printWriter(Charset.forName("utf-8")).use { out ->
            out.print(template)
        }
    }

    println(to.path)
}

fun loadProps() {
    try {
        val inputStream = FileInputStream(File("./gen.properties"))
        val props = Properties()
        props.load(inputStream)
        inputStream.close()
        props.getProperty("basePackage")?.let { basePackage = it }
        props.getProperty("isTestMode")?.toBoolean()?.let { isTestMode = it }
    } catch (ioe: IOException) {
        println("Unable to load properties from gen.properties")
    }
}

/*********************************
 * MAIN PROGRAM
 */

val usage = """
Usage:
 gen.kts [-tb PACKAGE] fragment <class>
 gen.kts [-tb PACKAGE] activity <class>
 gen.kts -h

Options:
 -h, --help                  Show this help
 -t, --test                  Test Mode
 -b PACKAGE, --base PACKAGE  Replace the base package.
 -i IN, --in IN              Input class (for use cases)
 -o OUT, --out OUT           Output class (for use cases)
"""

// Get the default properties
loadProps()

val docArgs: Map<String, Any> = Docopt(usage).withOptionsFirst(true).parse(args.toList())

if (docArgs["--base"] != null) {
    basePackage = docArgs["--base"] as String
}

isTestMode = docArgs["--test"] as Boolean

if (isTestMode) println("Arguments: $docArgs")

val componentName = docArgs["<class>"] as String

when {
    docArgs["fragment"] as Boolean -> {
        generatePresentationTemplate(
            componentName = componentName,
            viewType = "Fragment",
            viewDefinition = "Fragment",
            layoutDefinition = "FragmentLayout"
        )
    }
    docArgs["activity"] as Boolean -> {
        generatePresentationTemplate(
            componentName = componentName,
            viewType = "Activity",
            viewDefinition = "Activity",
            layoutDefinition = "ActivityLayout"
        )
    }
}
