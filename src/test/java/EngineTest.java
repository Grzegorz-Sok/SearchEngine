import interfaces.IndexEntry;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import simpleSearchEngine.Engine;
import simpleSearchEngine.Index;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EngineTest {

    @Test
    public void indexDocumentTest() {
        String id = "id", content = "test";
        Engine tester = new Engine();
        tester.indexDocument(id, content);

        assertEquals(id, tester
                .getMultimap()
                .values()
                .stream()
                .map(Index::getId)
                .collect(Collectors.joining()), "Properly added document's id");

        assertEquals(content, tester
                .getMultimap()
                .keys()
                .stream()
                .collect(Collectors.joining()), "Properly added document's content");

        content = "test test";
        id = "ID";
        tester.indexDocument(id, content);

        int test = tester
                .getMultimap()
                .values()
                .stream()
                .filter(Index -> Index.getId() == "ID")
                .map(Index::getOccurence).findFirst().get();

        assertEquals(2, test, "Properly calculated occurence");
    }

    @Test
    public void searchTest() {
        Engine tester = new Engine("id","test jeden");
        tester.indexDocument("id2","test dwa");
        List<IndexEntry> listTest = new ArrayList<>();
        IndexEntry indexTest = new Index("id");
        listTest.add(indexTest);

        assertEquals(listTest.get(0).getId(), tester.search("jeden").get(0).getId(), "Properly searched item's id");

        assertEquals(0.6931471805599453, tester.search("dwa").get(0).getScore(), "Properly searched item's score");
    }
}
