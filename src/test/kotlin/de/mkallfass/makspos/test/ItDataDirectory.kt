package de.mkallfass.makspos.test

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption

/**
 * Points the packaged application launched by `@QuarkusIntegrationTest` at a throwaway copy of the
 * test fixtures.
 *
 * Integration tests start the artifact in the `prod` profile, so `%test.data.directory` does not
 * apply and the application would otherwise serve the demo catalog from `data/` — which does not
 * contain the `TEST-0x` products the inherited `@QuarkusTest` assertions expect.
 *
 * The map returned by [start] is passed to the launched process as system properties, so
 * `data.directory` wins over the value baked into `application.properties`. A fresh copy per class
 * also keeps the orders written by the tests out of the source tree.
 *
 * The directory lives on the machine that runs the tests, so the application has to be launched
 * there as well. `quarkus.test.integration-test-artifact-type` in `pom.xml` pins that: without it,
 * a build that also creates the container image would start the application inside a container,
 * where the path exists only as an environment variable and the fixtures are nowhere to be found.
 */
class ItDataDirectory : QuarkusTestResourceLifecycleManager {

    private var dataDirectory: Path? = null

    override fun start(): Map<String, String> {
        val directory = Files.createTempDirectory("makspos-it-data")
        dataDirectory = directory
        Files.newDirectoryStream(fixtures()) { Files.isRegularFile(it) && it.fileName.toString() != ORDERS }
            .use { fixtureFiles ->
                fixtureFiles.forEach {
                    Files.copy(it, directory.resolve(it.fileName), StandardCopyOption.REPLACE_EXISTING)
                }
            }
        return mapOf("data.directory" to directory.toAbsolutePath().toString())
    }

    override fun stop() {
        dataDirectory?.toFile()?.deleteRecursively()
        dataDirectory = null
    }

    private fun fixtures(): Path {
        val url = checkNotNull(javaClass.getResource("/$FIXTURES")) {
            "Test fixture directory '$FIXTURES' is not on the test classpath"
        }
        return Path.of(url.toURI())
    }

    private companion object {
        const val FIXTURES = "data"
        const val ORDERS = "orders.jsonl"
    }
}
