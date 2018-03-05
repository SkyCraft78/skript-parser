package io.github.syst3ms.skriptparser.file;

import io.github.syst3ms.skriptparser.util.FileUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileParser {
    public static final Pattern LINE_PATTERN = Pattern.compile("^((?:[^#]|##)*)(\\s*#(?!#).*)$"); // Might as well take that from Skript

    public List<FileElement> parseFileLines(String fileName, List<String> lines, int expectedIndentation, int lastLine) {
        List<FileElement> elements = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            String content;
            final Matcher m = LINE_PATTERN.matcher(line);
            if (m.matches())
                content = m.group(1).replace("##", "#").trim();
            else
                content = line.replace("##", "#").trim();
            if (content.matches("\\s*"))
                continue;
            int lineIndentation = FileUtils.getIndentationLevel(line);
            if (lineIndentation > expectedIndentation) { // One indentation behind marks the end of a section
                error("Invalid indentation, expected " + expectedIndentation +
                      " indents, but found " +
                      lineIndentation);
                continue; // Let's ignore it *for now*
            } else if (lineIndentation < expectedIndentation) { // End of section
                return elements;
            }
            if (content.endsWith(":")) {
                if (i + 1 == lines.size()) {
                    elements.add(new FileSection(fileName, lastLine + i, content.substring(0, content.length() - 1), new ArrayList<>(), expectedIndentation));
                } else {
                    List<FileElement> sectionElements = parseFileLines(fileName, lines.subList(i + 1, lines.size()), expectedIndentation + 1, lastLine + i + 1);
                    elements.add(new FileSection(fileName, lastLine + i, content.substring(0, content.length() - 1), sectionElements, expectedIndentation));
                    i += sectionElements.size();
                }
            } else {
                elements.add(new SimpleFileLine(fileName, lastLine + i, content, expectedIndentation));
            }
        }
        return elements;
    }

    private void error(String error) {
        // TODO
    }

}
