<h1 align="center">Store</h1></br>

<p align="center">
🗳 Easier than SharedPreferences with DataStore(Jetpack)
</p>



<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
</p>


### Dependency Gradle 

Add below codes to your **root** `build.gradle` file (not your module build.gradle file).

```groovy
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```

And add a dependency code to your **module**'s `build.gradle` file

```groovy
dependencies {
  implementation 'com.github.kennethss:store:1.0.1'
}
```

### Dependencies

```groovy
implementation "androidx.lifecycle:lifecycle-runtime-ktx:2.3.1"
implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1"
```



## UseCase

### Activity & Fragment & ViewModel

```kotlin
setStore("SOME_KEY", "some primitive type")

getStore<String>("SOME_KEY") { value ->
	//do something
}
```