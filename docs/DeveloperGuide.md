# Developer Guide

![AniChan Logo](images/AniChan-Logo.png)

[![Build Status](https://travis-ci.org/AY2021S1-CS2113T-F12-2/tp.svg?branch=master)](https://travis-ci.org/AY2021S1-CS2113T-F12-2/tp) [![Open Issues](https://img.shields.io/github/issues-raw/AY2021S1-CS2113T-F12-2/tp)](https://github.com/AY2021S1-CS2113T-F12-2/tp/issues) [![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT) [![Last Commit](https://img.shields.io/github/last-commit/AY2021S1-CS2113T-F12-2/tp)](https://github.com/AY2021S1-CS2113T-F12-2/tp/commits/master) [![Codecov](https://codecov.io/gh/AY2021S1-CS2113T-F12-2/tp/branch/master/graph/badge.svg?token=2H6O4KMWRA)](https://codecov.io/gh/AY2021S1-CS2113T-F12-2/tp) [![Codacy Badge](https://app.codacy.com/project/badge/Grade/fac33332a53745f990829c425c499621)](https://www.codacy.com/gh/AY2021S1-CS2113T-F12-2/tp/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=AY2021S1-CS2113T-F12-2/tp&amp;utm_campaign=Badge_Grade)

<br/>

## Table of Content
1.  [Introduction](#1-introduction)
<br/>&nbsp;1.1 [Purpose](#11-purpose)

2.  [Setting up](#2-setting-up)
3.  [Design](#3-design)
<br/>&nbsp;3.1  [Architecture](#31-architecture)
<br/>&nbsp;3.2  [UI Component](#32-ui-component)
<br/>&nbsp;3.3  [Parser Component](#33-parser-component)
<br/>&nbsp;3.4  [Command Component](#34-command-component)
<br/>&nbsp;3.5  [AnimeData Component](#35-animedata-component)
<br/>&nbsp;3.6  [User Component](#36-user-component)
<br/>&nbsp;3.7  [Storage Component](#37-storage-component)

4.  [Implementation](#4-implementation)
<br/>&nbsp;4.1  [Estimate Feature](#41-estimate-feature)
<br/>&nbsp;4.2  [Browse Feature](#42-browse-feature)
<br/>&nbsp;4.3  [View Anime Information Feature](#43-view-anime-information-feature)
<br/>&nbsp;4.4  [Workspace Feature](#44-workspace-feature)
<br/>&nbsp;4.5  [Watchlist Management Feature](#45-watchlist-management-feature)
<br/>&nbsp;4.6  [Add To Watchlist Feature](#46-add-to-watchlist)
<br/>&nbsp;4.7  [Remove From Watchlist Feature](#47-remove-from-watchlist)
<br/>&nbsp;4.8  [View Anime In Watchlist Feature](#48-view-all-anime-in-watchlist)
<br/>&nbsp;4.9  [Bookmark Feature](#49-bookmark-feature)

5.  [Documentation, Logging, Testing, and DevOps](#5-documentation-logging-testing-and-devops)
<br/>&nbsp;5.1 [Documentation](#51-documentation)
<br/>&nbsp;5.2 [Logging](#52-logging)
<br/>&nbsp;5.3 [Testing](#53-testing)
<br/>&nbsp;5.4 [DevOps](#54-development-and-operations-devops)

[Appendix A: Product Scope](#appendix-a-product-scope)

[Appendix B: User Stories](#appendix-b-user-stories)

[Appendix C: Non-Functional Requirements](#appendix-c-non-functional-requirements)

[Appendix D: Instructions For Manual Testing](#appendix-d-instructions-for-manual-testing)

<br/>

## 1. Introduction
**AniChan** is a command-line application written in **Java 11**. It is written using the Object-Oriented Programming (OOP) paradigm which provides us with means to structure a software program into organized, reusable and reusable pieces of code that makes it good for future improvements and revisions.

### 1.1 Purpose

This document is meant for new and current developers of **AniChan**. It describes the overall architecture design of **AniChan**, and lays out the current implementation details of our notable features with the rationale and considerations behind each one. It is a living document that would continue to be edited and updated for each major release, and the current edition of this document is intended for the release v2.0.

<br/>

## 2. Setting Up
### 2.1 Setting up the project in your computer

Ensure that you have the following installed: 
*   JDK 11.
*   IntelliJ IDE (highly recommended).

Firstly, **fork** this repo and **clone** a copy into your computer.

If you plan to use Intellij IDEA: 
1.  **Ensure IntelliJ is configured to use JDK 11**.
    1.  Click on `Configure` > `Structure for New Projects` > `Project Settings` > `Project`, and ensure the `Project SDK` is using **JDK 11**.

2.  **Import the project as a Gradle project**.
    1.  Click on `Import Project` and locate the `build.gradle` file and select it. Click `OK`
    2.  If asked, choose to `Open as Project` (not `Open as File`).
    3.  Click `OK` to accept the default settings but do ensure that the selected version of `Gradle JVM` matches the JDK being used for the project.
    4.  The import process could take a few minutes to finish.

3.  **Verify the setup**: 
    1.  Run `Main.main()` and try a few commands.
    2.  You may want to refer to our [User Guide](UserGuide.md) for the list of commands supported by **AniChan**.

<br/>

### 2.2 Before writing code
#### 2.2.1 Configuring the Coding Style

If using IDEA, follow this guide [IDEA: Configuring the code style](https://se-education.org/guides/tutorials/intellijCodeStyle.html) to setup IDEA’s coding style to match ours.
    
#### 2.2.2 Set up CI
    
There is no set up required as the project comes with a GitHub Actions config files, located in `.github/workflows` folder. When GitHub detects these files, it will run the CI for the project automatically at each push to the master branch or to any PR.
    
#### 2.2.3 Learn the Design
    
When you are ready to start writing codes, we recommended that you have a look at **AniChan**'s overall design by reading about it at [AniChan's architecture](DeveloperGuide.md#31-architecture).
    
<br/>

## 3. Design 

The following section describes the architecture design of **Anichan**. This section starts off by looking at the overall architecture design in a general view, before going into the specific implementation details of the individual features.

<br/>

### 3.1 Architecture
This section will help provide insight to the general overview of **Anichan**’s architecture.

<br/>

![Architectural Diagram](images/Architectural-Design.png)

*Figure 1: Architecture Design Diagram*

> :bulb: The images used are stored in the directory: `images/`. If you wish to update a diagram you may replace the images in this folder.

<br/>

The **Architecture Diagram** presented above explains the high-level design of **AniChan**, and given below is a quick overview of each component involved.

The `Main` class is the starting point of the application and has only one class called `Main`, it is responsible for, 
*   At launch: Initializes the various components in the correct sequence, connects them up with each other, and loads any saved data.
*   At shut down: Shuts down the components and invokes any clean up methods where necessary.

The rest of **AniChan** consists of 6 components: 
*   `Ui`: Manages the user interface of **AniChan**.
*   `Parser`: Parses the user input.
*   `Command`: Executes the command.
*   `User`: Manages the workspace(s) and user data.
*   `AnimeData`: Provides data from the anime source file.
*   `Storage`: Reads data from, and writes data to, the hard disk.

<br/>

Below are some example sequence diagram to help illustrate the general program flow and how the different objects interact with each other.

The first sequence diagram shows the program flow of starting AniChan as an existing user.
![Starting Sequence Diagram](images/Starting-Sequence-Diagram.png) <br/>
*Figure 2: Starting Up Sequence Diagram*

<br/>

The next sequence diagram shows an example command execution.

![Command Execution Sequence Diagram](images/Command-Execution-Sequence-Diagram.png) <br/>
*Figure 3: Command Execution Sequence Diagram*
 
<br/>

### 3.2 UI Component
![UI Class Diagram](images/Ui-Class-Diagram.png) <br/>
*Figure 4: UI Class Diagram*

The UI component consists of a `UI` class that handles all user input and system output. The UI is only dependent on the `Main` class and does not interact directly with other classes ensuring high cohesiveness and separation of roles.

The `Ui` component listens for: 
*   the execution of commands to print the result of the Command.
*   any exceptions thrown to show an error message to the user, instead of a program termination.

<br/>

### 3.3 Parser Component
![Parser Class Diagram](images/Parser-Class-Diagram.png) <br/>
*Figure 5: Parser Class Diagram*

The `Parser` component consists of a `Parser` class and multiple `XYZParser` each representing a specific command’s parser. The Parser class will first receive a user command from `Main` and will proceed to determine the command type.

Once the command type is known, it will then create the respective `XYZParser` class.

Example: If Browse command was parsed, `Parser` will create `BrowseParser`.

`XYZParser` will then parse the parameter and perform input validation, before creating the Command object to return to `Main`.

<br/>

### 3.4 Command Component
![Command Class Diagram](images/Command-Class-Diagram.png) <br/>
*Figure 6: Command Class Diagram*

The `Command` component consists of different commands represented together as `XYZCommand` which all inherits from the abstract `Command` class. 

Example: The Browse command would be represented by a `BrowseCommand`.

`Main` would utilise the `Command.execute` operation to carry out the execution of the command and retrieve a String output that will contain the successful result of the `Command`. If the `Command` was not successful an exception will be thrown with details of the failure.

<br/>

### 3.5 AnimeData Component
![AnimeData Class Diagram](images/AnimeData-Class-Diagram.png) <br/>
*Figure 7: AnimeData Class Diagram*

The `AnimeData` component is responsible for retrieving offline json data and parsing it into `Anime` objects that will be stored in program memory. The `AnimeData` will manage an ArrayList of `Anime` objects providing **AniChan** with an interface for the program to retrieve with the source data.

The `AnimeData `component:
*   can retrieve Anime objects using ID.
*   can view detailed  information of each Anime Object.
*   can browse the Anime catalog with sorting algorithms.

<br/>

<!-- @@author ChanJianHao -->
### 3.6 User Component
![User Class Diagram](images/User-Class-Diagram.png) <br/>
*Figure 8: User Class Diagram*

The User inherits from the abstract `Human` class and stores the name and gender of the user. It represents the user's interaction with `Workspace` class.

The `User`component: 
*   can provide user information like `name`, `gender`, and `honorific name`
*   stores an array list of type `Workspace`
*   can add, set, and switch between workspaces 

The `Workspace` component:  
*   can allow `User` to create and get the list of `Watchlist` and `Bookmark`.
*   can allow `User` to change his active `Watchlist`.

<!-- @@author -->

<br/>

### 3.7 Storage Component
![Storage Class Diagram](images/Storage-Class-Diagram.png) <br/>
*Figure 9: Storage Class Diagram*

The `Storage` component consist of `StorageManager` which:
*   can **save** workspace created by the user as a folder.
*   can **save** user, watchlist and bookmark data in `.txt` format and **read it back** using their respective storage class, `UserStorage`, `WatchlistStorage`, and `BookmarkStorage`.
*   can **read** script files that are in `.txt` format using the class `ScriptStorage`.

**AniChan** saved these data as `.txt` files so advanced users will be able to view and manipulate these saved data easily with any available text editor.

<br/>

## 4. Implementation
This section introduces the specific implementation details and design consideration of some features in **AniChan**.

<br/>

### 4.1 Estimate Feature
The estimate feature aims to provide translators with better estimates on the time needed to translate a script based on their capability. Hence, allowing users to better manage their time and be able to provide clients with much accurate estimation timings.

#### 4.1.1 Current Implementation
The estimate feature is facilitated by `EstimateCommand`. By running the command `estimate` with the relevant arguments, `EstimateParser` will construct `EstimateCommand` which will be used to execute the user's instruction. The command takes in two parameters: 
*   `scriptFileName` (mandatory).
*   `wordsPerHour` (optional).

<br/>

Given below is an example usage scenario showing how the `EstimateCommand` behaves at each step.

**Step 1:** User executes the command `estimate script.txt -wph 300`. The application invokes `Parser#getCommand()` and because the command type is "estimate", `EstimateParser#parse()` is invoked to parse, validate, and construct `EstimateCommand` with "script.txt" and "300". The created object is then returned to `Main`.

**Step 2:** `EstimateParser` is terminated at this point. The application invokes `EstimateCommand#execute()` to execute the user's instruction.

**Step 3:** `EstimateCommand` first invokes `User#getActiveWorkspace()` to identify the workspace the user is currently using, then it invokes `StorageManager#loadScriptFile()` to read and store the content of `scriptFileName` in the active workspace folder in `fileContent`.

> :memo: Every workspace is actually a folder in the system.

> :memo: The application assumes that the user has the file placed in the active (currently using) workspace.

<br/>

**Step 4:** Once the file has been read, it calculates the estimated time using `fileContent` and `wordsPerHour`, then invokes `EstimateCommand#timeNeededToString()` to convert the estimated time into a human-readable format, and finally, returns the result to `Main` for it to be printed via `Ui#printMessage()`.

> :memo: If `wordsPerHour` was not specified, the values 400, 500, and 600 words per hour (average translator's speed) will be used and this will generate 3 estimation timings, unlike the current scenario, only 1 estimation timing will be generated.

<br/>

**Step 5:** `EstimateCommand` is terminated.

<br/>

The sequence diagram presented below depicts the interaction between the components for running the command, `estimate script.txt -wph 300`.
> :memo: The sequence diagram shows the interaction from step 2 onward.

![EstimateCommand Sequence Diagram](images/EstimateCommand-Sequence-Diagram.png)

*Figure 10: Sequence Diagram for `estimate script.txt -wph 300`*

<br/>

#### 4.1.2 Design Considerations
This section shows some design considerations taken when implementing the estimate feature.

Aspect: **When should the program read the script file**

| Approach | Pros | Cons |
| --- | --- | --- |
| During command execution **(current design)**. | Easy to implement since `Command` already handle file matters. | Memory resources are wasted if file validation fails during the execution. |
| During parsing. | No memory resource wasted as it will not fail due to invalid file. | Decreases cohesion as `Parser` now has to handle file matters on top of parsing matters. |

Having considered both of the alternatives, we have decided to implement the first alternative, **read script file content during command execution** because we do not want to decrease the cohesion of Parser, and we find that the memory resource wasted in the process is a worthy exchange for the cohesion preserved.

<br/>

Aspect: **The way user can specify the script file**

| Approach | Pros | Cons |
| --- | --- | --- |
| Specify file extension **(current design)**. |  Ensures the correct file will be read. | Some users may not know how to identify the file extension. |
| Do not have to specify file extension. | Users can easily specify the file to read. | May read the wrong file due to identical names but different file extension. |

We have decided to the implement the first alternative, **users should specify the file extension in their input** because there is great importance in getting a correct estimation timing, and it far outweighs and compensates for the hassle of entering the file extension, and we believe such mistakes are costly for our users.

<br/>

### 4.2 Browse Feature
The browse feature is a useful feature that will allow users to quickly look through all 
the different anime series available in a browsing fashion. The feature will have several enhancements such 
as sorted browsing to browse in alphabetical or by the rating of the anime.

<br/>

#### 4.2.1 Current Implementation
The `BrowseCommand` is executed by `BrowseCommandParser` after parsing the user input. It will then fetch `Anime` objects matching the parameters specified by `BrowseCommandParser` that are stored in `AnimeData`. It extends the `Command` class and implements the following operations:
*   `BrowseCommand#sortBrowseList()` - Handles any sorting of `Anime` objects. 
*   `BrowseCommand#buildBrowseOutput()` - Creates the output string to be printed to users.
<br/>

These are the two fundamental operations that will carry out the execution of the browse command.

`BrowseCommand` will be constructed with default values. This ensures that even when there is no input provided, 
it can still perform a default browse and return relevant useful information.

Before going any further, please refer to this helpful table of the `BrowseCommand` parameters for reference.
These are the attributes that will define the scope of a browse command.

| Attribute | Value  | Function          |
| ---       | ---    | ---               |
| order     | 0      | Ascending         |
| order	    | 1      | Descending        |
| sortType  | 0      | No Sort           |
| sortType  | 1      | by name           |
| sortType  | 2      | by rating         |
| sortType  | 3      | back to original  |
| page      | \>= 1  | page number       |

> :bulb: The magic values have already been refactored out into constant variables.

Given below is an example usage scenario to showcase how a `BrowseCommand` will behave at each step. 
This example will utilise a small sample list of `AnimeData` and a page size of `3`.

> :memo: It is set to `20` as default in actual execution. 

**Step 1:** When the user enters the command for `Browse`, the input will be processed and parsed by `parser.java` and 
then further parsed by `BrowseParser.java`.

**Step 2:** Upon completion of parsing and input validation, `BrowseParser.java` will create an executable `BrowseCommand` 
object and return it to `main`.

**Step 3:** Once the `BrowseCommand` object is executed, it will firstly process the `AnimeData` by calling on its `BrowseCommand#sortBrowseList()` operation to identify the order that the list will need to be in (if any).

For this case since it is a default browse operation, there is no sorting performed.

**Step 4:** Now `BrowseCommand` will utilise its `BrowseCommand#buildBrowseOutput()` operation to access all 
`Anime objects within the page window, as shown in the diagram below.

![Browse Object Diagram 1](images/Browse-Default-State.png) <br/>
*Figure 11: Browse Default State Object Diagram*

In this example, it fetches the following `Anime` objects.
```text
Charlie
Echo
Gamma
```

If the 2nd page of the list was requested instead with the command `browse -p 2`. 
`BrowseCommand#buildBrowseOutput()` will shift its page window down by 1 page as depicted in the diagram below.

![Browse Object Diagram 2](images/Browse-Default-State2.png) <br/>
*Figure 12: Browse Next Page Object Diagram*

**Step 5:** At each `Anime` object, it will access its methods to get the relevant information about that anime series and construct a printable result for the user to view.

**Step 6:** After all `Anime` objects in the page window have been retrieved, it will return the printable result back to `BrowseCommand#execute()`. At this point it will utilise `BrowseCommand#sortBrowseOption()` again to reset the `AnimeData` list to its original form if it has been altered.

**Step 7:** Once `AnimeData` has been sorted, it will return the result back to `Main` for printing.

Currently, this result is not very exciting as it is just based on the Anime ID which is assigned randomly by our data source, AniList.

An example scenario would be browsing the 2nd page of a **sorted** list in ascending order.
The only step that would change would be at Step 3, where it will perform sorting of `AnimeData` list. 

![Browse Object Diagram 3](images/Browse-Sorted-State.png) <br/>
*Figure 13: Browse Sorted State Object Diagram*

As you can see, even though the page window is at the same position as the previous command, 
the list is different as it has been sorted.

From this point onwards, the operation will continue as per the steps above but during the final `BrowseCommand#sortBrowseList()` call in step 6. It will perform a sort to reset the list.

Here is the sequence diagram to better illustrate the lifecycle of a browse command.

![Browse Sequence Diagram](images/Browse-SequenceDiagram.png) <br/>
*Figure 14: Browse Sorted Sequence Diagram*

<br/>

#### 4.2.2 Design Consideration
Here are some various design considerations that was taken when implementing the `browse` feature.

Aspect: **How should the program handle the sorted list**

The first design consideration was how the sorting should be carried out. The main issues here are the time and storage complexity.

| Approach | Pros | Cons  |
| --- | --- | --- |
| 1. Leaving the list unsorted        | - No complexity and fastest approach  | - List will be unsorted and may cause confusion to users |
| 2. Resorting the list again         | - The list will be back into its original form before browsing    | - May hinder performance as resorting could take time <br/> - Requires altering of the main list |
| 3. Cloning a duplicate list to sort | - The list will be back to its original form <br/> - The main list will not be affected at all | - Expensive operation that will require large storage and time complexity |

While the 1st approach is the fastest, the consequence of leaving the main list unsorted is too great and may produce a 
lot of uncertain results as well as confuse the user. Although the 3rd approach provides the best benefit, 
its complexity may end up violating the project’s memory limit constraint if the list is large. 
Therefore, the 2nd approach was adopted, as its performance cost outweighs the others.

Aspect: **Should the program use an interactive or static browsing approach**

The second design consideration was how to carry out the page by page browsing as demonstrated above.
The main concern here was cohesiveness and interactivity.

| Approach | Pros | Cons  |
| --- | --- | --- |
| 1. Interactive browsing, users can `flip` pages and `pick` to view specific anime | - Fluid and seamless browsing session. <br/>- Very good usability. | - Would require `BrowseCommand` to get user input. |
| 2. Static browsing, users specify the page they want to access. | - Completely decoupled from Ui component <br/>- Allows for browse to be more precise in finding what the user wants |  - Not as seamless as the first approach but still usable. |

Though the 1st approach could have created a more authentic browsing feature it is not a good fit of the OOP requirements.
While the 2nd approach allows for more precise browsing of pages means that more experienced users are able to utilise the 
tool quicker and to the same effect as the first approach. 
As a result, the 2nd approach was adopted in favour of having an application that is highly object-oriented.

<br/>

### 4.3 View Anime Information Feature
The `info` command allows the user to view all the relevant information regarding a specific anime that the user specifies. This allows them to know more about a particular anime.

#### 4.3.1 Current Implementation
The view information command is currently implemented by the `InfoCommand`. The user has to give an input of the form `info -a <ANIME_ID>`, and this would allow users to check all the information available for the ANIME_ID they have specified.

Given below is an example of the usage scenario of view information command and how it behaves at each step.

**Step 1:** `Ui` would receive input in form of `info -a <ANIME_ID>` in the `Main` class and pass it into `Parser` class

> :bulb: The ANIME_ID specified has to be an integer value as specified by the index of Anime in the AnimeData. 

**Step 2:** The `Parser` class would then extract out `info` from the input given, which will instantiate a new `InfoParser` object, in which `InfoCommand` object is constructed as well.

**Step 3:** The `InfoParser#parse()` method will be invoked, and this will validate the parameters given by the user. Once validated, ANIME_ID will be set inside the `InfoCommand` object that was created previously. `InfoCommand` object will be returned back all the way to `Main`. `InfoParser` is then terminated.

Figure 14 below shows the sequence diagram for steps 1 to 3 of the Information feature

![Info Command Step 1 to 3 Sequence Diagram](images/InfoCommand-Step1-3-Sequence-Diagram.png) <br/>
*Figure 15: Info feature steps 1 to 3 sequence diagram*

**Step 4:** `Main` will then call `InfoCommand#execute()`. In here the ANIME_ID will be validated and `AnimeData#returnAnimeInfo()` method is invoked, returning a string containing information regarding that particular ANIME_ID.

**Step 5:** The string is returned all the way back to `Main` and printed out by `Ui`.

**Step 6:** `InfoCommand` is terminated

The diagram below is the sequence diagram from steps 4 onward.

![Info Command Step 4 to 6 Sequence Diagram](images/InfoCommand-Step4-6-Sequence-Diagram.png) <br/>
*Figure 16: Info feature steps 4 to 6 sequence diagram*

#### 4.3.2 Design Consideration
This section shows the considerations taken when designing this feature.

Aspect: **Using anime title or Anime ID as the field for input**

| Approach | Pros | Cons  |
| --- | --- | --- |
| 1. Use anime title as input | - Easier for users who know anime titles | - Users will have to input the full anime title <br/> - The program has to search through the whole list of anime data |
| 2. Use anime ID as input | - Users will only need to input one single integer | - Users will have to search for the anime ID if they do not know the ID |

We decided to go with approach 2, as it would enhance the user experience of not having to key in the full anime title. At the same time, using anime ID as input would allow the program to be able to retrieve the full anime information much quicker, instead of having a title to search against the whole data of anime to retrieve information.

<br/>

### 4.4 Workspace Feature
Similar to a desktop, **AniChan** has a workspace feature which allows users to organise data in separate ‘containers’ and switch between them to avoid intermixing of information.

<br/>

`Workspace` is primarily the layer of code that sits between the user, and the rest of **AniChan** data management features (i.e., `Watchlist`, `Bookmark`). 

As such, most of the code that manages `Workspace` can be found in [User.java](https://github.com/AY2021S1-CS2113T-F12-2/tp/blob/master/src/main/java/anichan/human/User.java) 
and [Workspace.java](https://github.com/AY2021S1-CS2113T-F12-2/tp/blob/master/src/main/java/anichan/human/Workspace.java).

> :memo: Upon running the program for the first time, a workspace named `Default` is created. A similarly named folder will also be created in `/data` directory, managed by our `Storage` class.

<br/>

#### 4.4.1 Current Implementation

| Command Option        | Workspace Command                      | Description field |
|---|---|---|
| `-n`       | `WorkspaceCommand#createWorkspace()`              | Creates new workspace |
| `-s`       | `WorkspaceCommand#switchWorkspace()`              | Switches to specified workspace |
| `-l`       | `WorkspaceCommand#listWorkspace()`                | Lists existing workspace(s)           |
| `-d`       | `WorkspaceCommand#deleteWorkspace()`              | Deletes specified workspace |

The `WorkspaceCommand` is instantiated by `WorkspaceParser`, and it requires 2 parameters: 
*   `commandOption` (mandatory).
*   `workspaceName` (mandatory unless option `-l` is specified).

<br/>

Given below is an example usage scenario showing how the command behaves at each step when the user tries 
to **create new** `Workspace`:

**Step 1:** User launches the application for the first time. The `User` will be initialized with an initial `Workspace` named `Default`, and the `activeWorkspace` pointing to it and `workspaceList` `ArrayList` containing it.

![Workspace Command Initial State Diagram](images/WorkspaceCommand-Initial-State.png) <br/>
*Figure 17: Workspace Command Initial State*

<br/>

**Step 2:** User enters the command `workspace -n Netflix Animation Studio`, the input will be processed and parsed by `Parser.java` and then further parsed by `WorkspaceParser.java`.

**Step 3:** Upon completion of parsing and input validation, `WorkspaceParser.java` will create a `WorkspaceCommand` object with the extracted `commandOption` and `workspaceName` parameter and return it to `Main`.

**Step 4:** `Main` calls `WorkspaceCommand#execute()` and it checks the `commandOption` before running `WorkspaceCommand#createWorkspace()` accordingly.

**Step 5:** `WorkspaceCommand` firstly calls `User#addWorkspace()` to add a new `Workspace` to `User`, then makes an empty `ArrayList` of `Watchlist` using `User#setWatchlistList` for the `User`.
Finally, it uses `storageManager#saveWorkspace()` to save the `Workspace` to disk.

![Workspace Command After Creation Diagram](images/WorkspaceCommand-After-Create.png) <br/>
*Figure 18: Workspace Command After New Workspace Creation*

<br/>

**Step 6:** If successful, `WorkspaceCommand` returns the successfully created workspace message to `Main`.

<br/>

Likewise, the operations to switch, list, and delete follows a similar execution process. 
The following diagrams will continue **from step 6**, and will illustrate the changes to the `Workspace` `ArrayList`.

**Step 7:** User keys in `workspace -s Netflix Animation Studio` to switch active workspace.

![Workspace Command After Switch Diagram](images/WorkspaceCommand-After-Switch.png) <br/>
*Figure 19: Workspace Command After Workspace Switch*

<br/>

**Step 8:** User keys in `workspace -d Default` to delete the workspace named `Default`.

![Workspace Command After Switch Diagram](images/WorkspaceCommand-After-Delete.png) <br/>
*Figure 20: Workspace Command After New Workspace Delete*

<br/>

The following sequence diagram illustrates how `Workspace` creation in the example above works:

> :memo: The other options (`-s`, `-l`, `-d`) follows a similar process, only the list and switch option does not interact with `StorageManager` and `Watchlist`.

![Workspace Command Sequence Diagram](images/WorkspaceCommand-Sequence-Diagram.png) <br/>
*Figure 21: Workspace Command After New Workspace Delete*

<br/>

#### 4.4.2 Design Consideration

This section shows some design considerations taken when implementing the `Workspace` feature.

Aspect: **How can `Workspace` be identified?**

As most commands in `WorkspaceCommand` operates on an individual `Workspace`, there needs to be some way to identify each of them uniquely. 

| Approach | Pros | Cons  |
| --- | --- | --- |
| Identify using a number ID | Users can quickly `switch` and `delete` `Workspace` just by keying in a number | Operations like `delete` is irreversible and is not done often, accidentally keying in the wrong number can be catastrophic |
| Identify using name  | If user remembers the name, he can easily `switch`/`delete` without using the `List` command first | User may waste time typing long workspace names |

We have decided to use `name` to identify `Workspace` as it is more intuitive for the end-user. 
This also avoids the need to maintain an integer `ID` for each `Workspace`.

<br/>

Aspect: **`Workspace` name restrictions**

As `Workspace` is identified by their names, and other classes like `Storage` relies on the name to make folders for data storage purposes.
Should we allow the user full discretion to naming `Workspace`?

| Approach | Pros | Cons  |
| --- | --- | --- |
| Yes  | Allows user more flexibility | Confusing names may lead to unexpected outcomes |
| No   | Eliminate unexpected names which could lead to unexpected outcomes | Less flexibility and more code required to enforce |

For example, a user may provide `new workspace__` as a `Workspace` name, this may confuse the user in future when he tries to list 
all `Workspace` as the space characters are whitespaces. Hence, enforcing no extra whitespaces was implemented. 

In addition, we also prevent case-insensitive `Workspace` creation and the use of special characters, as they may cause issues when creating folders on certain file systems.

<br/>

Aspect: **Loading `Workspace` on program start**

As there needs to be an `activeWorkspace` set at all times for operations such as adding `Watchlist`. How do we determine which `Workspace` should be chosen if the `User` owns multiple `Workspaces`?

| Approach | Pros | Cons  |
| --- | --- | --- |
| Scan from data folder and pick first result  | Able to adapt to changes even if malicious edits were made to file system | User may need to switch to his favourite `Workspace` on each startup, if any |
| Store last used `Workspace`                  | `User` might gain some convenience of not needing to switch to his favourite `Workspace` | Prone to potential issues from the file system |

We picked the first approach as it is the safer option. By allowing **AniChan** to scan and adapt to file system changes (e.g. `User` accidentally moves/renames/deletes `Workspace` on his hard drive), we avoid potential issues of relying on old information created in previous runtime which may hinder user experience.

<br/>

### 4.5 Watchlist Management Feature
The watchlist management feature aims to provide translators with a simple way to keep track of animes by being able to group animes based on their own criteria. This allows them to stay organized and focused on their work rather than being concerned over management issues.

#### 4.5.1 Current Implementation
The watchlist management feature is facilitated by `WatchlistCommand`. By running the command `watchlist` with the relevant options and arguments, `WatchlistParser` will construct `WatchlistCommand` which will be used to execute the user's instruction. The command takes in three parameters: 
*   `option` (mandatory).
*   `watchlistName` (mandatory only if the option `-n` was specified).
*   `watchlistIndex` (mandatory only if the option `-s` and `-d` was specified).

Below is a table describing the 4 options supported by the `watchlist` command, including the methods (parameters are omitted) invoked for the option.
> :memo: The term **active watchlist** refers to the watchlist that the user is using to add anime into or remove anime from, and this is tracked by `activeWatchlist` in `Workspace`.

| Option | Method | Description |
| --- | --- | --- |
| `-n` | `WatchlistCommand#createWatchlist()` | Creates a new watchlist |
| `-l` | `WatchlistCommand#listAllWatchlist()` | Lists all watchlist in the workspace |
| `-s` | `WatchlistCommand#selectWatchlist()` | Selects a watchlist to be the new active watchlist |
| `-d` | `WatchlistCommand#deleteWatchlist()` | Deletes a watchlist |

<br/>

Given below is an example usage scenario showing how the `WatchlistCommand` behaves at each step. In this example, we will look at the **watchlist creation process**.

![WatchlistCommand Initial State](images/WatchlistCommand-Initial-State.png)

*Figure 22: WatchlistCommand Initial State*

**Step 1:** User executes the command `watchlist -n NewAnime`. The application invokes `Parser#getCommand()` and because the command type is "watchlist", `WatchlistParser#parse()` is invoked to parse, validate, and construct `WatchlistCommand` with "-n" and "NewAnime". The created object is then returned to `Main`.

**Step 2:** `WatchlistParser` is terminated at this point. The application invokes `WatchlistCommand#execute()` to execute the user's instruction.

**Step 3:** `WatchlistCommand` first invokes `User#getActiveWorkspace()` to identify the workspace to add the new watchlist, and according to the instruction "-n", `WatchlistCommand#createWatchlist()` is invoked.

**Step 4:** It first invokes `activeWorkspace.getWatchlistList()` to initialise `watchlistList`. A `Watchlist` object is then constructed with the name "NewAnime" and validated before it is added to `watchlistList`.

**Step 5:** `StorageManager#saveWatchlist()` is invoked to save the updated `watchlistList`, and finally, the result of this command execution is returned to `Main` for it to be printed via `Ui#printMessage()`.

> :memo: The validation checks ensure the watchlist name is unique in `watchlistList` and the name does not exceed 30 characters.

> :memo: The details of all `Watchlist` object for a workspace will be saved in the file "watchlist.txt" in the workspace folder.

<br/>

**Step 6:** `WatchlistCommand` is terminated.

![WatchlistCommand After Create State](images/WatchlistCommand-After-Create-State.png)

*Figure 23: WatchlistCommand After Create State*

<br/>

All the other options in the watchlist command also follows a similar execution process. The following diagrams will **continue from step 6**, and it will show you how the state of the application changes as it continues to execute the select and delete option.
> :memo: The list option (`-l`) is not shown as there will not be any change in the application state after its execution.

**Step 7:** The user executes `watchlist -s 2` to change his active watchlist to the second watchlist (“NewAnime”) in the list.

![WatchlistCommand After Select State](images/WatchlistCommand-After-Select-State.png)

*Figure 24: WatchlistCommand After Select State*

<br/>

**Step 8:** The user now decides that the "NewAnime" watchlist is no longer needed and decides to execute `watchlist -d 2` to delete it.

![WatchlistCommand After Delete State](images/WatchlistCommand-After-Delete-State.png)

*Figure 25: WatchlistCommand After Delete State*

<br/>

The sequence diagram presented below depicts the interaction between the components for running the command, `watchlist -n NewAnime`.
> :memo: The sequence diagram shows the interaction from step 2 onward.

> :memo: The other options (`-l`, `-s`, `-d`) follow a similar process, only the list and select option does not interact with `StorageManager`.

![WatchlistCommand Create Watchlist Sequence Diagram](images/WatchlistCommand-CreateWatchlist-Sequence-Diagram.png)

*Figure 26: Sequence Diagram for `watchlist -n NewAnime`*

<br/>

#### 4.5.2 Design Considerations
This section shows some design considerations taken when implementing the watchlist management features.

Aspect: **Saving watchlist data**

| Approach | Pros | Cons |
| --- | --- | --- |
| After each command execution **(current design)**. | Data would not be lost if the application or system crashes midway. | Application might slow down when the data grows large. |
| When the user exits the program. | Saving is more efficient and could improve performance. | User may lose their data if the application or system crashes midway. |

Having considered both of these alternatives, we have decided to save watchlist data **after each command execution** because users may work on the application for long period and unexpected events can always happen. Losing work data can also be a frustrating and costly mistake to translators especially if these data are important.

<br/>

Aspect: **Length of watchlist name**

| Approach | Pros | Cons |
| --- | --- | --- |
| No restriction | Users have more flexibility | This may hinder user's vision of the input prompt |
| Restricted to 30 characters **(current design)** | Ensures users have proper view of the input prompt | Users have less flexibility in naming |

While both alternatives are valid in their own ways, we have decided to **restrict the length of watchlist name to 30 characters** because we find that long names can muddle up the readability. We also believe that most users would probably prefer to use short names as that makes it easier for them to identify what the watchlist is.

<br/>

### 4.6 Add To Watchlist
The `add` feature allows users to add an anime into the active watchlist they are currently at. This helps them keep track of the anime they would like to watch next.

#### 4.6.1 Current Implementation
The current implementation of the add to watchlist command requires the user to give an input in the form of `add -a <ANIME_ID>`. This will allow users to add the ANIME_ID of the anime they want to add by calling the `addAnimeToList` method in the active `Watchlist` object. 

Add to watchlist command extends the `Command` class, and the `parse` method in `AddToWatchlistParser` class is being called to validate the parameter that the user has entered.

Below is an example usage scenario of how add to watchlist command behaves at each step.

**Step 1:** Starting from the `Main` class, the user first inputs `add -a <ANIME_ID>`. The input will be taken in by the `Ui` class, and passed into `Parser` through `Parser#getCommand(userInput)`. 

> :bulb: The ANIME_ID specified has to be an integer value as specified by the index of anime in the AnimeData. 

**Step 2:** The `Parser` class would then extract the `add` command out of the input and it will instantiate a new `AddToWatchlistParser` object, and its constructor would create a new `AddToWatchlistCommand` object.

**Step 3:** `AddToWatchlistParser#parse()` is then called by the `Parser` class. This will validate the parameter that has been given, and then sets the anime index in the `AddToWatchlistCommand` object by calling the setter method. The `AddToWatchlistCommand` object will then be returned back to `Main` class. At this point, `AddToWatchlistParser` is terminated.

The figure below shows the sequence diagram of steps 1 to 3.

![Add To Watchlist Command Step 1 to 3](images/AddToWatchlist-Step1-3-Sequence-Diagram.png) <br/>
*Figure 27: Sequence diagram for Add To Watchlist feature steps 1 to 3*

**Step 4:** `AddToWatchlistCommand#execute()` is then invoked in `Main`, which retrieve the active `workspace` through `AddToWatchlistCommand#getActiveWorkspace()`, and `Watchlist` object from `ActiveWorkspace#getActiveWatchlist()`.

**Step 5:** `Watchlist#AddAnimeToList()` will then be called, passing in the anime index. This will then add the anime index into the ArrayList of integers storing all the animes in that `Watchlist`.

**Step 6:** `commandResult` will then be passed back to `Main` to be printed out by `Ui`, and `AddToWatchlistCommand` is  terminated

For better illustration, Figure 27 below shows the sequence diagram of steps 4 to 6.

![Add To Watchlist Command Step 4 to 6](images/AddToWatchlist-Step4-6-Sequence-Diagram.png) <br/>
*Figure 28: Sequence diagram for Add To Watchlist feature steps 4 to 6*

#### 4.6.2 Design consideration
Below shows the considerations taken when implementing the `AddToWatchlist` feature. 

Aspect: **Using anime title or anime ID as the field for input**

This consideration is similar to our `info` feature consideration, so below is the same table we find in our `info` deature section.

| Approach | Pros | Cons  |
| --- | --- | --- |
| 1. Use anime title as input | Easier for users who remember anime titles | Users will have to input the full anime title  |
| 2. Use anime ID as input | Users will only need to input one single integer | Users will have to search for the anime ID if they do not know the ID |

Similarly, we decided to go with the second approach as this would be much easier for users to key in, and also faster for the program to find the anime the user wants to add into the watchlist.

<br/>

### 4.7 Remove From Watchlist
The remove from watchlist feature allows users to remove a particular anime from their currently active watchlist. This would allow them to keep their watchlist clean of the anime that they have watched, leaving only those that they have not watched.

#### 4.7.1 Current Implementation
The remove from watchlist command currently requires the user to give an input in the format: `remove -d <ANIME_ID_IN_WATCHLIST>`. The implementation of remove from watchlist command is similar to the add to watchlist feature with the only difference being that the user has to delete the index of the anime in that watchlist, instead of the actual ANIME_ID.

The usage scenario of remove from watchlist is similar to the add to watchlist command, but we will be using `RemoveCommand`, `RemoveCommandParser` and `Watchlist#removeAnimeFromList()`.

> :bulb: ANIME_ID_IN_WATCHLIST is the index of anime inside the watchlist itself, not the anime index in AnimeData.

#### 4.7.2 Design Consideration
This section describes the design considerations taken when implementing this feature.

Aspect: **which index to use when removing an anime**

| Approach | Pros | Cons  |
| --- | --- | --- |
| 1. Use anime title as input | - Easier for users who know anime titles | - Users will have to input the full anime title <br/> - The program has to search through the whole list of anime data |
| 2. Use anime ID as input | - Users will only need to input one single integer | - Users will have to search for the anime ID if they do not know the ID |

We have decided to use approach 2 instead of 1, as it will not only will it be much faster for the program to delete one particular index instead of having to search through the whole list, it would be provide better user experience as the user will not need to search for the anime ID that he wants to remove in the case that he does not know the anime ID.

<br/>

### 4.8 View All Anime in Watchlist
Users can view the anime that they have stored in a specific watchlist by using the `view` command. In doing so, they can easily check what anime they would like to watch next.

#### 4.8.1 Current Implementation
The `view` command is currently implemented by the `ViewWatchlistCommand`. It is instantiated by `Parser` and executed by `Main`. This allows users to view all the anime currently inside a `Watchlist` object. 

An example usage scenario on how view anime in watchlist behaves is given below.

**Step 1:** In the `Main` class, the user will input `view -v <WATCHLIST_ID>`. `Ui` will take in this input and is passed into `Parser`.

**Step 2:** In `Parser`, `view` will be extracted out of the input, leading to a new `ViewWatchlistParser` object being instantiated, and in the constructor, a new `ViewWatchlistCommand` is created.

**Step 3:** `ViewWatchListParser#parse()` is then called in `Parser`, which will validate the parameter that was given by the user. If the parameter is correct, the watchlist index will be set in the `ViewWatchlistCommand` object. 

**Step 4:** The `ViewWatchlistCommand` object is then returned back to `Parser`, and back to `Main`. `ViewWatchlistParser` is terminated.

The sequence diagram for steps 1 to 4 is as shown in the figure below.
![View Watchlist Command Step 1 to 4](images/ViewWatchlist-Step1-4-Sequence-Diagram.png) <br/>
*Figure 29: Sequence diagram for View Watchlist feature steps 1 to 4*

**Step 5:** The `ViewWatchlistCommand#execute()` would then be called by `Main`, in which the WATCHLIST_ID will be validated.

**Step 6:** `ViewWatchlistCommand#buildAnimeInWatchlist()` will build a string containing all the anime name inside the active `Watchlist`, and it will be returned to `Main`, where it will be printed out by `Ui`.

**Step 7:** `ViewWatchlistCommand` is terminated

The figure below is the sequence diagram for steps 5 to 7
![View Watchlist Command Step 5 to 7](images/ViewWatchlist-Step5-7-Sequence-Diagram.png) <br/>
*Figure 30: Sequence diagram for View Watchlist feature steps 5 to 7*

<br/>

### 4.9 Bookmark Feature
Overview of the different features provided by bookmark
The `bookmark` feature aims to provide the user with the ability to create shortcuts to anime they wish to track. This feature further provides tools to monitor the progress of a series and make informative notes.

<br/>

#### 4.9.1 Current Implementation

The Bookmark class uses three ArrayList to store bookmark entries of the user, these arraylists maintain information about the anime index, episode and notes. The synchronisation between arraylist is required so that it enables easy retrieval of bookmark information using the bookmark index on the three arraylist.

![Bookmark Class Diagram](images/Bookmark-Class-Diagram.png) <br/>
*Figure 31: Bookmark Class Diagram*

`BookmarkCommand` is instantiated by `BookmarkParser`, and requires a mandatory BookmarkAction. With the BookmarkAction the parser will determine the required field for the BookmarkCommand. Below table shows the required field for each action:

| Bookmark Command | Bookmark Action | Required field |
|---|---|---|
| Add              | a               | Anime ID       |
| Delete           | d               | Bookmark ID    |
| List             | l               | Not required |
| Episode          | e               | Bookmark ID, Episode |
| Note             | n               | Bookmark ID, Note |
| Remove Note      | r               | Bookmark ID, Note ID |
| Info             | i               | Bookmark ID |

<br/>

Given below is the example usage scenario and how the `Bookmark` command behaves at each step.

> :bulb: The command is using one-based indexing while the program stores and recognises the zero-based indexing. The following example will use one-based to be consistent with the command. 

**Step 1:** User inputs command `bookmark`,  the application then calls `Parser#getCommand()` and passes the command to it.

**Step 2:** `BookmarkParser` extracts “Bookmark Action” from the command, and based on the command type, it will determine the respective required field. The `BookmarkParser` is responsible for validating the input parameters and will throw an `AniException` when encountering unrecognised inputs.

For example: `bookmark 2 -e 5`, the parser will create `BookmarkCommand` and call the method setBookmarkAction(“e”), setBookmarkIndex(2) and setBookmarkEpisode(5).

**Step 3:** The application calls `BookmarkCommand#execute()` and the command will use the `BookmarkAction` to do a corresponding validation check on the field before calling the  Bookmark Operations.

Below is a list of bookmark operations:
*   `Bookmark#addAnimeBookmark()`: Adds the `Anime` index provided into the bookmark list.
*   `Bookmark#getListInString()`: List all entries within the `Bookmark` using the `Bookmark` index together with the `Anime` name.
*   `Bookmark#deleteAnimeBookmark()`: Remove the `Bookmark` index provided from the bookmark list.
*   `Bookmark#editBookmarkEpisode()`: Edit the current episode for a Bookmark entry .
*   `Bookmark#addNote()`: Add a note for a Bookmark entry.
*   `Bookmark#removeNote()`: Remove a note from a Bookmark entry.
*   `Bookmark#getAnimeInfoFromBookmark()`: Retrieve the anime info on the anime id kept at the specified bookmark entry.
*   `Bookmark#getBookmarkEpisode()`: Retrieve the episode field for a Bookmark entry.
*   `Bookmark#getNoteInString()`: Retrieve notes in a list within the specified bookmark entry.

**Step 4:** The user executes `bookmark -a 430` command to add the anime id: 3 into the bookmark. `Bookmark#addAnimeBookmark()` will then add the anime index to the ArrayList within the bookmark.

![Bookmark State After Add Diagram](images/Bookmark-After-Step4.png) <br/>
*Figure 32: Bookmark Entry After Add*

> :memo: The table shows the three ArrayList objects in the column with the bookmark id. When adding a new anime id into the bookmark, the bookmark will initialise the anime episode to be 0 together with an empty note object.

**Step 4.5:** The user executes `bookmark -a 1` and `bookmark -a 410` to add anime id 1 and 410 to the bookmark.

![Bookmark State After More Add Diagram](images/Bookmark-After-Step4.5.png) <br/>
*Figure 33: Bookmark Entries with more Add*

The following sequence diagram shows how the `Add Bookmark` operation works:

![Bookmark Add Command Sequence Diagram](images/Bookmark-Add-Sequence-Diagram.png) <br/>
*Figure 34: Bookmark Add Command Sequence Diagram*

**Step 5:** The user executes `bookmark -l` command to list all anime within the bookmark. `Bookmark#getListInString()` will use the Anime index stored in the bookmark list and retrieve the anime name from AnimeData, the method then returns the bookmark index with the anime name.

```text
Listing all anime in bookmark:
	1. To Heart 2
	2. Cowboy Bebop
	3. InuYasha the Movie 2: The Castle Beyond the Looking Glass
```

**Step 6:** The user executes `bookmark -d 1` command to delete the bookmark entry at bookmark id: 1. `Bookmark#deleteAnimeBookmark()` will then remove the Bookmark index from the `Bookmark`.

![Bookmark State After Delete Diagram](images/Bookmark-After-Step6.png) <br/>
*Figure 35: Bookmark Entries After Delete*

> :memo: The ArrayList comes with an inbuilt function to enable easy deletion at index, but the bookmark index of subsequent entries will decrease.

**Step 7:** The user executes `bookmark 1 -e 5` command to edit the episode for the first bookmark entry. `Bookmark#editBookmarkEpisode()` will change the episode field for that bookmark entry.

![Bookmark State After Edit Episode Diagram](images/Bookmark-After-Step7.png) <br/>
*Figure 36: Bookmark Entries After Edit Episode*

**Step 8:** The user executes `bookmark 1 -n Schedule push back` command to add a note for a bookmark entry. `Bookmark#addNote()' will then add a note to the bookmark entry at bookmark id:1.

![Bookmark State After Add Note Diagram](images/Bookmark-After-Step8.png) <br/>
*Figure 37: Bookmark Entries After Add Note*

**Step 9:** The user executes `bookmark 1` command to view all information of the first bookmark entry. The command will use `Bookmark#getAnimeInfoFromBookmark()` to retrieve the detailed anime info for the anime id at that bookmark, `Bookmark#getBookmarkEpisode()` for the tracked episode by the user and `Bookmark#getAnimeNotesFromBookmark()` will retrieve all notes in a list format. With all the relevant information on the bookmark entry, the result will be displayed to the user (Figure 26: Bookmark Entries After Edit Episode).

```text
Here is the information for that anime.
Index: 1
Name: Cowboy Bebop
Episodes: 26
Release Date: 03/Apr/1998
Rating: 86
Genre: [Action, Adventure, Drama, Sci-Fi]

Current Episode: 5

Notes for anime:
1. Schedule push back
```

**Step 10:** The user executes `bookmark 1 -r 1` command to remove a note from a bookmark entry. `Bookmark#removeNote()` will remove the note id:1 from the first bookmark entry. The resulting state of the remove note command will look exactly the same to the state before the note was added.

![Bookmark State After Edit Episode Diagram](images/Bookmark-After-Step7.png) <br/>
*Figure 38: Bookmark Entries After Edit Episode*

<br/>

#### 4.9.2 Design consideration
This section describes the various design considerations taken when implementing the `Bookmark` feature.

Aspect: **How should the bookmark entries be kept**

The first design consideration was the data structure on how to bookmark entries should be maintained. The main issue here was the cohesiveness between the `bookmark` object and the `workspace`.

| Approach | Pros | Cons  |
| --- | --- | --- |
| 1. Keep only the anime index information within the bookmark **(Current)**            | - Easy to reference objects within ArrayList using its index and it is easy to implement | - Require to synchronise the three ArrayList so the same index reference the components of the same bookmark entry |
| 2. Use a `BookmarkManager` to handle bookmark features                  | - Do not need to maintain multiple Arraylist    | - One extra layer of unnecessary abstraction (nesting), while introducing more coupling and dependency.  |

<br/>

Aspect: **How should bookmark entries keep anime information**

The second design consideration was how to keep the reference to the anime object. The main issue here was the storage and duplication of data.

| Approach | Pros | Cons  |
| --- | --- | --- |
| 1. Usage of three ArrayList to store anime index, Episode, and Notes **(Current)**            | - Easy to add and delete, which also simplifies the information to keep in offline storage. | - Bookmark Commands has to communicate with AnimeData for anime-related functionalities. |
| 2. Keep the entire anime object in Bookmark                  | - Do not need to reference AnimeData for anime information    | - One extra layer of unnecessary abstraction (nesting), also introducing more coupling and dependency  |

<br/>

## 5. Documentation, Logging, Testing, and DevOps
This section details the documentation, logging, testing and dev-ops setup used in this project as well as information on how to use them.

<br/>

### 5.1 Documentation
We use **Jekyll** to manage documentation. We recommend that you document your features implementation and code changes so that other developers are aware of its architecture.

The `docs/` folder stores the documentation of this project. You can learn more about how to setup and maintain the project website at with [this guide](https://se-education.org/guides/tutorials/jekyll.html).

<br/>

### 5.2 Logging
We encourage the use of logger in this project as they provide deeper insights than error messages which can greatly help developers identify bugs and simplify their logging process.

We are using `java.util.logging`  package for logging. The logger can be accessed using the  `AniLogger`  class. 

`AniLogger` is used to manage the logging levels and logging destinations. Based on the default setting, logs will be written to both console and file `data/AniChan.log`.

The  `Logger`  for a class can be obtained using  `AniLogger.getAniLogger(Class)`  which will log messages according to the specified logging level.

We use the following log levels:
*   `Level.SEVERE` A critical failure, which prevents normal execution of the program.
*   `Level.WARNING`Indicates a potential problem, but can proceed with caution.
*   `Level.INFO` General noteworthy information of the program.

<br/>

### 5.3 Testing
Testing is integral to the development of a reliable software. Before making a pull request, please ensure that all tests pass. You are recommended to write tests as you add new code to the program.

We employ both unit and integration tests for our codes. They are located in `./src/test` directory.

<br/>

#### 5.3.1 Running Tests
There are primarily 2 ways to run the tests.

**Method 1: Using IntelliJ**
*   To run all tests, right-click on the `src/test/java` folder in the project panel and choose `Run Tests`.
<br/>

**Method 2: Using Gradle**
*   Open a terminal and navigate to project directory, run command `./gradlew clean checkstyleMain checkstyleTest test`.

<br/>

### 5.4 Development and Operations (DevOps)

We employ DevOps to ensure consistent release of good quality code and write-ups. 
These tools allow us to detect and mitigate bugs before they are merged to the repository.

<br/>

#### 5.4.1 Build Automation

This project uses Gradle for build automation and dependency management.

It automates tasks such as:
*   Managing library dependencies
*   Analyzing code for style compliance
*   Running tests

The gradle configuration file can be located in `build.gradle`.

<br/>

#### 5.4.2 Continuous integration (CI)

We use [GitHub Actions](https://github.com/features/actions) and [Travis CI](https://travis-ci.org/) as our tool for Continuous integration.

Their configuration is complete and there is no need to modify them, these CI will automatically run checks on every commit to **AniChan** repository.

If you wish to modify these tools, you may find their configuration at:
*   **GitHub Workflow:** `.github/workflows`
*   **Travis CI:** `.travis.yml`

<br/>

##### 5.4.2.1 Code Coverage

As part of CI, this project uses [Codecov](https://codecov.io/) to generate coverage reports. 

If you wish to configure Codecov for your fork, you may refer to [Codecov Docs](https://docs.codecov.io/docs).

<br/>

##### 5.4.2.2 Repository-wide checks

In addition to running Gradle checks, CI includes some repository-wide checks. 
These checks include files from the entire repository and are useful in checking for rules which are hard to enforce on development machines such as line ending requirements.

These checks are POSIX shell scripts (i.e. platform dependent to Linux and macOS). 
To run all checks, execute the following at project root `./config/travis/run-checks.sh`.

If you wish to add new checks, simply add the check file with a filename `check-*` to the `./config/travis/` and the `run-checks.sh` script will automatically run it.

<br/>

## Appendix A: Product Scope

### A.1 Target User Profile
*   Professional anime translators.
*   Works on multiple projects and with various companies.
*   Has difficulty managing their time and information.
*   Prefers command-line desktop applications.
*   Able to type fast.

### A.2 Value Proposition
*   Helps the target user estimate the time needed to translate their scripts.
*   Allows the user to organise work from different projects and companies into workspaces.
*   Allows the user to write and refer to notes on different anime series while translating.
*   Find relevant information regarding an anime series quickly.
*   Organize anime into different watchlists for different genres.
*   Portable and works offline.

<br/>

## Appendix B: User Stories

| Version | As a ... | I want to ... | So that I ... |
| --- | --- | --- | --- |
| v1.0 | user | create new watchlist | can keep track of animes easily based on my defined criteria |
| v1.0 | user | add and remove an anime to a watchlist | can organise my anime into the defined criterias |
| v1.0 | forgetful user | find out all watchlist I have created | can find out what watchlist I have |
| v1.0 | user | save the data I have created | can remember what I have done |
| v1.0 | user | load the data I created previously | can continue to work on these data |
| v1.0 | new user | view all available commands | can easily find a specific command and its usage |
| v1.0 | user | be able to browse through all anime series | can see easily what anime are available  |
| v1.0 | user | be able to sort my browsing session | will only browse through relevant data |
| v1.0 | freelance translator | be able to create new workspaces | can have a clear separation of my work at different companies |
| v1.0 | freelance translator | switch between workspaces | can swap my workspace to a specific company that my current project is tied to |
| v1.0 | forgetful user | bookmark specific anime | remember which anime I am currently interested in |
| v1.0 | forgetful user | set an episode for each bookmarked anime | can remember which episode I'm watching or translating |
| v2.0 | translator | write notes for a bookmark | can remember helpful information regarding a specific anime series |
| v2.0 | translator | remove notes for a bookmark | can remove outdated information or mistakes in my notes |
| v2.0 | user | view detailed information of a specific anime | can find out more about that anime series |
| v2.0 | user | be able to select a watchlist from my list of watchlist to use | can stay focus on working on one watchlist |
| v2.0 | user | delete watchlist that I no longer needs | can keep my list of watchlist organized and up-to-date |
| v2.0 | translator | estimate the time needed to translate a script | can better manage and plan my time |
| v2.0 | user | search for a specific anime with a keyword | can find an anime series without remember its full title |
| v2.0 | user | search for all anime in a genre | can find all similar types anime  |

<br/>

## Appendix C: Non-Functional Requirements

1.  The application should work on major operating systems (OS) such as Windows and Linux that have `Java 11` installed.
2.  Users with fast typing speed should be able to accomplish tasks easily and faster than when they were using mouse.
3.  The application should be easy to use with an accompanying User Guide and/or Developer Guide.
4.  The application should utilize memory resources efficiently.
5.  The application should be responsive.

<br/>

## Appendix D: Instructions for Manual Testing

### D.1: Launch and Shutdown
#### Initial Launch
1.  Download the jar file and copy it into an empty folder.
2.  Open a new command prompt or terminal and navigate to the directory containing `AniChan.jar`.
3.  Enter the command `java -jar AniChan.jar` to launch **AniChan**.
4.  { user setup process to be added }.

> :bulb: This is what the input prompt means: **WORKSPACE-NAME (WATCHLIST-NAME) #>**

#### Shutdown
1.  To exit **AniChan**, enter the `exit` command.

<br/>

### D.2: Estimate the time needed to translate a script
1.  Estimating the time needed to translate a script.
    1.  Prerequisite: Have a non-empty `script.txt` file in the current workspace folder.
        1.  Create a `.txt` file and name it "script" such that the file name with the file extension is `script.txt`, then fill it up with some content (i.e. random words and sentences).
        2.  Go to the folder containing `AniChan.jar`.
        3.  Open the `data` folder (if it does not exist, restart **AniChan**).
        4.  In the `data` folder, find and open the folder named after the current workspace (if the input prompt shows `Default (myWatchlist) #>`, then look for the folder named "Default" as that is the current workspace name).
        5.  Move `script.txt` into this folder (i.e. `data/Default/script.txt`).

    2.  Test case: `estimate script.txt`. <br/>
    Expected: 3 estimation timings are generated and printed.

    3.  Test case: `estimate script.txt -wph 550`. <br/>
    Expected: 1 estimation timing is generated and printed.
    
    4.  Other incorrect commands to try: 
        1.  `estimate`.
        2.  `estimate x` (where x is not a `.txt` file, or it is a file path).
        3.  `estimate script.txt -wph y` (where y is a negative number or a word).

> :memo: The file name (including extension) does not have to be `script.txt`, it is named as such for the convenience of testing.

<br/>

### D.3: Creating watchlist
1.  Creating a watchlist with a unique name.
    1.  Prerequisite: Watchlist list does not have a watchlist named "newWatchlist".
    
    2.  Test case: `watchlist -n newWatchlist`. <br/>
    Expected: A empty watchlist named "newWatchlist" is created, and a message indicating the watchlist was created successfully is printed.
        
    3.  Other incorrect commands to try: 
        1.  `watchlist -n`.
        2.  `watchlist -n x` (where x is a non-unique watchlist name, exceeds 30 characters, or it contains non-alphanumeric characters).

<br/>

### D.4: Listing all watchlist(s)
1.  Listing all created watchlist(s).
    1.  Prerequisite: The watchlist list in the current workspace is not empty.
    
    2.  Test case: `watchlist -l` <br/>
    Expected: The names of all watchlist in the current workspace is printed.
    
    3.  Other incorrect commands to try: 
        1.  `watchlist -l x` (where x is any additional parameters or values).

<br/>

### D.5: Selecting a watchlist to be the new active watchlist
1.  Selecting a watchlist to be the new active watchlist.
    1.  Prerequisite: The watchlist list in the current workspace has at least 2 watchlist, and **the first watchlist is the active watchlist**.
    
    2.  Test case: `watchlist -s 2` <br/>
    Expected: The second watchlist in the list becomes the new active watchlist, and a message indicating the name of the new active watchlist is printed.
    
    3.  Test case: `watchlist -s 1` <br/>
    Expected: An error message indicating that the selected watchlist is already the active watchlist is printed.
    
    4.  Other incorrect commands to try: 
        1.  `watchlist -s`
        2.  `watchlist -s x` (where x is a negative number, a word, or an additional parameter).

<br/>

### D.6: Deleting a watchlist
1.  Deleting a watchlist.
    1.  Prerequisite: The watchlist list in the current workspace has at least 2 watchlist, and **the first watchlist is the active watchlist**.

    2.  Test case: `watchlist -d 2` <br/>
    Expected: The second watchlist in the list is deleted, and a message indicating the name of the deleted watchlist is printed.
    
    3.  Test case: `watchlist -d 1` <br/>
    Expected: The first watchlist in the list is deleted, and a message indicating the name of the deleted watchlist, and the name of the new active watchlist is printed.
    
    4.  Other incorrect commands to try: 
        1.  `watchlist -d`
        2.  `watchlist -d x` (where x is a negative number, a word, or an additional parameter)
        