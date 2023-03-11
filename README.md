# GreedyGameAssigment

MusicWiki Android App

MusicWiki is an unofficial Last.fm app that allows users to browse and discover music genres, top albums, top tracks, and top artists. This document outlines the features, assumptions, and decisions made while developing the app.

Features

The app has the following features:

Display list of genres available: The app displays the top 10 genres initially, and when the user clicks on the expand button, the entire list is shown on the same screen.

Genre information page: Clicking on the genre takes the user to a page that contains information about the genre. It includes the genre as the title, description of the genre, and lists top albums, top tracks, and top artists as different sections.

Albums page: Clicking on an album from the genre information page displays the cover image, title, and artist information. It also has the description and the genres. Clicking on the genre displays the details about the genre, similar to the flow in the first screen.

Artists page: Clicking on an artist from the genre information page displays the image, title, play count, followers, description, a list of top tracks and top albums, and the genres. Clicking on the genre displays the details about the genre, similar to the flow in the first screen. Clicking on the album displays its information.

Assumptions

The following assumptions were made while developing the app:

The Last.fm API provides all the required information to build the app.

The app requires an internet connection to fetch data from the API.

The app is designed for mobile devices and is optimized for smaller screens.

Decisions

The following decisions were made while developing the app:

The app is built using Kotlin as the programming language.

The app uses the MVVM architecture pattern to separate the presentation logic from the business logic.

The app uses Retrofit to fetch data from the Last.fm API.

The app uses Glide to load images.

The app uses RecyclerView to display lists of data.

The app uses LiveData to observe changes in data and update the UI.

The app uses Kotlin Coroutines to perform asynchronous operations.

The app uses Data binding to bind views to their respective components.

The app uses a single activity to create a cohesive user experience.


Conclusion

MusicWiki is an Android app that allows users to discover and explore music genres, top albums, top tracks, and top artists. The app uses the Last.fm API to fetch data and provides an intuitive user interface that follows Material Design guidelines. The app uses the MVVM architecture pattern, Retrofit, Glide, RecyclerView, Navigation component, LiveData, Kotlin Coroutines, and data binding to create a robust and modern app.
