package com.ir.project.extracredit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.io.FileWriter;
import org.jsoup.Jsoup;
import com.ir.project.retrievalmodel.Runner;
import com.ir.project.utils.SearchQuery;
import com.ir.project.utils.Utilities;

import org.jsoup.nodes.Document;
// TODO: Auto-generated Javadoc

/**
 * The Class ECTask1.
 */
public class ECTask1 {
   
   /**
    * Make bucket.
    *
    * @param query the query
    * @return the string
    */
   public static String makeBucket(String query){
      
      String tempQuery = query.toLowerCase();
      List<String> bucket = new ArrayList<>();
      String[] queryWords = query.toLowerCase().split(" ");
      for (int i = 0; i < queryWords.length; i++) {
         queryWords[i] = Utilities.processedWord(queryWords[i]);
      }
      //finding the smallest word
      String smallest = queryWords[0];
      for(String word : queryWords){
         smallest = smallest.length() > word.length() ? word : smallest;
      }
      //making the bucket based on the length of the words, in comparison to the smallest one.
      bucket.add(smallest);
      for(String word : queryWords){
         for(int i = 0 ; i < word.length() - smallest.length(); i++ ){
            bucket.add(word);
         }
      }
      //picking up a random number from 0 to size of the bucket without replacement i.e. same word cannot be chosen again.
      //doing this till we 40% words of the query.
      int queryLength = queryWords.length;
      for(int i = 0; i < 0.4 * queryLength; i++ ){
         int max = bucket.size() - 1, min = 0;
         int index = (int) ((Math.random() * ((max - min) + 1)) + min);
         String chosenWord = bucket.get(index);
         //now removing this word from the bucket so that it's not chosen again.
         while (bucket.contains(chosenWord)){
            bucket.remove(chosenWord);
         }
         query = query.replaceAll(chosenWord,"");
         //shuffling the letters of the chosen word.
         String toReplace = shuffle(chosenWord).get(chosenWord);
         tempQuery = tempQuery.replace(chosenWord, toReplace);

      }          

      return tempQuery;

   }
   //citation -> https://www.quickprogrammingtips.com/java/how-to-shuffle-a-word-in-java.html
   /**
    * Shuffles a given word. Randomly swaps characters 10 times.
    *
    * @param word the word
    * @return the map
    */
   private static Map<String, String> shuffle(String word) {
      Map shuffleMap = new HashMap<>();
      if(word.length() < 4) {
         shuffleMap.put(word, word);
         return shuffleMap;
      }
      String shuffledWord = word; // start with original
      int wordSize = word.length();
      int shuffleCount = 5; // let us randomly shuffle letters 10 times
      for(int i=0;i<shuffleCount;i++) {
         //swap letters in two indexes
         int position1 = ThreadLocalRandom.current().nextInt(1, wordSize - 1);
         int position2 = ThreadLocalRandom.current().nextInt(1, wordSize - 1);
         shuffledWord = swapCharacters(shuffledWord,position1,position2);
      }
//    if(shuffledWord.equals(word)) {
//       shuffledWord = shuffle(word).get(word);
//    }
      shuffleMap.put(word, shuffledWord);
      return shuffleMap;
   }

   /**
    * Swaps characters in a string using the given character positions.
    *
    * @param shuffledWord the shuffled word
    * @param position1 the position 1
    * @param position2 the position 2
    * @return the string
    */
   private static String swapCharacters(String shuffledWord, int position1, int position2) {
      char[] charArray = shuffledWord.toCharArray();
      char temp = charArray[position1];
      charArray[position1] = charArray[position2];
      charArray[position2] = temp;
      return new String(charArray);
   }
   
   /**
    * Return noisy search query.
    *
    * @param queries the queries
    * @return the list
    * @throws IOException Signals that an I/O exception has occurred.
    */
   public static List<SearchQuery> returnNoisySearchQuery(List<SearchQuery> queries) throws IOException{
   
      List<SearchQuery> noisyQuery=new ArrayList<SearchQuery>();
      
      for(SearchQuery s: queries) {
         String tempFilteredQuery = makeBucket(s.getQuery());
          s.setQuery(tempFilteredQuery); 
          
      }  
      
      return queries;
   }
   
   
}