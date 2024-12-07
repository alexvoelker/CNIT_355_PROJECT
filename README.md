# Centsible: The Finance App

This application was created for an easy, beginner-friendly college student/young adult finance app. 

## Description

Centsible is an easy to use android app that allows the user to track their spending and budget habits. The app contains a login page where the user may log in or create a new account. Once the user is logged in, the user is able to see a budget overview, which has current spending and debt that is stored per their username. The user is able to create new debts, which updates the overview. Next, the user is able to see all bills for the month and track which date the bills are due. The user is also able to use the Debt Payoff to track specific loans/debts and "what-if" scenario of paying off the debt by specified months. The user is also able to add in debts on the expense tracker, which allocates the expense to the specific spending category. The overview of monthly expenditures updates as data is added to the various pages.

## Getting Started

### Dependencies

* Must use up-to-date Android Studio SDK 34 and Ladybug 
* Windows 10
* Libararies used:

[EazeGraph](https://github.com/paulroehr/EazeGraph) (Pie Chart)

[Nineoldandroids](https://mvnrepository.com/artifact/com.nineoldandroids/library/2.4.0) (Dependency for EaseGraph)

[Material Calendar View](https://github.com/Applandeo/Material-Calendar-View) (Base Widget for Bill Calendar)

Thus, added the following to the [app/build.gradle] file
```kotlin
dependencies {
    implementation("com.applandeo:material-calendar-view:1.9.2")
    implementation ("androidx.cardview:cardview:1.0.0")
    implementation ("com.github.blackfizz:eazegraph:1.2.2@aar")
    implementation ("com.nineoldandroids:library:2.4.0")
}
```

### Installing

* Github repository 
* Any modifications needed to be made to files/folders
  
As mentioned previously, the above libraries will need to be added to the required file. Without these added dependencies, the app will not function. 

## Authors

Alex Voelker (voelker0@purdue.edu) 

Carissa Bauerband (cbauerba@purdue.edu)

Megan Clecak (mclecak@purdue.edu)
