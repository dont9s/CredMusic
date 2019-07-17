Android GojekWeatherApplication

A weather application for getting the current weather and forecast for 4 upcoming days.


Major implementations:

1. Dagger
2. MVVM architecture pattern
3. DataBinding 
4. Retrofit for network calls
5. JUNit tests
6. Espresso UI Automated Tests

Android architecture components are being used in this project, which includes.

1. LiveData
2. Lifecycle
3. ViewModel
4. DataBinding

Basic functionality of the app is to get the current location of the user using FusedLocationProvider library, provided that the user has provided ACCESS_FINE_LOCATION permission to the app. Apixu Rest api is used for getting the forecast and current temperature. 

Project is divided in packages:

1. adapter
2. di
3. helper
4. service
5. view
6. viewmodel


1. adapter -> contains the recycler view adapter for forecast list.


2. di -> consist of 
	a. builder
	b. component
	c. module


	a. builder -> consist of ContributeAndroidInjector for injecting into MainActivity and WeatherFragment

	b. component -> Consist of AppComponent that build the dependency graph , binding AppModule, ActivityBulder, AndroidSupportInjection

	c. module -> consist AppModule providing context, PrefManager, Gson , and Retrofit instance for calling the api


3. helper -> consist of : 

	a. Constant -> constants are set in it.
	b. CurrentLocationListener -> Managing the FusedLocationProvider for listening to location callbacks, lifecycle aware as it extends LiveData, so if no observer is there in the state of onStart or onResume, then it will not update the ui, hence no chances of NullPointerException.
	c. DateUtil -> for converting the date to Day of Week.
	d. PermissionManager -> for checking the current permission state and asking fo user permission
	e. PermissionStatus -> enum class for supported PermissionStatus.
	f. PrefManager -> Save the preference for location
	g. WeatherLoadStatus -> enum class  for supported weather load status.



4. service -> consist of model packege for model classes of Weather
				repository package for ApixuService for retrofit instance
				WeatherRepository for SINGLE SOURCE OF TRUTH for getting LiveData of weather for observing in the view model


5. view -> base package consist of BaseActivity and BaseFragment for containing common code
			custom consist of LoadingSpinner that animates the ic_loading icon 
			ui package consist of :

				MainAcitivity, MainActivityModule, MainActivityViewModule
				WeatherFragment, WeatherFragmentModule, WeatherFragmentViewModel

6. viewmodel -> consist of WeatherViewModelFactory and WeatherViewModel



GojekApplication extends DaggerApplication for overriding function that returns AndroidInjector for injecting activity  classes.



TEST MODULES

Espresso Tests are included in UiTets class

JUnit tests are included in AppTest class 




