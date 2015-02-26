
The Evaluation_Task1.xlsx(/Project_Information_Retrieval/Task 1/Evaluation/Evaluation_Task1.xlsx) in the Evaluation folder under Task 1 has the precision score for businesses.
The TestData_Input(/Project_Information_Retrieval/Task 1/TestData_Input/) folder has all the reviews for a particular business ID. There are in all 41 different business ID. Categories needs to be predicted for these businessId.
The TestData_Output(/Project_Information_Retrieval/Task 1/TestData_Output/) folder has all predicted categories for businesses based on tf-idf values.
The Java folder(/Project_Information_Retrieval/Task 1/Java/) under Task 1 has all the source files.
The Collection(/Project_Information_Retrieval/Task 1/Collection/) folder under Task 1 has all the text files retrieved after parsing the 'yelp_academic_dataset_review.json' and 'yelp_academic_dataset_tip.json'json file.

Java Files.

ReadBusinessDataset.java
To run the program following are the variables that need to be changed:
DB: The name for the database. (Currently test 11)
JsonParser jParser: Path for yelp_academic_dataset_business.json file.


ParseJsonOnceAgain.java
It identifies the reviews of a business whose one of the categories is Restaurants by parsing the 'yelp_academic_dataset_review' and 'yelp_academic_dataset_tip' file.
It then stores the business id  and reviews for the business in the text file based on the hash value.
To run the program following are the variables that need to be changed:
POSModel model: The path for en-pos-maxent.bin needs to be set in the model variable. This variable is set in the static block.
FileReader(getBusinessList()): Contains the file path of 'yelp_academic_dataset_business.json'.
String fileName(main method): Contains the path of the output text file.
FileReader(main method): Contains the path for business and tip file.

ReadBusinessDatasetForCategory.java
It identifies all the categories for a business by parsing the 'yelp_academic_dataset_business' file.
It then stores the business id  and the attributes for the business in the 'yelp_academic_dataset_business_attributes' collection.
To run the program following are the variables that need to be changed:
JsonParser jParser: Contains the file path of 'yelp_academic_dataset_business.json'. 

ReadParsedFileOnceAgain.java
This file is used to read the data from the text file stored in the data folder.
This data is stored in hashmap with categories as key and value as hashmap with 
word as key and count as value(A hashmap within a hashmap-- HashMap<String,HashMap<String,Integer>)
This hashmap is traversed wherein a collection is created based on category and 
hashmap of value is stored for that category.  
To run the program following are the variables that need to be changed:
File file[]=Contains the path of the text file.
 
RestaurantsCategories.java
It is used to identify different categories in which restaurants is one of the category for businesses.
There are in all 241 different categories
This data is stored in the 'yelp_restaurants_categories' collection.
To run the program following are the variables that need to be changed:
FileReader(Main method): Contains the file path of 'yelp_academic_dataset_business.json'.

CreateTestData.java
It identifies all the reviews for a particular businessId and stores it in a text file after extracting nouns and performing tokenization and stemming. The data processing step (noun extraction and tokenization,stemming) is same as the one did in the process while traversing the review and tip file and storing the data in the text file.
To run the program following are the variables that need to be changed:
FileReader(Main method): Contains the file path of 'yelp_academic_dataset_review.json'.
String fileName: Contains the path of the text file. 
All the reviews are then extracted from a 'yelp_academic_dataset_review.json' file for a given businessId.

PredictBusinessCategory.java
It predicts the categories for a business based on the reviews. The categories for a business is determined based on the tf-idf score across categories.
The top tf-idf values is used to determine the categories.
To run the program following are the variables that need to be changed:
FileReader(Main method): Contains the path for reviews for a particular businessId.
String fileName(Main method): Contains the file path for tf-idf score for categories.

Evaluation_Task1.xlsx
It has the evaluation score computed for 41 businesses.

TestData_Input folder:
It has all the reviews in a text file for particular businessId. These reviews are then used to identify categories for buisness.
There are in all 41 different businessId.

TestData_Output folder:
It has the sorted output based on Tf-idf score for categories identified for each business.
 
Java Folder:
All the source files.
 
Collection Folder:
It has all the text files retrieved after parsing 'yelp_academic_dataset_review.json' and 'yelp_academic_dataset_tip.json' file.