package br.com.lbenaducci.meowth.shared.domain.datatypes

import br.com.lbenaducci.meowth.shared.domain.errors.ErrorCatalog
import br.com.lbenaducci.meowth.shared.domain.exceptions.ValidationException
import java.time.Instant
import java.time.temporal.ChronoUnit

class Audit private constructor(
    val createdAt: Instant,
    updatedAt: Instant
) {
    var updatedAt: Instant = updatedAt
        private set

    companion object {
        fun create(): Audit {
            val now = Instant.now().truncatedTo(ChronoUnit.MILLIS)
            return Audit(
                createdAt = now,
                updatedAt = now
            )
        }

        fun with(createdAt: Instant, updatedAt: Instant): Audit {
            if (createdAt > Instant.now()) {
                val error = object : ErrorCatalog {
                    override val code: String = "invalid.audit.createdAt"
                    override val defaultMessage: String = "created at must be after now"
                }
                throw ValidationException(error, "audit.createdAt", createdAt)
            }
            if (createdAt > updatedAt) {
                val error = object : ErrorCatalog {
                    override val code: String = "invalid.audit.updatedAt"
                    override val defaultMessage: String = "updated at must be after created at"
                }
                throw ValidationException(error, "audit.updatedAt", updatedAt)
            }
            return Audit(
                createdAt = createdAt.truncatedTo(ChronoUnit.MILLIS),
                updatedAt = updatedAt.truncatedTo(ChronoUnit.MILLIS)
            )
        }
    }

    fun update() {
        this.updatedAt = Instant.now().truncatedTo(ChronoUnit.MILLIS)
    }
}
