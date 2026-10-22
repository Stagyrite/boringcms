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

class HL7ComponentParser {
    private final Character componentDelimiter;
    private final String delimiter;
    private final HL7Message message;
    private final char subcomponentDelimiter;

    HL7ComponentParser(Character componentDelimiter, char subcomponentDelimiter, char delimiter, HL7Message message) {
        super();
        this.componentDelimiter = componentDelimiter;
        this.subcomponentDelimiter = subcomponentDelimiter;
        this.delimiter = String.valueOf(delimiter);
        this.message = message;
    }

    private boolean containsDelimiter(String field) {
        boolean separated = false;

        if (field != null) {
            separated = field.indexOf(subcomponentDelimiter) != -1;

            if (componentDelimiter != null && !separated) {
                separated = field.indexOf(componentDelimiter) != -1;
            }

        }

        return separated;
    }

    private void parseComponents(HL7Field field, StringTokenizer components,
                                 HL7SegmentParser parser) {
        HL7Field componentNumber = new HL7Field(field, true);

        while (components.hasMoreTokens()) {
            String component = components.nextToken();

            if (parser == null) {
                message.put(componentNumber, component);
            } else {
                parser.parseField(componentNumber, component, false);
            }

            componentNumber = new HL7Field(field, false);
        }

    }

    void parseField(HL7Field hl7Field, String field, HL7SegmentParser parser) {

        if (containsDelimiter(field)) {
            parseComponents(hl7Field, new StringTokenizer(field, delimiter, false), parser);
        } else if (componentDelimiter != null) {
            message.put(new HL7Field(hl7Field, true), field);
        } else {
            message.put(hl7Field, field);
        }

    }

}
