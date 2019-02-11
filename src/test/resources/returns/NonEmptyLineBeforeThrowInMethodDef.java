package ru.pva.code.style.checks;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class EmptyLine extends AbstractCheck {

	public int errorWhenNotEmptyLineBeforeThrow() {
		System.out.println("errorWhenNotEmptyLineBeforeReturn");
		System.out.println("errorWhenNotEmptyLineBeforeReturn");
		throw new Exception();
	}
}
