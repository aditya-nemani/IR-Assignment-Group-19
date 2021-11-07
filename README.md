## Implementing and Evaluating Information Retrieval Models
This repo is for project work of course work CS 6200 Information Retreival Systems at Northeastern University. The project implements information retrieval methods like cleaning, indexing, stemming, query enhancement. It also implements various document search models like BM25, TF-IDF, Query Likelihood Model along with Lucene. It uses CACM as corpus.


### General Layout

The code is divided into multiple functional packages.

 1. cleaner : handles cleaning logic.
 2. indexer: handles indexing logic based on cleaned corpus.
 3. retriever: implements various document retreival algorithms.
 4. stemmer: handles stemming task
 5. utils: general purpose functions.
 6. evaluation: performs evaluation uisng metrics like Precision, Recall, MAP, MRR etc. on retreived documents for model.

### Compiling and Running Program

**Creating cleaned corpus and index files.**
- Import the project in IntelliJ or Eclipse
- To generate the cleaned corpus, run Cleaner.java in cleaner package. This will generate a folder under `src/main/resources/testcollection/cleanedcorpus` folder.
- To generate the index user Indexer.java. StemmedIndexer.java can be used to generate index of stemmed version of CACM corpus.


**Running project tasks**

 - Every task in project can be run using a command line flag in Runner.java.
 - Run `Runner.java#main()` method in `retreivalmodels` package.
 - Run Options `usage: Retreival Model: -taskName <arg>`   
  - task to run - [can be one of the __TASK1__, __TASK2__ or __TASK3__,
      __PHASE1__, __PHASE2__, __noiseGeneration__, __softMatching__]
      

`
### Key Terms
 Lucene, Query Language Model, Noise Generation, Soft Matching`
 

### Contributions
NEMANI ADITYA SARMA (2020mt12259)
NALLAGONDA LAKSHMI ADITHYA (2020MT12342)
MUCHERLA SUDHEER KUMAR(2019HT12196)
MANISH SHARMA (2020MT12407)
MANJUSHREE A (2019HT13252)


#   I R - A s s i g n m e n t - G r o u p - 1 9  
 