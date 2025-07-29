package vn.id.tozydev.phantom.paper.database.jdbc

import java.io.File
import kotlin.io.path.Path
import kotlin.test.Test
import kotlin.test.assertEquals

class JdbcDatabaseConfigTest {
    @Test
    fun `jdbcUrl should return correct URL for non sqlite jdbc url`() {
        // Arrange
        val config =
            JdbcDatabaseConfig(
                url = "jdbc:mysql://localhost:3306/test_db",
            )
        val expected = "jdbc:mysql://localhost:3306/test_db"

        // Act
        val actual = config.jdbcUrl(Path(""))

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun `jdbcUrl should return correct URL with parent path for sqlite jdbc url`() {
        // Arrange
        val config =
            JdbcDatabaseConfig(
                url = "jdbc:sqlite:./test.db",
            )
        val parent = Path("parent")
        val expected = "jdbc:sqlite:$parent${File.separator}test.db"

        // Act
        val actual = config.jdbcUrl(parent)

        // Assert
        assertEquals(expected, actual)
    }
}
