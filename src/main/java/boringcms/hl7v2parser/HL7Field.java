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

import java.util.Arrays;

class HL7Field {
    private static final int MAX_LEVEL = 3;
    /**
     * MSH, FHS, BHS,...
     */
    String segment;
    /**
     * field, component, subcomponent
     */
    Integer[] parts;
    /**
     * segment, field, component or subcomponent
     */
    private int level;

    HL7Field(String segment) {
        super();
        this.segment = segment;
        parts = new Integer[MAX_LEVEL];
    }

    HL7Field(HL7Field other, boolean deeper) {
        this.level = other.level;
        this.segment = other.segment;
        this.parts = Arrays.copyOf(other.parts, other.parts.length);

        if (deeper) {
            parts[level] = level == 0 ? 0 : 1;
            level++;
        } else {

            for (int i = 0; i < level; i++) {
                parts[i]++;
            }

            for (int i = level; i < parts.length; i++) {
                parts[i] = null;
            }

        }

    }

    @Override
    public String toString() {
        StringBuilder fieldName = new StringBuilder(segment);

        for (Integer integer : parts) {

            if (integer == null) {
                break;
            } else {
                fieldName.append('.').append(integer);
            }

        }

        return fieldName.toString();
    }

}
