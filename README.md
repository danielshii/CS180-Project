# Social Media Posting Application

## Table of Contents
1. *Project Overview*
2. *Classes*
3. *Testing*
4. *Contributers*

## Project Overview
For our project, we decided to go with option one and make a social media posting application. This application includes the ability to create users and sign in to a specific user. The user can then create a post or comment on a post, and all of the posts from a specific user can be accessed. The user can also delete posts and comments, and the application is fully interactive, using a graphical user interface (GUI) to interact with the user. On top of this, data is saved for users, and testing is done to ensure that the program never crashes and can handle anything the user may throw at it.

## Classes
* **ActionType**
  * A class of enumerations used to define what is currently being done by the user in the program
* **ObjectType**
  * A class of enumerations used to define the current folder that the program is in (user, comment, or post)
* **CommentNotFoundException**
  * Class to throw an exception if the comment is not found by the program
* **DuplicateUsernameException**
  * Class to throw an exception if the username the user is trying to create is already in use
* **InvalidObjectTypeException**
  * Class to throw an exception if the object is not of a valid type
* **InvalidUserException**
  * Class to throw an exception if the username or password that the user entered is incorrect
* **PostNotFoundException**
  * Class to throw an exception if the post is not found by the program
* **UserNotAuthorizedException**
  * Class to throw an exception if the user is not authorized to make changes to a post or comment
* **UserNotFoundException**
  * Class to throw an exception if the user inputted is not found
* **Comment**
  * Class representing a comment
* **User**
  * Class representing a user including a username and a password
* **Post**
  * Class representing a post containing time stamp, author, and list of comments
* **CommentService**
  * An interface that the program uses to organize all comments
* **UserService**
  * An interface that the program uses to organize all users
* **PostService**
  * An interface that the program uses to organize all posts
* **FileService**
  * An interface that the program uses to organize all files created for users, posts, and comments
* **CommentServiceImpl
  * A class implementing CommentService to be able to create, look through, and edit all comments created by users
* **UserServiceImpl**
  * A class implementing UserService to be able to create, access, and delete all users (and their comments and posts)
* **PostServiceImpl**
  * A class implementing PostService to be able to create, look through, and edit all posts created by users
* **FileServiceImpl**
  * A class implementing FileService to be able to jump through the post, user, and comment files and perform actions with these files
* **ClientApp**
  * A client that represents each user and sets up the entire GUI for the user to interact with
* **ServerApp**
  * A server that allows the program to handle many users, and depending on user input, determines the current action of the user and performs the action
  
## Testing
* For **CommentServiceImpl**
  * Ensures that the fields are empty
  * Ensures that all methods exist
  * Ensures comments are fetched from the proper post
  * Ensures comments are able to be created successfully
  * Ensures comments are deleted successfully
  * Ensures comments are stored properly
  * Ensures comments are edited correctly and a UserNotAuthorizedException is thrown in the proper case
  * Ensures the proper comments are fetched for the proper user
* For **CommentService**
  * Ensures that the fields are empty
  * Ensures that all methods exist
* For **FileServiceImpl**
  * Ensures that the fields are empty
  * Ensures that all methods exist
  * Ensures that files are fetched properly
  * Ensures that a created user is successfully saved to a file
  * Ensures that a created post is successfully saved to a file
  * Ensures that a created comment is successfully saved to a file
  * Ensures that files are deleted properly
* For **FileService**
  * Ensures that the fields are empty
  * Ensures that all methods exist
* For **PostServiceImpl**
  * Ensures that the fields are empty
  * Ensures that all methods exist
  * Ensures that a post is successfully created
  * Ensures that posts are fetched properly
  * Ensures that posts are deleted properly
  * Ensures that all posts are fetched from specific users properly
  * Ensures posts are edited correctly and a UserNotAuthorizedException is thrown in the proper case
* For **PostService**
  * Ensures that all the fields are empty
  * Ensures that all methods exist
* For **UserServiceImpl**
  * Ensures that all the fields are empty
  * Ensures that all methods exist
  * Ensures that a user is created successfully and a DuplicateUserException is thrown in the proper case
  * Ensures that a user can login successfully and an InvalidUserException is thrown in the proper case
  * Ensures that users can be fetched successfully and a UserNotFoundException is thrown in the proper case
  * Ensures that users are deleted properly
* For **UserService**
  * Ensures that all the fields are empty
  * Ensures that all methods exist
* For **Comment**
  * Ensures that the class exists and inherits from Object
  * Ensures that all the fields are correct
* For **User**
  * Ensures that the class exists and inherits from Object
  * Ensures that all the fields are correct
* For **Post**
  * Ensures that the class exists and inherits from Object
  * Ensures that all the fields are correct
* For **GUI Testing**
  1. Pressed quit to ensure the client stopped
  2. Pressed create user and created a user to ensure user creation was successful
  3. Added a post to ensure the post was successful
  4. Edited the post to ensure the post was successfully edited
  5. Pressed view comments and added a comment to ensure that the comment was successful
  6. Edited the comment to ensure the comment was successfully edited
  7. Deleted the comment to ensure the comment was successfully deleted
  8. Viewed all posts by my user to ensure a seperate post window popped up and the next and previous post buttons worked
  9. Deleted the post to ensure the post was successfully deleted
  10. Logged out and logged back in to ensure that user data was successfully stored
  11. Deleted the account to ensure the user data was successfully deleted

## Contributors
* Daniel Shi
* Connor Proudman
* Sam Rothschild
* Omar Sadiek
* Harnaaz Preet Singh
