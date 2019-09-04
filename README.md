
This application is created to show how paging library can be used to load infinite lists and used in conjunction with Network fetch & Room Database.

This app gives the user the ability to show a list of all the issues of a specific GitHub repository, if a user choose an issue of interest the app will also display a detail screen to see what the particular issue is about.

Tech Stack Used
---------------
1. **Android Architecture Components** (Room Database, LiveData, Paging Library(PagedList, PagedListAdapter)).
2. Application Architecture is **MVVM**.
3. Networking using [Retrofit 2](https://github.com/square/retrofit).
4. Used **Kotlin**.
5. Used **Kotlin Coroutines** for Async Operations.
6. Used [Retrofit 2 Adapter for Kotlin Coroutine's Deferred type](https://github.com/JakeWharton/retrofit2-kotlin-coroutines-adapter).
7. Whole project is based on [AndroidX](https://developer.android.com/jetpack/androidx/).
8. Used [Dagger 2](https://github.com/google/dagger) for Dependency Injection.
9. [Picasso](https://github.com/square/picasso) for Image handling.


#### 1. List of Issues screen

This screen shows a list with all the issues of a repository of our choice.

    A. Implemented lazy loading of list items.
    B. Implemented a way to refresh the list.
    C. Loading indicator shows up while data being fetched.
    D. A way for a user to retry when loading fails.
    E. Restore screen state on screen orientation change.
    F. Tapping a list entry opens a detail view of the issue.
    G. Repository selection works by giving user the ability to use Search Repository option in the menu item. In case, the user has not entered anything then we use a default value of our preference.
        Eg. 'tensorflow' ~> https://github.com/tensorflow/tensorflow.


#### 2. Issue's Detail screen

The detail screen displays:

    A. Issue's title
    B. Issue's body
    C. Issue's status
    D. Author’s login
    E. created_at`


#### *TODOs*

    - Implement a search field which allows the user to filter issues list by keywords, which
    could be contained in either title or body of an issue.
    - Implement a filter for status of issues (opened and closed).
    - Implement a way to select the git repository.
    - Display corresponding issue comments in a detail view.
