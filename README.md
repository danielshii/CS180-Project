# Social Media Posting Application

## Table of Contents
1. *Project Overview*
2. *Classes*
3. *Testing*
4. *Contributers*

## Project Overview
For our project, we decided to go with option one and make a social media posting application. This application includes the ability to create users and sign in to a specific user. The user can then create a post or comment on a post, and all of the posts from a specific user can be accessed. The user can also delete posts and comments, and the application is fully interactive, using a graphical user interface (GUI) to interact with the user. On top of this, data is saved for users, and testing is done to ensure that the program never crashes and can handle anything the user may throw at it.

## Classes
* ActionType
  * A class of enumerations used to define what is currently being done by the user in the program
* ObjectType
  * A class of enumerations used to define the current folder that the program is in (user, comment, or post)
* CommentNotFoundException
  * Class to throw an exception if the comment is not found by the program
* DuplicateUsernameException
  * Class to throw an exception if the username the user is trying to create is already in use
* InvalidObjectTypeException
  * Class to throw an exception if the object is not of a valid type
* InvalidUserException 
  * Class to throw an exception if the username or password that the user entered is incorrect
* PostNotFoundException
  * Class to throw an exception if the post is not found by the program
* UserNotAuthorizedException
  * Class to throw an exception if the user is not authorized to make changes to a post or comment
* UserNotFoundException
  * Class to throw an exception if the user inputted is not found
* Comment
  * Class representing a comment
* User
  * Class representing a user including a username and a password
* Post
  * Class representing a post containing time stamp, author, and list of comments
* CommentService
  * An interface that the program uses to organize all comments
* UserService
  * An interface that the program uses to organize all users
* PostService
  * An interface that the program uses to organize all posts
* FileService
  * An interface that the program uses to organize all files created for users, posts, and comments
* CommentServiceImpl
  * A class implementing CommentService to be able to create, look through, and edit all comments created by users
* UserServiceImpl
  * A class implementing UserService to be able to create, access, and delete all users (and their comments and posts)
* PostServiceImpl
  * A class implementing PostService to be able to create, look through, and edit all posts created by users
* FileServiceImpl
  * A class implementing FileService to be able to jump through the post, user, and comment files and perform actions with these files
* ClientApp
  * A client that represents each user and sets up the entire GUI for the user to interact with
* ServerApp
  * A server that allows the program to handle many users, and depending on user input, determines the current action of the user and performs the action
  
## Testing
* For CommentServiceImpl
  * Info
* For CommentService
  * Info
* For FileServiceImpl
  * Info
* For FileService
  * Info
* For PostServiceImpl
  * Info
* For PostService
  * Info
* For UserServiceImpl
  * Info
* For UserService
  * Info
* For Comment
  * Info
* For User
  * Info
* For Post
  * Info

## Contributors
* Daniel Shi
* Connor Proudman
* Sam Rothschild
* Omar Sadiek
* Harnaaz Preet Singh
