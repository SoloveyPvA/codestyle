package ru.pva.code.style.checks.whitespace;

import static com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_RETURN;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.LITERAL_THROW;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.SLIST;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import lombok.val;

/**
 * Checks for empty lines before `return` or `throw` if the number of lines in the code block is greater than three.
 * Set autoFixing to true for enabling auto inserting empty line before `return` or `throw`
 * Parent module: TreeWalker
 */
public class EmptyLineBeforeReturnCheck extends AbstractCheck {

	private static final int NUMBER_LINES_IN_BLOCK_WHEN_EMPTY_LINE_NOT_NEED = 3;
	private static final int INDEX_OFFSET_OF_LINE_BEFORE_RETURN = 2;

	private boolean autoFixing = false;

	public void setAutoFixing(final boolean autoFixing) {
		this.autoFixing = autoFixing;
	}

	@Override
	public int[] getDefaultTokens() {
		return new int[]{
				LITERAL_RETURN,
				LITERAL_THROW
		};
	}

	@Override
	public int[] getAcceptableTokens() {
		return new int[]{
				SLIST,
				LITERAL_RETURN,
				LITERAL_THROW
		};
	}

	@Override
	public int[] getRequiredTokens() {
		return new int[0];
	}

	@Override
	public void visitToken(final DetailAST returnToken) {
		val block = returnToken.getParent();
		if (block == null || block.getType() != SLIST) {
			return;
		}

		val blockLineNumber = block.getLineNo();
		val returnLineNumber = returnToken.getLineNo();

		if ((returnLineNumber - blockLineNumber) < NUMBER_LINES_IN_BLOCK_WHEN_EMPTY_LINE_NOT_NEED) {
			return;
		}

		if (!isThereEmptyLineBeforeReturn(returnLineNumber)) {
			if (autoFixing) {
				this.insertEmptyLine(returnToken);
				return;
			}

			log(returnToken.getLineNo(), returnToken.getColumnNo(), "Empty string must be before return");
		}
	}

	private boolean isThereEmptyLineBeforeReturn(final int returnLineNumber) {
		return isEmpty(getLine(returnLineNumber - INDEX_OFFSET_OF_LINE_BEFORE_RETURN));
	}

	private void insertEmptyLine(final DetailAST returnToken) {
		EmptyLineBeforeReturnFix.insertEmptyLineBeforeToken(returnToken, this.getFileContents().getFileName());
	}
}
