# SpaceX Lauches

SpaceX Launches is a modular app built for a coding challenge.

It uses RxJava and Retrofit to make a call to get the spacex api to retrieve past launch data.

Other third party libraries used are:

- Koin for Dependency Injection
- android-youtube-player https://github.com/PierfrancescoSoffritti/android-youtube-player for embedding the youtube video for each launch

## Application Architecture

The app is feature based and modular, following Clean Architecture guidelines with MVVM Pattern

- app
- buildSrc
- common
- features
- navigtion

### Improvements

- Convert RxJava to Kotlin coroutines and use databinding between viewmodel and view
- Replace android-youtube-player (see known issues)
- Increase test coverage

### Known Issues

Leak Canary is reporting retained instances which point to the third party library used for embedding video. Replace this with official YouTube player, or another alternative as YouTube player has some limitiations.
