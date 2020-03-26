package simpleSearchEngine;

import interfaces.IndexEntry;
import java.util.List;
import java.util.stream.Collectors;

public class Starter {
    public static void main(String[] args) {

        Engine engine = new Engine("Dokument 1", "the brown fox jumped over the brown dog");
        engine.indexDocument("Dokument 2", "the lazy brown dog sat in the corner");
        engine.indexDocument("Dokument 3", "the red fox bit the lazy dog");

        List<IndexEntry> result =  engine.search("brown");
    }
}
