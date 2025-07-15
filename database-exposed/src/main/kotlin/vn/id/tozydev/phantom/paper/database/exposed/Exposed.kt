package vn.id.tozydev.phantom.paper.database.exposed

import org.jetbrains.exposed.v1.jdbc.Database
import vn.id.tozydev.phantom.paper.database.jdbc.JdbcDatabaseConfig
import vn.id.tozydev.phantom.paper.database.jdbc.HikariDataSource
import java.nio.file.Path
import java.sql.Connection

/**
 * Establishes a connection to a database using the provided configuration.
 *
 * @param config The configuration object containing the necessary details for connecting to the database.
 * @param setupConnection A function that takes a `Connection` object and performs any additional setup or initialization.
 *                        This function is optional and defaults to an empty lambda.
 * @param workingDir The working directory where the SQLite database file is located, if applicable.
 * @param poolName An optional name for the connection pool. If provided, it will be set on the HikariDataSource.
 * @return A Database object representing the connected database.
 */
fun Database.Companion.connect(
    config: JdbcDatabaseConfig,
    setupConnection: (Connection) -> Unit = {},
    workingDir: Path = Path.of(""),
    poolName: String? = null,
): Database {
    val ds =
        HikariDataSource(config, workingDir).apply {
            poolName?.let { this.poolName = it }
            // as of version 0.46.0, if these options are set here, they do not need to be duplicated in DatabaseConfig
            isReadOnly = false
            transactionIsolation = "TRANSACTION_SERIALIZABLE"
        }
    return Database.connect(
        datasource = ds,
        setupConnection = setupConnection,
    )
}
