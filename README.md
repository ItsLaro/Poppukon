# Project 2 - **Poppukо̄n**

**Poppukо̄n** shows the latest movies currently playing in theaters. The app utilizes the Movie Database API to display images and basic information about these movies to the user.

Time spent: 20 hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can **scroll through current movies** from the Movie Database API
* [x] Display a nice default [placeholder graphic](https://guides.codepath.org/android/Displaying-Images-with-the-Glide-Library#advanced-usage) for each image during loading
* For each movie displayed, user can see the following details:
    * [x] Title, Poster Image, Overview (Portrait mode)
    * [x] Title, Backdrop Image, Overview (Landscape mode)
* [x] Allow user to view details of the movie including ratings and popularity within a separate activity

The following **stretch** features are implemented:

* [x] Improved the user interface by experimenting with styling and coloring.
* [x] Apply rounded corners for the poster or background images using [Glide transformations](https://guides.codepath.org/android/Displaying-Images-with-the-Glide-Library#transformations).
* [x] Apply the popular [View Binding annotation library](http://guides.codepath.org/android/Reducing-View-Boilerplate-with-ViewBinding) to reduce boilerplate code.
* [x] Allow video trailers to be played in full-screen using the YouTubePlayerView from the details screen.

## Demo
Here's a walkthrough of implemented user stories:
<p align="center">
  <img src= demo/demo.gif width="500">
</p>

## Notes
New branch ``PostD4`` reflects most recent changes (all extra User Stories) after the submission deadline on CodePath's Day 4. This work (time & features) is separate/additional to proposed user stories stated above. Additional features include:
* [x] Further enhancements to the overall look of the UI.
* [x] Created matching icon & logo for the app (featuring assets by <a href="https://www.flaticon.com/authors/freepik">Freepik at flaticon</a>.
   * Icon: <p align="left"> <img src= demo/icon_poppukon.png width="100"> </p>
   * Logo: <p align="left"> <img src= demo/title_poppukon.png width="700"> </p>
* [x] Popularity bar design is now more notable and changes color depending on popularity level.
* [x] Added menu to the action bar with the ability to sort movies by: popularity (default), alphabetical order, average rating or release date.

Here's the preview:
<p align="center">
  <img src="https://github.com/ItsLaro/Poppukon/blob/PostD4/demo/demo2.gif" width="500">
</p>

## Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Glide](https://github.com/bumptech/glide) - Image loading and caching library for Android


## License

    Copyright 2020 Ivan A. Reyes

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
