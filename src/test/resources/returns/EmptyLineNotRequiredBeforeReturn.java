package ru.pva.code.style.checks;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class EmptyLine extends AbstractCheck {

	public void returnVoid() {
		return;
	}

	public void returnVoidWithBlock() {
		if (true) {
			return;
		}

		if (true) {
			System.out.println("lineNumberInMethodLessThenThree");
			return;
		}


		for (int i : emptyList()) {
			return;
		}

		for (int i : emptyList()) {
			System.out.println("lineNumberInMethodLessThenThree");
			return;
		}

		for (int i = 0; i < 2; i++) {
			return;
		}

		for (int i = 0; i < 2; i++) {
			System.out.println("lineNumberInMethodLessThenThree");
			return;
		}
	}

	public void notLiteralReturn() {
		System.out.println("notLiteralReturn");
	}

	public int lineNumberInMethodLessThenThree() {
		System.out.println("lineNumberInMethodLessThenThree");
		return 1;
	}

	public int ifBlockDefine() {
		System.out.println("lineNumberInMethodLessThenThree");

		if (true) {
			return 1;
		}

		if (true) {
			System.out.println("lineNumberInMethodLessThenThree");
			return 1;
		}
	}

	public int forBlockDefine() {
		System.out.println("lineNumberInMethodLessThenThree");

		for (int i : emptyList()) {
			return 1;
		}

		for (int i : emptyList()) {
			System.out.println("lineNumberInMethodLessThenThree");
			return 1;
		}

		for (int i = 0; i < 2; i++) {
			return 1;
		}

		for (int i = 0; i < 2; i++) {
			System.out.println("lineNumberInMethodLessThenThree");
			return 1;
		}
	}
}