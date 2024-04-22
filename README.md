# Welcome!
Hello, my name is Laisha Ramos. This GitHub.io website contains the capstone project for my CS-499 class at Southern New Hampshire University. The objective of this ePortfolio is to demonstrate skills and abilities I have learned. This ePortfolio comprises the professional self-assessment, the code review, and three enhancements of the chosen artifact that was created in my CS360 mobile architecture and programming course. The chosen artifact will correspond to demonstrated skills and abilities in the categories of software design and engineering, algorithms and data structures, and databases.

# SELF ASSESSMENT
In the Fall of 2021, I started on a transformative journey by transitioning from a biology degree to pursuing a Computer Science degree with a minor in Software Engineering. Driven by my belief in the power of technology, I embraced every opportunity to expand my knowledge and skills in this dynamic field. Despite being relatively new to this world of Computer science, my enthusiasm and dedication have propelled me forward, positioning me to make a positive impact in the world through technology. Leveraging my previous academic achievement, including holding an associate degree in biology, I've approached my computer science studies with a diverse perspective, enhancing my problem-solving abilities and fostering a deeper understanding of the intersection between technology and biology. Throughout my journey, completing coursework and developing my ePortfolio have not only showcased my strengths but also shaped my goals and values, preparing me to enter the workforce with confidence. I've gained invaluable skills in problem-solving, technical writing, programming, and project management, thanks to the comprehensive curriculum and engaging extracurricular activities. Also including exploration of various aspects of computer science, including programming, algorithms, cybersecurity, data analysis, and more, has equipped me with a well-rounded skill set and a deeper understanding of my potential to drive positive change in society.

Category One: Software Engineering and Design was the starting point for my enhancements to the Inventory App developed during my CS 360: Mobile Architecture and Programming course. Building upon the existing database integration, I optimized database queries and operations to enhance performance, particularly for data retrieval tasks. This involved implementing techniques such as indexing, query optimization, and database normalization. The optimization of database queries was crucial for improving the overall performance and responsiveness of the Inventory App, especially when dealing with large datasets.

Moving on to Category Two: Algorithms and Data Structures, I recognized the need to streamline inventory management processes further. To address this, I integrated barcode scanning functionality into the app. This enhancement allowed users to quickly and accurately add new items to the inventory by scanning their barcodes. The addition of barcode scanning technology aimed to simplify data entry tasks, reduce errors, and improve overall productivity in inventory management tasks.

Finally, for Category Three: Databases, I implemented several enhancements to address key areas of improvement. Recognizing the inefficiency of the existing search functionality, I optimized it by integrating a more efficient binary search algorithm. This improvement aimed to enhance search performance significantly, especially for larger inventories. Additionally, I introduced comprehensive error-handling mechanisms to gracefully manage exceptions during database operations, ensuring a more stable and reliable user experience. Moreover, to enhance code maintainability and readability, I refactored the codebase to improve modularity and organization.


# Aligning Skills with Career Goals in Software Development
The specific skills I aim to demonstrate closely align with my career plans in software development and engineering. Proficiency in mobile application development, algorithm design, database management, and software engineering principles positions me for roles involving designing, developing, and maintaining software solutions for mobile platforms or enterprise applications. These skills contribute directly to my specialization in software development, particularly in mobile and database domains. Enhancing the Inventory App with features like barcode scanning and data mining not only broadens my skill set but also deepens my expertise in relevant areas, making me a competitive candidate for roles requiring proficiency in mobile app development, algorithm design, and database management

# CS499 Journal 
[Journal 4 |](/Journal/CS499Journal4LaishaRamos.pdf) An Academic Reflection: Exploring Career aspirations and future prospects in the field of Computer Science, alongside Artifact updates.

[Journal 5 |](/Journal/CS499Journal5LaishaRamos.pdf) Academic Reflection: Exploring two emerging trends in the field of Computer Science, alongside Artifact Updates.

[Journal 6 |](/Journal/CS499Journal6LaishaRamos.pdf) Academic Reflection: Examining two emerging technologies in the field of Computer Science, alongside Artifact updates

# Code Review 
I conducted my first code review and quickly grasped its importance. The practice of code review is significant because it enables developers, either individually or as part of a team, to dissect their application into specific code blocks and review them for aspects such as structure, documentation, variables, arithmetic operations, loops, branches, and defensive programming. This approach, from part to whole, allows programmers to break down their code into more manageable pieces for review, ultimately leading to a more defect-free product when passed to the Quality Assurance (QA) team. This code review will analyze the original artifact and discuss the three enhancements I intend to make to each. You can find my published code review on YouTube by visiting [CODE REVIEW: INVENTORY APP](https://youtu.be/QwXk6BVkTvo)

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
[Inventory App Add Item Barcode Image](/assets/AddItemBarcode.png)


The artifact in category one is an Android application called "Inventory App." It was created during my mobile architecture and programming course (CS 360) to serve as a tool for managing inventory efficiently. The application allows users to "Add new items" to their inventory, including capturing item details and images through the device's camera, and storing them in a SQLite database. I selected this artifact for inclusion in my ePortfolio because it demonstrates my skills and abilities in Android app development, particularly in implementing user interfaces, handling permissions, working with databases, and integrating third-party libraries. The code showcases my proficiency in Java programming, understanding of Android SDK components, and knowledge of software engineering principles.

The artifact was improved by integrating a barcode scanning feature using the ZXing library. This enhancement adds functionality to the app by enabling users to scan barcodes to input item details quickly. Additionally, proper permissions handling for camera and storage access was implemented to ensure a smooth user experience. With this enhancement, I met the course objectives of demonstrating proficiency in error handling, code refactoring, optimization, and input validation. The addition of barcode scanning aligns with the objective of streamlining the process of adding items to the inventory, enhancing the app's functionality and user experience.

During the process of enhancing and modifying the artifact, I learned the importance of thorough testing and error handling, especially when integrating third-party libraries. I also gained experience in optimizing code for performance and refactoring to improve readability and maintainability. Challenges I faced included managing permissions properly and ensuring compatibility with different Android versions and devices. However, through experimentation and research, I was able to overcome these challenges and successfully implement the desired features. Overall, the process of enhancing and modifying the artifact was a valuable learning experience that enhanced my skills.

ENCHANCED CODE: [Inventory App Add Item barcode](/EnhancementOne/AddItem.java)

# ENCHANCEMENT TWO: ALGORITHMS AND DATA STRUCTURES 
[Inventory App All Items Updated |](/assets/AllItemsUpdated.jpg)
[Inventory App All Items Search |](/assets/AllItemsSearch.jpeg)

The artifact in the data structures and algorithms category is an Android Inventory application, specifically the "AllItems" activity of an inventory management app. This activity enables users to browse all items in the inventory and search for specific ones using a search bar. It was developed during my mobile architecture and programming course (CS 360) to streamline inventory management. The app allows users to add new items to their inventory, including capturing item details and images via the device's camera, and storing them in an SQLite database.

I chose to include this artifact in my ePortfolio because it showcases my skills in implementing algorithms and data structures in Android software development. Enhancing the search functionality with a binary search algorithm demonstrates my proficiency in algorithm design and optimization for efficient search operations. Additionally, incorporating error-handling mechanisms reflects my understanding of robust software development practices.

The artifact was improved by implementing an optimized search functionality using a binary search algorithm, significantly boosting the efficiency of item searches within the inventory. This enhancement aligns with the course objectives focused on algorithms and data structures. By optimizing the search functionality with a binary search algorithm, I showcased proficiency in algorithm design and implementation, specifically in real-world application development. Moreover, including error-handling mechanisms underscores my commitment to ensuring robustness in software systems.

Throughout the process of enhancing and modifying the artifact, I gained valuable insights into the practical application of algorithms and data structures in software development. Implementing the binary search algorithm within the Android application context required careful consideration of efficiency and integration with existing code. Challenges included ensuring compatibility with the RecyclerView setup and addressing edge cases in the search functionality. Overall, this experience reinforced the importance of algorithmic efficiency and robustness in software development, particularly within the constraints of mobile applications.


ENCHANCED CODE: [Inventory App All Items](/EnhancementTwo/AllItems.java)

# ENHANCEMENT THREE: DATABASES

The artifact in category three is "DBHelper", which manages database operations for the Inventory App. It includes methods for creating, updating, deleting, and querying database tables for users and items. Additionally, it now includes placeholders for data mining integration methods such as analyzing inventory trends, generating sales forecasts, and recommending restocking strategies.

The DBHelper class was initially created as part of the Inventory App development process. It was developed during a mobile architecture and programming course (CS 360) to serve as a tool for managing inventory efficiently. The application allows users to add new items to their inventory, including capturing item details and images through the device’s camera, and storing them in a SQLite database.

The inclusion of the DBHelper class in my ePortfolio is justified because it demonstrates my proficiency in database management and integration within Android applications. I selected this artifact because it showcases my skills in software development, particularly in designing and implementing database schemas, performing CRUD operations, and integrating advanced functionalities like data mining techniques. The enhancements made to the DBHelper class highlight my ability to adapt and extend existing codebases to incorporate new features and improve overall application functionality.

The enhancements made to the DBHelper class align with the course objectives related to database management, data analysis, and software development. By integrating data mining techniques into the app, I have demonstrated proficiency in applying advanced database functionalities to derive insights and enhance user experience. The improvements made to the DBHelper class align with the course outcomes focused on database optimization, integration of advanced features, and development of efficient computing solutions.

During the process of enhancing and modifying the DBHelper class, I gained valuable insights into database management techniques and data analysis methods. I learned how to integrate data mining functionalities into Android applications to extract meaningful insights from large datasets. One of the challenges I faced was ensuring the efficiency and performance of database queries, especially when dealing with large datasets. Additionally, designing and implementing data mining algorithms posed challenges in terms of complexity and resource utilization. However, through iterative development and testing, I was able to overcome these challenges and successfully implement the desired enhancements to the DBHelper class. Overall, the enhancement process provided a valuable learning experience and improved my skills in database management and software development.


  ENCHANCED CODE: [Inventory App DBHelper](/EnhancementThree/DBHelper.java)
