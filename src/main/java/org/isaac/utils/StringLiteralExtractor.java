/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.isaac.utils;

/**
 * Extract all literalstrings from global-metadata.dat
 * @repo https://github.com/jozsefsallai/il2cpp-stringliteral-patcher
 * @acknowledgement jozsefsallai - Original creator (python)
 * @author O-Isaac
 */
import org.isaac.commands.CheckVerCode;

import java.io.*;
import java.util.*;

public class StringLiteralExtractor {
    private String filepath;
    private List<LookupTableEntry> lookupTable;
    private List<String> stringLiterals;

    private static final byte[] MAGIC_BYTES = { (byte) 0xAF, (byte) 0x1B, (byte) 0xB1, (byte) 0xFA };
    private static final int LOOKUP_TABLE_DEFINITION_OFFSET = 8;
    private static final int LOOKUP_TABLE_SIZE_DEFINITION_OFFSET = 12;
    private static final int STRINGLITERAL_DATA_DEFINITION_OFFSET = 16;
    private static final int STRINGLITERAL_DATA_SIZE_DEFINITION_OFFSET = 20;

    private static final MyLogger LOGGER = new MyLogger(StringLiteralExtractor.class);

    public StringLiteralExtractor(String filepath) {
        this.filepath = filepath;
        this.lookupTable = new ArrayList<>();
        this.stringLiterals = new ArrayList<>();
    }

    public StringLiteralExtractor extract() throws IOException {
        try (RandomAccessFile file = new RandomAccessFile(filepath, "r")) {
            extract(file);
        }
        return this;
    }

    private boolean isHashSha256 (String str) {
        return str != null
            && !str.equals("0000000000000000000000000000000000000000000000000000000000000000")
            && str.length() == 64
            && str.matches("^[a-fA-F0-9]{64}$");
    }
    
    public String getVerCodeString() throws NoSuchElementException {
        String verCode = null;

        for (String str : stringLiterals) {
            if (!isHashSha256(str)) continue;
            LOGGER.info("Found verCode hash (sha256): " + str + " ");
            verCode = str;
        }

        if (verCode == null) throw new NoSuchElementException("verCode");
        
        return verCode;
    }
    
    private void extract(RandomAccessFile file) throws IOException {
        ensureMagicIsValid(file);

        long lookupTableOffset = getLookupTableOffset(file);
        long lookupTableSize = getLookupTableSize(file);
        long stringLiteralDataOffset = getStringLiteralDataOffset(file);
        long stringLiteralDataSize = getStringLiteralDataSize(file);

        extractLookupTable(file, lookupTableOffset, lookupTableSize);
        extractStringLiterals(file, stringLiteralDataOffset, stringLiteralDataSize);
    }

    private void ensureMagicIsValid(RandomAccessFile file) throws IOException {
        byte[] magic = new byte[4];
        file.readFully(magic);
        if (!Arrays.equals(magic, MAGIC_BYTES)) {
            throw new IOException("Invalid global-metadata file");
        }
    }

    private long getLookupTableOffset(RandomAccessFile file) throws IOException {
        file.seek(LOOKUP_TABLE_DEFINITION_OFFSET);
        return Integer.reverseBytes(file.readInt()) & 0xFFFFFFFFL;  // Ensure Little Endian
    }

    private long getLookupTableSize(RandomAccessFile file) throws IOException {
        file.seek(LOOKUP_TABLE_SIZE_DEFINITION_OFFSET);
        return Integer.reverseBytes(file.readInt()) & 0xFFFFFFFFL;  // Ensure Little Endian
    }

    private long getStringLiteralDataOffset(RandomAccessFile file) throws IOException {
        file.seek(STRINGLITERAL_DATA_DEFINITION_OFFSET);
        return Integer.reverseBytes(file.readInt()) & 0xFFFFFFFFL;  // Ensure Little Endian
    }

    private long getStringLiteralDataSize(RandomAccessFile file) throws IOException {
        file.seek(STRINGLITERAL_DATA_SIZE_DEFINITION_OFFSET);
        return Integer.reverseBytes(file.readInt()) & 0xFFFFFFFFL;  // Ensure Little Endian
    }

    private void extractLookupTable(RandomAccessFile file, long lookupTableOffset, long lookupTableSize) throws IOException {
        file.seek(lookupTableOffset);

        long bytesRead = 0;
        
        while (bytesRead < lookupTableSize) {
            int length = Integer.reverseBytes(file.readInt());
            int index = Integer.reverseBytes(file.readInt());
            addLookupTableEntry(length, index);
            bytesRead += 8;
        }
    }

    private void extractStringLiterals(RandomAccessFile file, long stringLiteralDataOffset, long stringLiteralDataSize) throws IOException {
        for (int idx = 0; idx < lookupTable.size(); idx++) {
            LookupTableEntry entry = lookupTable.get(idx);
            file.seek(stringLiteralDataOffset + entry.getIndex());
            byte[] literalBytes = new byte[entry.getLength()];
            file.readFully(literalBytes);
            String literal = new String(literalBytes, "UTF-8");
            addStringLiteral(idx, literal);
        }
    }

    private void addLookupTableEntry(int length, int index) {
        lookupTable.add(new LookupTableEntry(length, index));
    }

    private void addStringLiteral(int idx, String literal) {
        stringLiterals.add(literal);
    }

    private static class LookupTableEntry {
        private int length;
        private int index;

        public LookupTableEntry(int length, int index) {
            this.length = length;
            this.index = index;
        }

        public int getLength() {
            return length;
        }

        public int getIndex() {
            return index;
        }
    }
}
