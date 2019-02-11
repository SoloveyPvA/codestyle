package ru.pva.code.style.checks.whitespace

import ru.pva.code.style.checks.base.ErrorAuditEventSpecificationIT
import spock.lang.Unroll

@Unroll
class EmptyLineBeforeReturnCheckSpecIT extends ErrorAuditEventSpecificationIT {

	private static final String CHECKSTYLE_CONFIGURATION_FILE = "src/test/resources/returns/return_check.xml"
	private static final String ERROR_MESSAGE = "Empty string must be before return"
	private static final String MODULE_NAME = "EmptyLineBeforeReturnCheck"

	def "not logging error when empty line not required"() {
		when:
			def actual = runCheckFor(CHECKSTYLE_CONFIGURATION_FILE, "src/test/resources/returns/EmptyLineNotRequiredBeforeReturn.java")
		then:
			actual.size() == 0
	}

	def "log error when empty line not exist before return in method"() {
		when:
			def actual = runCheckFor(CHECKSTYLE_CONFIGURATION_FILE, "src/test/resources/returns/NonEmptyLineInMethodDef.java")
		then:
			assertErrorAuditEvent actual,
					"NonEmptyLineInMethodDef.java",
					11,
					9,
					MODULE_NAME,
					ERROR_MESSAGE

	}

	def "log error when empty line not exist before return in for"() {
		when:
			def actual = runCheckFor(CHECKSTYLE_CONFIGURATION_FILE, "src/test/resources/returns/NonEmptyLineInForDef.java")
		then:
			assertErrorAuditEvent actual,
					"NonEmptyLineInForDef.java",
					11,
					13,
					MODULE_NAME,
					ERROR_MESSAGE
	}

	def "log error when empty line not exist before return in if"() {
		when:
			def actual = runCheckFor(CHECKSTYLE_CONFIGURATION_FILE, "src/test/resources/returns/NonEmptyLineInIfDef.java")
		then:
			assertErrorAuditEvent actual,
					"NonEmptyLineInIfDef.java",
					11,
					13,
					MODULE_NAME,
					ERROR_MESSAGE
	}

	def "log error when empty line not exist before throw in method"() {
		when:
			def actual = runCheckFor(CHECKSTYLE_CONFIGURATION_FILE, "src/test/resources/returns/NonEmptyLineBeforeThrowInMethodDef.java")
		then:
			assertErrorAuditEvent actual,
					"NonEmptyLineBeforeThrowInMethodDef.java",
					11,
					9,
					MODULE_NAME,
					ERROR_MESSAGE
	}

}
