# Movies
This app fetches the information from [www.themoviedb.org](www.themoviedb.org). Users can get the information of the top movies and tv shows. Users can also can search for movies, tv shows, person and similar movies. It allow the users to favourite their movies or tv-shows. The fetched information is also available offlline.

#### Motivation
- Cover maximum topics in single project.
- Dagger2, RxJava
- Application Architecture
- Offline Loading with Paging and View Model
- Instagram like bottom navigation and maintain fragment stack.

#### Libraries
- [Dagger2](https://developer.android.com/training/dependency-injection/dagger-android)
- [RxJava](https://github.com/ReactiveX/RxAndroid)
- [Room](https://developer.android.com/topic/libraries/architecture/room)
- [Paging](https://developer.android.com/topic/libraries/architecture/paging)
- [LiveData and ViewModel](https://developer.android.com/topic/libraries/architecture)
- [Data Binding](https://developer.android.com/topic/libraries/data-binding)
- [Android X](https://developer.android.com/jetpack/androidx)
- [Retrofit](http://square.github.io/retrofit)
- [Glide](https://github.com/bumptech/glide)

#### Demo
<img src="demo.gif" width="40%">

#### Project Setup

1. Clone this project.
2. Signup for [TMDB](https://www.themoviedb.org/account/signup) account to generate your `api_key`
3. Create new file `graddle.properties` and add `MovieApiKey="<api_key>"`

### License

```
   Copyright (C) 2020 Rajat Sangrame

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```