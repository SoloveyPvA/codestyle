package ru.pva.code.style.checks;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;

public class EmptyLine extends AbstractCheck {

	public int errorWhenNotEmptyLineBeforeReturnInIf() {
		if (true) {
			System.out.println("errorWhenNotEmptyLineBeforeReturn");
			System.out.println("errorWhenNotEmptyLineBeforeReturn");
			return 1;
		}

		return 1;
	}
}
