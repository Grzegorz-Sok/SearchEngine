package simpleSearchEngine;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import interfaces.IndexEntry;
import interfaces.SearchEngine;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Engine implements SearchEngine {

    public Engine (String id, String content){
        indexDocument(id, content);
    }

    public Engine(){}

    private Multimap<String, Index> multimap = ArrayListMultimap.create();

    public Multimap<String, Index> getMultimap() {
        return multimap;
    }

    @Override
    public void indexDocument(String id, String content) {
        Scanner scanner = new Scanner(content);
        String[] words = scanner.nextLine().split(" ");

        for(int i=0;i<words.length;i++) {
            if (multimap.containsKey(words[i]) && matchId(multimap, id, words, i)) {
                for (Index index : multimap.get(words[i]))
                    if (index.getId()==id)
                        index.increase();
            }
            else {
                Index index = new Index(id);
                multimap.put(words[i], index);
            }
        }
    }

    @Override
    public List<IndexEntry> search(String term) {
        List<IndexEntry> list = new ArrayList<>();
        double idf, tfidf;
        idf = idfCalculator(multimap, term);

        for (Index index : multimap.get(term)){
            tfidf = tfidfCalculator(index, idf);
            index.setScore(tfidf);
            list.add(index);
        }
        list = sortList(list);
        return list;
    }
    private boolean matchId (Multimap<String, Index> multimap, String id, String[] words, int i){
        return multimap
                .get(words[i])
                .stream()
                .anyMatch(index -> index.getId() == id);
    }
    private double Ncalculator (Multimap<String, Index> multimap){
        return multimap
                .values()
                .stream()
                .map(Index::getId)
                .distinct()
                .count();
    }
    private double DFcalculator (Multimap<String, Index> multimap, String term){
        return multimap
                .get(term)
                .stream()
                .count();
    }
    private List<IndexEntry> sortList (List<IndexEntry> list){
        return list
                .stream()
                .sorted(Comparator.comparingDouble(IndexEntry::getScore))
                .collect(Collectors.toList());
    }
    private double idfCalculator(Multimap<String, Index> multimap, String term){
        double N = Ncalculator(multimap);
        double DF = DFcalculator(multimap, term);
        //importance of term in all docs
        //log2(all docs / docs containing term)
        //log2(max id / nr of term in multimap)
        return Math.log(N/DF);
    }
    private double tfidfCalculator (Index index, double idf){
        //nr of occurences of term in doc
        double tf = index.getOccurence();
        return tf * idf;
    }
}
