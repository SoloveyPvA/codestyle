package ru.pva.code.style.checks.whitespace

import ru.pva.code.style.checks.base.ErrorAuditEventSpecificationIT
import spock.lang.Unroll

import java.nio.file.Files
import java.nio.file.Paths

import static java.nio.charset.StandardCharsets.UTF_8

@Unroll
class EmptyLineBeforeReturnCheckAutoFixingSpecIT extends ErrorAuditEventSpecificationIT {

	private static final String CHECKSTYLE_CONFIGURATION_FILE = "src/test/resources/returns/return_check_with_auto_fixing.xml"

	def "insert empty string before return"(final String filePath) {
		setup:
			def path = Paths.get(filePath)
			def initialLines = Files.readAllLines(path, UTF_8)
			def returnLineNo = 11
		when:
			def actualErrorEvents = runCheckFor(CHECKSTYLE_CONFIGURATION_FILE, filePath)
			def actualLines = Files.readAllLines(path, UTF_8)
		then:
			actualErrorEvents.size() == 0
			actualLines.size() > initialLines.size()
			initialLines.get(returnLineNo - 1) != ""
			actualLines.get(returnLineNo - 1) == ""
		cleanup:
			Files.write(path, initialLines, UTF_8)
		where:
			filePath << ["src/test/resources/returns/InsertEmptyLineBeforeReturnAutoFix.java"]
	}

	def "replace blank string on empty before return"(final String filePath) {
		setup:
			def path = Paths.get(filePath)
			def initialLines = Files.readAllLines(path, UTF_8)
			def returnLineNo = 12
		when:
			def actualErrorEvents = runCheckFor(CHECKSTYLE_CONFIGURATION_FILE, filePath)
			def actualLines = Files.readAllLines(path, UTF_8)
		then:
			actualErrorEvents.size() == 0
			actualLines.size() == initialLines.size()
			initialLines.get(returnLineNo - 2) == "\t\t"
			actualLines.get(returnLineNo - 2) == ""
		cleanup:
			Files.write(path, initialLines, UTF_8)
		where:
			filePath << ["src/test/resources/returns/ReplaceBlankLineOnEmptyBeforeReturnAutoFix.java"]
	}

}
