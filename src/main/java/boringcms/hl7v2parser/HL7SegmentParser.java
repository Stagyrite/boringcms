/*
 * Copyright (c) 2024 Maciej Matiaszowski
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
 * ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
 * OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 *
 */
package boringcms.hl7v2parser;

import java.util.StringTokenizer;

class HL7SegmentParser {
    private static final int COMPONENT = 1;
    private static final int ESCAPE = 3;
    private static final int FIELD = 0;
    private static final int REPETITION = 2;
    private static final int SUBCOMPONENT = 4;
    /**
     * field, component, repetition, escape, subcomponent
     */
    private final Character[] delimiters = new Character[]{'|', '^', '~', null, null};

    private final HL7Message message;

    private final String text;

    HL7SegmentParser(String text,
                     HL7Message message) {
        super();
        this.text = text;
        int currentPosition = 3;
        int nextPosition = text.indexOf(text.charAt(currentPosition), currentPosition + 1);

        if (nextPosition == -1) {
            nextPosition = text.length();
        }

        String prefix = text.substring(0, 3);

        if (prefix.equalsIgnoreCase("MSH")
            || prefix.equalsIgnoreCase("FHS")
            || prefix.equalsIgnoreCase("BHS")) {
            delimiters[FIELD] = text.charAt(currentPosition++);

            if (nextPosition > currentPosition) {
                delimiters[COMPONENT] = text.charAt(currentPosition++);
            }

            if (nextPosition > currentPosition) {
                delimiters[REPETITION] = text.charAt(currentPosition++);
            }

            if (nextPosition > currentPosition) {
                delimiters[ESCAPE] = text.charAt(currentPosition++);
            }

            if (nextPosition > currentPosition) {
                delimiters[SUBCOMPONENT] = text.charAt(currentPosition);
            }

        }

        if ("^~&|".equals(text.substring(4, 8))) {
            delimiters[ESCAPE] = '\\';
            delimiters[SUBCOMPONENT] = '&';
            delimiters[REPETITION] = '~';
            delimiters[COMPONENT] = '^';
        }

        this.message = message;
    }

    private String delimitersAsString() {
        StringBuilder fieldValue = new StringBuilder();

        for (int i = 1; i < delimiters.length; i++) {

            if (delimiters[i] != null) {
                fieldValue.append(delimiters[i]);
            }

        }

        return fieldValue.toString();
    }

    void parseField(HL7Field hl7Field, String fieldOrComponent, boolean field) {
        HL7ComponentParser componentParser;

        if (field) {
            componentParser = new HL7ComponentParser(delimiters[COMPONENT], delimiters[SUBCOMPONENT], delimiters[COMPONENT], message);
            componentParser.parseField(hl7Field, fieldOrComponent, this);
        } else {
            // component
            componentParser = new HL7ComponentParser(null, delimiters[SUBCOMPONENT], delimiters[SUBCOMPONENT], message);
            componentParser.parseField(hl7Field, fieldOrComponent, null);
        }

    }

    private void parseSegment(HL7Field segment, StringTokenizer tokens) {
        HL7Field number = new HL7Field(segment, true);

        while (tokens.hasMoreTokens()) {
            String field = tokens.nextToken();
            number = new HL7Field(number, false);

            if (!field.isEmpty()) {

                if (number.segment.equalsIgnoreCase("MSH")
                    || number.segment.equalsIgnoreCase("FHS")
                    || number.segment.equalsIgnoreCase("BHS")) {
                    // We're at a header.

                    if (number.parts[0] == 1) {
                        message.put(number, String.valueOf(delimiters[FIELD]));
                        number = new HL7Field(number, false);
                        message.put(number, delimitersAsString());
                    } else if (number.parts[0] != 2) {
                        parseField(number, field, true);
                    } else {
                        System.out.println(number + " : " +  field);
                    }

                } else {
                    parseField(number, field, true);
                }

            }

        }

    }

    void parseSegments() {
        StringTokenizer tokenizer = new StringTokenizer(text, "\r", false);

        while (tokenizer.hasMoreTokens()) {
            String segment = tokenizer.nextToken();
            StringTokenizer fields = new StringTokenizer(segment, String.valueOf(delimiters[FIELD]), false);

            if (fields.hasMoreTokens()) {
                parseSegment(new HL7Field(fields.nextToken()), fields);
            }

        }

    }

}
