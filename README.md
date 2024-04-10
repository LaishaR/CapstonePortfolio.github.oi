# My Capstone Project
This github.io website contains the capstone project for my CS-499 class at Southern New Hampshire University. The assignment was to take a final project from a previous class and enhance it in three areas: software design, data structures and algorithms, and databases.

# About me 
In the Fall of 2021, I started my journey in the Computer Science program while pursuing my biology degree. In the spring of 2021, despite graduating holding an associate degree in biology, I made the bold decision to switch majors and schools, I switched my major to Computer science with a minor in Software Engineering. I was driven by the belief in the importance of technology and programming. Despite being relatively new to the program, my enthusiasm and dedication have propelled me forward, eagerly embracing every opportunity to expand my knowledge and skills in this dynamic field. I’ve always aspired to make a positive impact in the world, and with technology playing an increasingly vital role in our daily lives, I’m determined to be a part of that transformative change.

#	What have you learned while in the program? List three of the most important concepts or skills you have learned.
Throughout my academic journey in the program, I have gained valuable knowledge and skills in various aspects of computer science and software engineering. Three of the most important concepts or skills I have learned include:
- Mobile Application Development: I have learned how to design, develop, and deploy mobile applications using languages such as Java and tools like the Android SDK. This includes understanding mobile-specific design patterns, user interface development, and integration of features such as location services and sensors.
-  Algorithm Design and Analysis: I have acquired a solid understanding of fundamental algorithms and data structures, as well as techniques for designing efficient algorithms to solve complex problems. This includes topics such as sorting algorithms, graph algorithms, dynamic programming, and algorithmic complexity analysis.
-  Database Management: I have learned about relational database management systems (RDBMS) and SQL, including database design, normalization, querying, and optimization. Additionally, I have gained experience in integrating databases into software applications to persistently store and manage data.

# Discuss the specific skills you aim to demonstrate through your enhancements to reach each of the course outcomes.
For each of the course outcomes, I aim to demonstrate the following specific skills through my enhancements:
-	Employ strategies for building collaborative environments: I will demonstrate collaboration skills by effectively communicating with team members, incorporating feedback, and coordinating tasks to achieve project goals. This will be evident in how I engage with stakeholders and solicit input during the enhancement process.
-	Design and evaluate computing solutions: Through enhancements such as integrating barcode scanning and implementing advanced database functionality, I will showcase skills in problem-solving, critical thinking, and evaluating alternative solutions. I will also manage trade-offs in design choices, considering factors such as performance, usability, and scalability.
-	Demonstrate an ability to use well-founded and innovative techniques: I will showcase proficiency in utilizing well-founded techniques such as algorithm design principles and software engineering best practices. Additionally, I will demonstrate innovation by incorporating advanced features and exploring cutting-edge technologies to enhance the functionality and efficiency of the Inventory App.


# How do the specific skills you will demonstrate align with your career plans related to your degree?
The specific skills I aim to demonstrate align closely with my career plans in software development and engineering. By showcasing proficiency in mobile application development, algorithm design, database management, and software engineering principles, I am positioning myself for roles that involve designing, developing, and maintaining software solutions for mobile platforms or enterprise applications.

# How does this contribute to the specialization you are targeting for your career?
The skills I will demonstrate contribute directly to the specialization I am targeting in software development, particularly in the mobile and database domains. By enhancing the Inventory App with advanced features such as barcode scanning and data mining, I am not only broadening my skill set but also deepening my expertise in areas that are highly relevant to my career specialization. This will make me a competitive candidate for roles that require expertise in mobile app development, algorithm design, and database management.

# Code Review 
[Publication](https://youtu.be/QwXk6BVkTvo)

# INVENTORY APP BEFORE
[Inventory App Add Items |](/assets/AddItem.png)
[Inventory App All Items |](/assets/AllItems.png)
[Inventory App Change Password |](/assets/ChangePassword.png)
[Inventory App Create Account |](/assets/CreatAccount.png)
[Inventory App Dashboard Screen |](/assets/DashboardScreen.png)
[Inventory App Edit Items |](/assets/EditItem.png)
[Inventory App Forgot Password |](/assets/ForgotPassword.png)
[Inventory App Login Screen |](/assets/LoginScreen.png)
[Inventory Stock History |](/assets/StockHistory.png)
[Inventory User Profile |](/assets/UserProfile.png)

# ENCHANCEMENT ONE: SOFTWARE ENGINEERING AND DESIGN
[Inventory App Add Item Barcode |](/assets/AddItemBarcode.png)
The artifact in category one is an Android application called "Inventory App" It was created during my mobile architecture and programming course (CS 360) to serve as a tool for managing inventory efficiently. The application allows users to "Add new items" to their inventory, including capturing item details and images through the device's camera, and storing them in a SQLite database. I selected this artifact for inclusion in my ePortfolio because it demonstrates my skills and abilities in Android app development, particularly in implementing user interfaces, handling 
permissions, working with databases, and integrating third-party libraries. The code showcases my proficiency in Java programming, understanding of Android SDK components, and knowledge of software engineering principles.
The artifact was improved by integrating a barcode scanning feature using the ZXing library. This enhancement adds functionality to the app by enabling users to scan barcodes to input item details quickly. Additionally, proper permissions handling for camera and storage access was implemented to ensure a smooth user experience. With this enhancement, I met the course objectives of demonstrating proficiency in error 
handling, code refactoring, optimization, and input validation. The addition of barcode scanning aligns with the objective of streamlining the process of adding items to the inventory, enhancing 
the app's functionality and user experience. During the process of enhancing and modifying the artifact, I learned the importance of thorough testing and error handling, especially when integrating third-party libraries. I also 
gained experience in optimizing code for performance and refactoring to improve readability and maintainability. Challenges I faced included managing permissions properly and ensuring compatibility with different Android versions and devices. However, through experimentation and research, I was able to overcome these challenges and successfully implement the desired features. Overall, the process of enhancing and modifying the artifact was a valuable learning experience that enhanced my skills

# ENCHANCEMENT TWO: ALGORITHMS AND DATA STRUCTURES 
[Inventory App All Items Updated |](/assets/AllItemsUpdated.jpg)
[Inventory App All Items Search |](/assets/AllItemsSearch.jpeg)
The artifact in the data structures and algorithms category two is an Android Inventory application, specifically the "AllItems" activity of an inventory management app. This activity enables users to browse all items in the inventory and search for specific ones using a search bar. It was developed during my mobile architecture and programming course (CS 360) to streamline inventory management. The app allows users to add new items to their inventory, including 
capturing item details and images via the device's camera, and storing them in an SQLite database. I chose to include this artifact in my ePortfolio because it showcases my skills in 
implementing algorithms and data structures in Android software development. Enhancing the search functionality with a binary search algorithm demonstrates my proficiency in algorithm design and optimization for efficient search operations. Additionally, incorporating error handling mechanisms reflects my understanding of robust software development practices.
The artifact was improved by implementing an optimized search functionality using a binary search algorithm, significantly boosting the efficiency of item searches within the inventory. This enhancement aligns with the course objectives focused on algorithms and data structures. By optimizing the search functionality with a binary search algorithm, I showcased proficiency in algorithm design and implementation, specifically in real-world application 
development. Moreover, including error handling mechanisms underscores my commitment to ensuring robustness in software systems. Throughout the process of enhancing and modifying the artifact, I gained valuable 
insights into the practical application of algorithms and data structures in software development. Implementing the binary search algorithm within the Android application context required careful consideration of efficiency and integration with existing code. Challenges included ensuring compatibility with the RecyclerView setup and addressing edge cases in the search functionality. Overall, this experience reinforced the importance of algorithmic efficiency and 
robustness in software development, particularly within the constraints of mobile applications

# ENHANCEMENT THREE: DATABASES
  The artifact in category three is the DBHelper class, which manages database operations for the Inventory App. It includes methods for creating, updating, deleting, and querying database tables for users and items. Additionally, it now includes placeholders for data mining integration methods such as analyzing inventory trends, generating sales forecasts, and recommending restocking strategies.
  The DBHelper class was initially created as part of the Inventory App development process. It was created during a mobile architecture and programming course (CS 360) to serve as a tool for managing inventory efficiently. The application allows users to add new items to their inventory, including capturing item details and images through the device’s camera, and storing them in a SQLite database.
  The inclusion of the DBHelper class in my ePortfolio is justified because it demonstrates my proficiency in database management and integration within Android applications. I selected this artifact because it showcases my skills in software development, particularly in designing and implementing database schemas, performing CRUD operations, and integrating advanced functionalities like data mining techniques. The enhancements made to the DBHelper class highlight my ability to adapt and extend existing codebases to incorporate new features and improve overall application functionality.
  The enhancements made to the DBHelper class align with the course objectives related to database management, data analysis, and software development. By integrating data mining techniques into the app, I have demonstrated proficiency in applying advanced database functionalities to derive insights and enhance user experience. The improvements made to the DBHelper class align with the course outcomes focused on database optimization, integration of advanced features, and development of efficient computing solutions.
  During the process of enhancing and modifying the DBHelper class, I gained valuable insights into database management techniques and data analysis methods. I learned how to integrate data mining functionalities into Android applications to extract meaningful insights from large datasets. One of the challenges I faced was ensuring the efficiency and performance of database queries, especially when dealing with large datasets. Additionally, designing and implementing data mining algorithms posed challenges in terms of complexity and resource utilization. However, through iterative development and testing, I was able to overcome these challenges and successfully implement the desired enhancements to the DBHelper class. Overall, the enhancement process provided a valuable learning experience and improved my skills in database management and software development
