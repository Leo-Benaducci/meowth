package br.com.lbenaducci.meowth.shared.domain.datatypes

import br.com.lbenaducci.meowth.shared.domain.exceptions.ValidationException
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.assertNotNull
import org.mockito.Mockito.mockStatic
import org.mockito.kotlin.whenever
import java.time.Instant
import java.time.temporal.ChronoUnit
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class AuditTest {
    @Nested
    inner class Create {
        @Test
        fun `when create, then return Audit`() {
            val fixedInstant = Instant.parse("2026-03-29T10:00:00Z")
            val expectedInstant = fixedInstant.truncatedTo(ChronoUnit.MILLIS)

            mockStatic(Instant::class.java).use { mockedInstant ->
                mockedInstant.whenever { Instant.now() }.thenReturn(fixedInstant)

                val created = Audit.create()

                assertNotNull(created)
                assertEquals(expectedInstant, created.createdAt)
                assertEquals(expectedInstant, created.updatedAt)
            }
        }
    }

    @Nested
    inner class With {
        @Test
        fun `given valid params, then return Audit`() {
            val fixedInstant = Instant.parse("2026-03-29T10:00:00Z")
            val expectedInstant = fixedInstant.truncatedTo(ChronoUnit.MILLIS)

            val audit = Audit.with(fixedInstant, fixedInstant)

            assertNotNull(audit)
            assertEquals(expectedInstant, audit.createdAt)
            assertEquals(expectedInstant, audit.updatedAt)
        }

        @Test
        fun `given invalid createdAt, then return throw exception`() {
            val createdAt = Instant.parse("2126-03-29T10:00:00Z")
            val updatedAt = Instant.parse("2026-03-29T09:00:00Z")

            assertFailsWith<ValidationException> { Audit.with(createdAt, updatedAt) }
                .also {
                    assertEquals("invalid.audit.createdAt", it.detail.error.code)
                    assertEquals("created at must be after now", it.detail.error.defaultMessage)
                    assertEquals("audit.createdAt", it.detail.field)
                    assertEquals(createdAt, it.detail.rejectedValue)
                }
        }

        @Test
        fun `given invalid updatedAt, then return throw exception`() {
            val createdAt = Instant.parse("2026-03-29T10:00:00Z")
            val updatedAt = Instant.parse("2026-03-29T09:00:00Z")

            assertFailsWith<ValidationException> { Audit.with(createdAt, updatedAt) }
                .also {
                    assertEquals("invalid.audit.updatedAt", it.detail.error.code)
                    assertEquals("updated at must be after created at", it.detail.error.defaultMessage)
                    assertEquals("audit.updatedAt", it.detail.field)
                    assertEquals(updatedAt, it.detail.rejectedValue)
                }
        }
    }

    @Nested
    inner class Update {
        @Test
        fun `when update, then change updatedAt to now`() {
            val createdAt = Instant.parse("2026-03-29T09:00:00Z")
            val expectedInstant = Instant.parse("2026-03-29T10:00:00Z").truncatedTo(ChronoUnit.MILLIS)
            val audit = Audit.with(createdAt, createdAt)

            mockStatic(Instant::class.java).use { mockedInstant ->
                mockedInstant.whenever { Instant.now() }.thenReturn(expectedInstant)

                audit.update()

                assertEquals(createdAt, audit.createdAt)
                assertEquals(expectedInstant, audit.updatedAt)
            }
        }
    }
}