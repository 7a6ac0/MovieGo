<h1 align="center">MovieGo</h1>

MovieGo is a sample Android project using [The Movie DB](https://www.themoviedb.org/) API based on MVVM architecture.

## Features
* 100% Kotlin
* MVVM architecture
* Reactive pattern
* Android Architecture Components
* Jetpack Compose
* Kotlin Coroutines
* Single activity pattern
* Dependency injection

## Tech Stacks
* [Retrofit](http://square.github.io/retrofit/) + [OkHttp](http://square.github.io/okhttp/) - RESTful API and networking client.
* [Koin](https://insert-koin.io/) - Dependency injection.
* [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - A collections of libraries that help you design rebust, testable and maintainable apps.
    * [Room](https://developer.android.com/training/data-storage/room) - Local persistence database.
    * [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) - Pagination loading for RecyclerView.
    * [ViewModel](https://developer.android.com/reference/androidx/lifecycle/ViewModel) - UI related data holder, lifecycle aware.
    * [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Observable data holder that notify views when underlying data changes.
* [Navigating with Compose](https://developer.android.com/jetpack/compose/navigation) - Navigate between composables.(Upcoming)
* [Jetpack Compose](https://developer.android.com/jetpack/compose) - Declarative and simplified way for UI development.
* [Coroutine](https://developer.android.com/kotlin/coroutines) Concurrency design pattern for asynchronous programming.
* [Coil](https://github.com/coil-kt/coil) - Image loading.
* [Timber](https://github.com/JakeWharton/timber) - Extensible API for logging.

## API Key 🔑
You will need to provide developer token to fetch the data from TMDB API.
* Generate a new token (v4 auth) from [here](https://www.themoviedb.org/settings/api). Copy the token and go back to Android project.
* Create a new kotlin file `ApiKey.kt` in path `./buildSrc/src/main/kotlin/`.
* Define a constant `TMDB_API_TOKEN` with the double quotes, it looks like

```kotlin
const val TMDB_API_TOKEN = "\"eyJhbJ**************************5n-f\""
```

```kotlin
defaultConfig {
    ...
    buildConfigField("String", "TMDB_API_TOKEN", TMDB_API_TOKEN)
    ...
}
```

## LICENCE

```
Copyright (c) 2022 Tabaco Wang

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
