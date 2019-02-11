package ru.pva.code.style.checks.base

import com.puppycrawl.tools.checkstyle.api.AuditEvent
import com.puppycrawl.tools.checkstyle.api.AuditListener
import org.apache.commons.lang3.tuple.Pair 

class StubAuditListener implements AuditListener {

	private final List<AuditEvent> auditStartedEvents = new ArrayList<>()
	private final List<AuditEvent> auditFinishedEvents = new ArrayList<>()
	private final List<AuditEvent> fileStartedEvents = new ArrayList<>()
	private final List<AuditEvent> fileFinishedEvents = new ArrayList<>()
	private final List<AuditEvent> errorEvents = new ArrayList<>()
	private final List<Pair<AuditEvent, Throwable>> exceptionEvents = new ArrayList<>()

	List<AuditEvent> getAuditStartedEvents() {
		return auditStartedEvents
	}

	List<AuditEvent> getAuditFinishedEvents() {
		return auditFinishedEvents
	}

	List<AuditEvent> getFileStartedEvents() {
		return fileStartedEvents
	}

	List<AuditEvent> getFileFinishedEvents() {
		return fileFinishedEvents
	}

	List<AuditEvent> getErrorEvents() {
		return errorEvents
	}

	List<Pair<AuditEvent, Throwable>> getExceptionEvents() {
		return exceptionEvents
	}

	@Override
	void auditStarted(final AuditEvent auditEvent) {
		auditStartedEvents.add(auditEvent)
	}

	@Override
	void auditFinished(final AuditEvent auditEvent) {
		auditFinishedEvents.add(auditEvent)
	}

	@Override
	void fileStarted(final AuditEvent auditEvent) {
		fileStartedEvents.add(auditEvent)
	}

	@Override
	void fileFinished(final AuditEvent auditEvent) {
		fileFinishedEvents.add(auditEvent)
	}

	@Override
	void addError(final AuditEvent auditEvent) {
		errorEvents.add(auditEvent)
	}

	@Override
	void addException(final AuditEvent auditEvent, final Throwable throwable) {
		exceptionEvents.add(Pair.of(auditEvent, throwable))
	}
}
