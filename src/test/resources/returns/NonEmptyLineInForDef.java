package ru.pva.code.style.checks;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;

public class EmptyLine extends AbstractCheck {

	public int errorWhenNotEmptyLineBeforeReturnInFor() {
		for (int i = 0; i < 2; i++) {
			System.out.println("errorWhenNotEmptyLineBeforeReturn");
			System.out.println("errorWhenNotEmptyLineBeforeReturn");
			return 1;
		}

		return 1;
	}
}
