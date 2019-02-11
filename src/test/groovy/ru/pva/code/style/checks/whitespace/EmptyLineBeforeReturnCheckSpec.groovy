package ru.pva.code.style.checks.whitespace

import com.puppycrawl.tools.checkstyle.DefaultConfiguration
import com.puppycrawl.tools.checkstyle.api.DetailAST
import com.puppycrawl.tools.checkstyle.api.FileContents
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage
import com.puppycrawl.tools.checkstyle.api.LocalizedMessages
import de.jodamob.kotlin.testrunner.OpenedPackages
import de.jodamob.kotlin.testrunner.SpotlinTestRunner
import org.junit.runner.RunWith
import spock.lang.Specification
import spock.lang.Unroll

import static com.puppycrawl.tools.checkstyle.api.TokenTypes.*

@SuppressWarnings("GroovyAssignabilityCheck")
@Unroll
@RunWith(SpotlinTestRunner)
@OpenedPackages(["ru.pva.code.style.checks", "com.puppycrawl.tools.checkstyle"])
class EmptyLineBeforeReturnCheckSpec extends Specification {

	private static SOURCE_NAME = "EmptyLineBeforeReturnCheck"

	EmptyLineBeforeReturnCheck check

	DetailAST returnToken = Mock(DetailAST)
	DetailAST parentToken = Mock(DetailAST)
	LocalizedMessages localizedMessages = Mock(LocalizedMessages)
	FileContents fileContents = Mock(FileContents)

	def setup() {
		check = new EmptyLineBeforeReturnCheck()

		check.setMessages(localizedMessages)
		check.setFileContents(fileContents)
		check.configure(new DefaultConfiguration("any"))
	}

	def "getDefaultTokens"() {
		when:
			def actual = check.getDefaultTokens()
		then:
			actual != null
			actual.length == 2
			LITERAL_RETURN in actual
			LITERAL_THROW in actual
	}

	def "getAcceptableTokens"() {
		when:
			def actual = check.getAcceptableTokens()
		then:
			actual != null
			actual.length == 3
			SLIST in actual
			LITERAL_RETURN in actual
			LITERAL_THROW in actual
	}

	def "getRequiredTokens"() {
		when:
			def actual = check.getRequiredTokens()
		then:
			actual != null
			actual.length == 0
	}

	def "nothing doing when parent token is null"() {
		when:
			check.visitToken(returnToken)
		then:
			1 * returnToken.getParent() >> null
			0 * _
	}

	def "nothing doing when parent token is not block"() {
		when:
			check.visitToken(returnToken)
		then:
			1 * returnToken.getParent() >> parentToken
			1 * parentToken.getType() >> FINAL
			0 * _
	}

	def "check is ok when parent block has less than three lines"() {
		when:
			check.visitToken(returnToken)
		then:
			1 * returnToken.getParent() >> parentToken
			1 * parentToken.getType() >> SLIST
			1 * returnToken.getLineNo() >> 12
			1 * parentToken.getLineNo() >> 10
			0 * _
	}

	def "check is ok when return is have empty line before"() {
		when:
			check.visitToken(returnToken)
		then:
			1 * returnToken.getParent() >> parentToken
			1 * parentToken.getType() >> SLIST
			1 * parentToken.getLineNo() >> 1
			1 * returnToken.getLineNo() >> 4
			1 * fileContents.getLine(4 - 2) >> ""
			0 * _
	}

	def "check log fail when return is have whitespace line before"() {
		setup:
			def lines = ["{", "anyStringAnyString", "  ", "return 2;", "}"]
		when:
			check.visitToken(returnToken)
		then:
			1 * returnToken.getParent() >> parentToken
			1 * parentToken.getType() >> SLIST
			1 * parentToken.getLineNo() >> 1
			2 * returnToken.getLineNo() >> 4
			1 * returnToken.getColumnNo() >> 6
			1 * fileContents.getLine(2) >> "  "
			1 * fileContents.getLines() >> lines
			1 * localizedMessages.add(_) >> { LocalizedMessage message ->
				assert message.getLineNo() == 4
				assert message.getColumnNo() == 7
				assert message.getMessage() == "Empty string must be before return"
				assert message.getSourceName() == SOURCE_NAME
			}
			0 * _
	}

	def "check log fail when return is have not empty line before"() {
		setup:
			def lines = ["{", "anyStringAnyString", "anyStringAnyString", "return 2;", "}"]
		when:
			check.visitToken(returnToken)
		then:
			1 * returnToken.getParent() >> parentToken
			1 * parentToken.getType() >> SLIST
			1 * parentToken.getLineNo() >> 1
			2 * returnToken.getLineNo() >> 4
			1 * returnToken.getColumnNo() >> 6
			1 * fileContents.getLine(2) >> "anyStringAnyString"
			1 * fileContents.getLines() >> lines
			1 * localizedMessages.add(_) >> { LocalizedMessage message ->
				assert message.getLineNo() == 4
				assert message.getColumnNo() == 7
				assert message.getMessage() == "Empty string must be before return"
				assert message.getSourceName() == SOURCE_NAME
			}
			0 * _
	}

	def "check auto fixing when return is have not empty line before"() {
		setup:
			def fileName = "any file name"
			check.setAutoFixing(true)
		when:
			check.visitToken(returnToken)
		then:
			1 * returnToken.getParent() >> parentToken
			1 * parentToken.getType() >> SLIST
			1 * parentToken.getLineNo() >> 1
			1 * returnToken.getLineNo() >> 4
			1 * fileContents.getLine(2) >> "  "
			1 * fileContents.getFileName() >> fileName
			0 * _
	}

}
