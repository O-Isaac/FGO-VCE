package org.isaac;

import com.fasterxml.jackson.databind.JsonNode;

public class Comparator {
    private final JsonNode STRINGS_LITERAL;

    public Comparator(JsonNode stringLiteralNode) {
        this.STRINGS_LITERAL = stringLiteralNode;
    }

    private boolean isSha256(String str) {
        return str != null
            && !str.equals("0000000000000000000000000000000000000000000000000000000000000000")
            && str.length() == 64
            && str.matches("^[a-fA-F0-9]{64}$")
        ;
    }

    public String getVerCode() {
        String verCode = null;

        for (JsonNode element : STRINGS_LITERAL) {
            if (!isSha256(element.get("value").asText())) continue;
            verCode = element.get("value").asText();
        }

        return verCode;
    }
}
