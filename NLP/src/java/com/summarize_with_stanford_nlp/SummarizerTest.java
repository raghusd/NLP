
package com.summarize_with_stanford_nlp;
import java.nio.file.*;
import java.util.List;
import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


public class SummarizerTest {
    
  public static String readFileAsString(String fileName)throws Exception 
  { 
    String data = ""; 
    data = new String(Files.readAllBytes(Paths.get(fileName))); 
    return data; 
  } 
  
  public static void main(String[] args) throws Exception 
  { 
      
    SentimentAnalyzer sentimentAnalyzer = new SentimentAnalyzer();
    sentimentAnalyzer.initialize();
    
    String summary = "";
                
    String path = "C://Users//Asus//Desktop//Simple_Text_Summarization_with_Sentiment_Analysis//texts//text01.txt";
    String path1 = "C://Users//Asus//Desktop//Simple_Text_Summarization_with_Sentiment_Analysis//texts//text02.txt";
    String data = readFileAsString(path); 
    //System.out.println(data); 
    String[] sSentence = data.split("(?<=[a-z])\\.\\s+");
    
    
    HashMap<String, Double> map = new HashMap<String, Double>();
    ValueComparator bvc = new ValueComparator(map);
    TreeMap<String, Double> sorted_map = new TreeMap<String, Double>(bvc);
    
    for(int i=0 ; i<sSentence.length; i++)
    {
        SentimentResult sentimentResult = sentimentAnalyzer.getSentimentResult(sSentence[i]);
        
        //System.out.println(sSentence[i]+ " " + sentimentResult.getSentimentType());
        
        
        double val= 0 ;
        
        if(sentimentResult.getSentimentType().equalsIgnoreCase("neutral") || sentimentResult.getSentimentType().equalsIgnoreCase("veryNegative") || sentimentResult.getSentimentType().equalsIgnoreCase("veryPositive"))
        {
            summary += sSentence[i] + ". ";
            val = (sSentence[i].length());
            map.put(sSentence[i], val);
        }
        
        
    }
    
    //System.out.println("unsorted map: " + map);
    //sorted_map.putAll(map);
    //System.out.println("results: " + sorted_map);
    
      System.out.println(summary);
      
      System.out.println("Compression percentage : "+(100*summary.length())/data.length());
      
    BufferedWriter writer = new BufferedWriter(new FileWriter(path1));
    writer.write(summary);
    writer.close();
      
    
    
    
    
  } 
}

class ValueComparator implements Comparator<String> {
    Map<String, Double> base;

    public ValueComparator(Map<String, Double> base) {
        this.base = base;
    }

    // Note: this comparator imposes orderings that are inconsistent with
    // equals.
    public int compare(String a, String b) {
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        } // returning 0 would merge keys
    }
}
