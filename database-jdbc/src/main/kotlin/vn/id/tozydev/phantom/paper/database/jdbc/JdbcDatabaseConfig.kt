package vn.id.tozydev.phantom.paper.database.jdbc

import com.zaxxer.hikari.HikariDataSource
import org.spongepowered.configurate.objectmapping.ConfigSerializable
import org.spongepowered.configurate.objectmapping.meta.Required
import java.nio.file.Path
import kotlin.io.path.Path

/**
 * A data class representing the configuration details required to connect to a database.
 */
@ConfigSerializable
data class JdbcDatabaseConfig(
    /**
     * The URL of the database.
     */
    @Required
    val url: String,
    /**
     * The [username] required to connect to the remote database.
     */
    val username: String? = null,
    /**
     * The [password] required to connect to the remote database.
     */
    val password: String? = null,
    /**
     * The maximum number of connections that can be created in the connection pool.
     */
    val poolSize: Int = 8,
)

internal val JdbcDatabaseConfig.driverClassName: String
    get() =
        when {
            url.startsWith("jdbc:mysql:") -> "com.mysql.cj.jdbc.Driver"
            url.startsWith("jdbc:mariadb:") -> "org.mariadb.jdbc.Driver"
            url.startsWith("jdbc:postgresql:") -> "org.postgresql.Driver"
            url.startsWith("jdbc:sqlite:") -> "org.sqlite.JDBC"
            url.startsWith("jdbc:h2:") -> "org.h2.Driver"
            else -> throw IllegalArgumentException("Unsupported database URL: $url")
        }

private val SQLITE_FILE_START_PART = "sqlite:(/|\\./)?(.+)".toRegex(RegexOption.IGNORE_CASE)

internal fun JdbcDatabaseConfig.jdbcUrl(parent: Path) =
    url.replace(SQLITE_FILE_START_PART) {
        "sqlite:${parent.resolve(it.groups[2]?.value ?: "data.sqlite3").normalize()}"
    }

/**
 * Creates a [HikariDataSource] instance configured with the provided [config].
 *
 * @param config The [JdbcDatabaseConfig] containing the database connection details.
 * @param workingDir The working directory where the SQLite database file is located, if applicable.
 */
fun HikariDataSource(
    config: JdbcDatabaseConfig,
    workingDir: Path = Path(""),
): HikariDataSource =
    HikariDataSource().apply {
        jdbcUrl = config.jdbcUrl(workingDir)
        driverClassName = config.driverClassName
        config.username?.let { username = it }
        config.password?.let { password = it }
        maximumPoolSize = config.poolSize
    }
