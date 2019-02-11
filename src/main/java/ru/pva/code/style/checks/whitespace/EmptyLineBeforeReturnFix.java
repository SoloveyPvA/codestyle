package ru.pva.code.style.checks.whitespace;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.lang3.StringUtils.isBlank;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

@Slf4j
class EmptyLineBeforeReturnFix {

	private static final String EMPTY_STRING = "";

	private EmptyLineBeforeReturnFix() {
	}

	static void insertEmptyLineBeforeToken(@NonNull final DetailAST token, @NonNull final String filePath) {
		try {
			val path = Paths.get(filePath);
			val lines = Files.readAllLines(path, UTF_8);

			if (isBlank(lines.get(token.getLineNo() - 2))) {
				lines.set(token.getLineNo() - 2, EMPTY_STRING);
			} else {
				lines.add(token.getLineNo() - 1, EMPTY_STRING);
			}

			Files.write(path, lines, UTF_8);
		} catch (final IOException e) {
			LOGGER.error("Can't insert empty line before token {}", token, e);
		}
	}
}