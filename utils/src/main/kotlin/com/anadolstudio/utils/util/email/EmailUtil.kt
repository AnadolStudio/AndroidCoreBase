package com.anadolstudio.utils.util.email

import com.anadolstudio.utils.util.extentions.nullIfEmpty
import java.util.regex.Pattern

object EmailUtil {

    private const val EMAIL_PARTS_COUNT = 3
    private const val MAIN_EMAIL_PARTS_COUNT = 2

    private const val LOCAL_INDEX = 0
    private const val DOMAIN_INDEX = 1

    private const val NAME_DOMAIN_EMAIL_INDEX = 1
    private const val HOST_DOMAIN_EMAIL_INDEX = 2

    private const val HOST_MIN_SIZE = 3 // include dot
    private const val HOST_MAX_SIZE = 25
    private const val NAME_MIN_SIZE = 3 // include @
    private const val NAME_MAX_SIZE = 64
    private const val LOCAL_MIN_SIZE = 1

    private const val NOT_FOUND = -1

    private const val BASE_SYMBOLS = "a-zA-Z0-9"
    private const val TWO_DOTS = ".." // TODO как-нибудь занести в регулярку проверку на вхождение двух точек

    private const val LOCAL_PART_PATTERN: String = "^[$BASE_SYMBOLS\\+\\.\\_\\%\\-]"
    private const val DOMAIN_PART_PATTERN: String = "\\@[[^\\-.]&&$BASE_SYMBOLS\\.][$BASE_SYMBOLS\\-]"
    private const val HOST_PATTERN: String = "\\.[$BASE_SYMBOLS]"

    private const val PRE_LOCAL_PART_EMAIL: String = "$LOCAL_PART_PATTERN+"
    private const val PRE_DOMAIN_PART_EMAIL: String = "$DOMAIN_PART_PATTERN*"
    private const val PRE_HOST_EMAIL: String = "$HOST_PATTERN*"

    // Pattern for email validation
    // 1. local-part [a-zA-Z0-9\+\.\_\%\-]{1,64}
    // - uppercase and lowercase Latin letters A to Z and a to z
    // - digits 0 to 9
    // - printable characters - _ % + - .
    // from 1 to 64 symbols
    private const val LOCAL_PART_EMAIL: String = "$LOCAL_PART_PATTERN{1,64}"

    // 2. domain-part [a-zA-Z0-9][a-zA-Z0-9\-]{1,64}
    // - uppercase and lowercase Latin letters A to Z and a to z
    // - digits 0 to 9 and '-' not in the beginning
    // from 2 to 64 symbols
    private const val DOMAIN_PART_EMAIL: String = "$DOMAIN_PART_PATTERN{1,63}"

    // 3. (.[a-zA-Z0-9][a-zA-Z0-9\-]{2,24}) group repeats 1 or more times
    // - dot and uppercase and lowercase Latin letters A to Z and a to z
    // - digits 2 to 9
    // from 3 to 25 symbols (включая точку)
    private const val HOST_EMAIL: String = "$HOST_PATTERN{2,24}$"

    val EMAIL_PATTERN_REGEX = "$LOCAL_PART_EMAIL$DOMAIN_PART_EMAIL$HOST_EMAIL".toRegex()

    @Suppress("detekt.ReturnCount")
    fun String.emailHaveAllParts(): Boolean {
        val parts = getEmailParts(this)
        val nonNullParts = parts.filterNotNull()

        if (nonNullParts.size != parts.size) return false

        val map = mapOf(
                nonNullParts[LOCAL_INDEX] to PatternEmail.LOCAL,
                nonNullParts[NAME_DOMAIN_EMAIL_INDEX] to PatternEmail.DOMAIN_NAME,
                nonNullParts[HOST_DOMAIN_EMAIL_INDEX] to PatternEmail.DOMAIN_HOST
        )

        for ((part, partPattern) in map.entries) {
            when (partPattern) {
                PatternEmail.LOCAL, PatternEmail.DOMAIN_NAME -> if (part.isEmpty()) return false
                PatternEmail.DOMAIN_HOST -> if (part.length < partPattern.minLength) return false
            }
        }

        return true
    }

    fun String.containsTwoDots(): Boolean = contains(TWO_DOTS)

    private fun getEmailParts(email: String): Array<String?> {
        val emailParts = arrayOfNulls<String>(EMAIL_PARTS_COUNT)

        val mainParts = email.split("@".toRegex(), MAIN_EMAIL_PARTS_COUNT)
        emailParts[LOCAL_INDEX] = mainParts.getOrNull(LOCAL_INDEX).orEmpty()

        val domainPart = mainParts.getOrNull(DOMAIN_INDEX).orEmpty()
        val lastDotIndex = domainPart.lastIndexOf(".")

        val nameDomainPart = if (lastDotIndex == NOT_FOUND) domainPart else domainPart.substring(0, lastDotIndex)
        emailParts[NAME_DOMAIN_EMAIL_INDEX] = if (nameDomainPart.isEmpty()) null else "@$nameDomainPart"

        val hostDomainPart = if (lastDotIndex == NOT_FOUND) "" else domainPart.substring(lastDotIndex)
        emailParts[HOST_DOMAIN_EMAIL_INDEX] = hostDomainPart.nullIfEmpty()

        return emailParts
    }

    fun String.emailIsPreValid(): Boolean {
        val parts = getEmailParts(this)

        // Проверяет с конца
        val map = mapOf(
                parts[HOST_DOMAIN_EMAIL_INDEX] to PatternEmail.DOMAIN_HOST,
                parts[NAME_DOMAIN_EMAIL_INDEX] to PatternEmail.DOMAIN_NAME,
                parts[LOCAL_INDEX] to PatternEmail.LOCAL
        )

        var previousPart: String? = null
        for ((part, partPattern) in map.entries) {
            if (part == null) {
                if (previousPart != null) return false
                previousPart = null
                continue
            }

            val isValidPattern = Pattern
                    .compile(partPattern.pattern, Pattern.CASE_INSENSITIVE)
                    .matcher(part)
                    .matches()

            if (
                    !isValidPattern
                    || isTooLong(part, partPattern)
                    || isTooSmall(part, partPattern, previousPart)
                    || part.containsTwoDots()
            ) {
                return false
            }

            previousPart = part
        }

        return true
    }

    // Последняя часть на длину провериться не может, так как нельзя найти тригера завершенности
    private fun isTooSmall(part: String, partPattern: PatternEmail, previousPart: String?): Boolean =
            partPattern != PatternEmail.DOMAIN_HOST && part.length < partPattern.minLength && previousPart != null

    private fun isTooLong(part: String, partPattern: PatternEmail): Boolean = part.length > partPattern.maxLength

    private enum class PatternEmail(val pattern: String, val minLength: Int, val maxLength: Int) {
        LOCAL(PRE_LOCAL_PART_EMAIL, LOCAL_MIN_SIZE, NAME_MAX_SIZE),
        DOMAIN_NAME(PRE_DOMAIN_PART_EMAIL, NAME_MIN_SIZE, NAME_MAX_SIZE),
        DOMAIN_HOST(PRE_HOST_EMAIL, HOST_MIN_SIZE, HOST_MAX_SIZE)
    }

}
