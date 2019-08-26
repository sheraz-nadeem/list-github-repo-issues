# Joyn - Coding Challenge Android

The goal is to implement a native android app using the latest tech stack and android's architecture patterns. This app gives the user the posibility to show a list of all the issues of a specific GitHub repository, if a user choose an issue of interest the app will also display a detail screen to see what the particular issue is about.

### Functional Requirements

#### 1. List of Issues screen

This screen should show a lists with all the issues of a repository of your choice (***See:*** _G. Repository selection_):

    A. Implement lazy loading of list items.
    B. Implement a way to refresh the list.
    C. Loading indicator while data being fetch.
    D. A way for a user to retry when loading fails.
    E. Restore screen state on screen orientation change.
    F. Tapping a list entry should open a detail view of the issue.
    G. Repository selection: give the user the option to enter the name/repository_id he wants to see. In case the user has not enter it provide a default value of your preference.
        Eg. 'tensorflow' ~> https://github.com/tensorflow/tensorflow.

#### 2. Issue's Detail screen

The detail screen should display:

    A. Issue's title
    B. Issue's body
    C. Issue's status
    D. Author’s login
    E. created_at`

### C. Deliverables

    - Instructions to build the app.
    - Assembled Apk file under a 'build' folder, placed in the root of the project.

_Be prepared to show the code and the app demo during the follow up interview._

### Evaluation criterias
We will pay extra attention to the following aspects of your app:

- App architecture. Please implement architecture you would use in a production app.
- Modularity of your code. Separation of concerns.
- Testability. Cover your code with automated tests.
- General code style and code cleanliness.
- Material design. Follow Android design guidelines.

__Note:__ _You should strongly prefer fulfilling these requirements than doing the optional tasks._

### Relevant links
_[https://developer.github.com/v3/](https://developer.github.com/v3/)_

#### *Optional Tasks*

    - Implement a search field which allows the user to filter issues list by keywords, which
    could be contained in either title or body of an issue.
    - Implement a filter for status of issues (opened and closed).
    - Implement a way to select the git repository.
    - Display corresponding issue comments in a detail view.
