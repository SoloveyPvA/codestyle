package ru.pva.code.style.checks.base

import com.puppycrawl.tools.checkstyle.Checker
import com.puppycrawl.tools.checkstyle.PropertiesExpander
import com.puppycrawl.tools.checkstyle.api.AuditEvent
import spock.lang.Specification

import static com.puppycrawl.tools.checkstyle.ConfigurationLoader.loadConfiguration
import static java.util.Collections.singletonList

class ErrorAuditEventSpecificationIT extends Specification {

	protected static List<AuditEvent> runCheckFor(final String fileConfigPath, final String checkPath) {
		def checkingDirectory = new File(checkPath)
		def checker = new Checker()
		def listener = new StubAuditListener()
		def config = loadConfiguration(
				fileConfigPath,
				new PropertiesExpander(System.getProperties())
		)

		checker.setModuleClassLoader(Checker.class.getClassLoader())
		checker.configure(config)
		checker.addListener(listener)

		checker.process(singletonList(checkingDirectory))

		return listener.getErrorEvents()
	}

	protected static void assertErrorAuditEvent(
			final List<AuditEvent> actual,
			final String fileName,
			final int lineNo,
			final int columnNo,
			final String moduleName,
			final String errorMessage
	) {
		def filteredEvent = assertAuditEventInFileName actual, fileName
		filteredEvent = assertAuditEventInLine filteredEvent, lineNo
		filteredEvent = assertAuditEventInColumn filteredEvent, columnNo
		filteredEvent = assertAuditEventInModule filteredEvent, moduleName
		assertAuditEventWithErrorMessage filteredEvent, errorMessage
	}

	private static List<AuditEvent> assertAuditEventInFileName(final List<AuditEvent> actual, fileName) {
		def events = actual.findAll { it.fileName.endsWith(fileName) }
		assert events.size() != 0 : "Not found error in file ${fileName}"

		return events
	}

	private static List<AuditEvent> assertAuditEventInLine(final List<AuditEvent> actual, lineNo) {
		def events = actual.findAll { it.getLine() == lineNo }
		assert events.size() != 0 : "Not found error in line ${lineNo}"

		return events
	}

	private static List<AuditEvent> assertAuditEventInColumn(final List<AuditEvent> actual, columnNo) {
		def events = actual.findAll { it.getColumn() == columnNo }
		assert events.size() != 0 : "Not found error in column ${columnNo}"

		return events
	}

	private static List<AuditEvent> assertAuditEventInModule(final List<AuditEvent> actual, moduleName) {
		def events = actual.findAll { it.getSourceName() == moduleName }
		assert events.size() != 0 : "Not found error generated by module with name ${moduleName}"

		return events
	}

	private static List<AuditEvent> assertAuditEventWithErrorMessage(final List<AuditEvent> actual, errorMessage) {
		def events = actual.findAll { it.getMessage() == errorMessage }
		assert events.size() != 0 : "Not found error with message ${errorMessage}"

		return events
	}
}
