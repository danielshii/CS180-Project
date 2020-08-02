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
  * Used by client to tell the server what action to take
* ObjectType
  * Tells the file service which folder to access
* CommentNotFoundException
  * Thrown when comment can't be found
* DuplicateUsernameException
  * Thrown when a user tries to create an account with a username that is already associated with an existing account
* InvalidObjectTypeException
  * Thrown when an object is not of the valid type
* InvalidUserException
  * Thrown when a usre incorrectly enters login info
* PostNotFoundException
  * Thrown when a post cannot be found
* UserNotAuthorizedException
  * Thrown when a user tries to change a post or comment that isn't theirs
* UserNotFoundException
  * Thrown when a user isn't found
* Comment
  * Object representing a comment
* User
  * Object representing a user. Includes username and password
* Post
  * Object representing a post. Contains timestamp, author, and own list of comments
* CommentService
  * Interface for a comment service
* UserService
  * Interface for a user service
* PostService
  * Interface for a post service
* FileService
  * Interface for a file service
* CommentServiceImpl
  * Implements comment service. Controls comment files
* UserServiceImpl
  * Implements User service. Controls user files
* PostServiceImpl
  * Implements Post service. Controls post files
* FileServiceImpl
  * Implements file service. Controls all files within the program. Used by the other three services
  
## Testing

## Contributors
Daniel Shi
Sam Rothschild
Connor Proudman
Omar Sadiek
Harnaaz Preet Singh
