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

[Nineoldandroids](https://mvnrepository.com/artifact/com.nineoldandroids/library/2.4.0) (Dependency
for EaseGraph)

[Material Calendar View](https://github.com/Applandeo/Material-Calendar-View) (Base Widget for Bill
Calendar)

Thus, added the following to the `app/build.gradle` file

```kotlin
dependencies {
    implementation("com.applandeo:material-calendar-view:1.9.2")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("com.github.blackfizz:eazegraph:1.2.2@aar")
    implementation("com.nineoldandroids:library:2.4.0")
}
```

### Installing

* Clone Github repository
* Make any desired modifications files/folders
* Run application (launch `CNIT_355_PROJECT.app` module) using an android device (physical or emulated) running Android SDK 34 or higher

As mentioned previously, the above libraries will need to be downloaded to the project for the application to run. Therefore, they must be present in the `app/build.gradle` file.

## Project Organization

The project contains the following classes:

- `MainActivity`
    - Controls the initial log in screen
- `SignUpActivity`
    - Controls the creation of new user accounts (sign-up screen)
- `OverviewActivity`
  - Main content of the application
  - Consists of the navigation button manu and the fragment container
  - Enables switching of fragments as per user interactions with the navigation menu
- `OverviewFragment`
  - Fragment displayed within the `OverviewActivity`
  - Shows an overview of the user account's finances
- `BillsCalendarFragment`
  - Fragment displayed within the `OverviewActivity`
  - Shows user's monthly expenses, with an indication of what is due on which day of the month
  - Allows users to add new monthly expenses
- `DebtTrackerFragment`
  - Fragment displayed within the `OverviewActivity`
  - Displays a list of the user's debts, and how much money they need to pay each month to keep up with paying it off
  - Allows users to add new debts to the list
- `NewDebtPayoffFragment`
  - Fragment displayed within the `OverviewActivity`
  - Fragment used to create a new debt and add it to the user's database records
  - Called by user interactions with the `DebtTrackerFragment`
- `ReceiptScannerFragment`
  - Fragment displayed within the `OverviewActivity`
  - Gathers user text input and inserts new expenses into the user's records
- `Security`
  - Hashes string passwords used in user accounts
  - Used by the `MainActivity` and `SignUpActivity` classes
- `DatabaseHelper`
  - Database interface object used throughout the application to handle all communications between the app and the app's SQLite database.
  - Created specifically in `MainActivity`, `SignUpActivity` and `OverviewActivity`
    - The instance object in `OverviewActivity` is also used by each fragment (`BillsCalendarFragment`, `DebtTrackerFragment`, `NewDebtPayoffFragment`, `OverviewFragment`, `ReceiptScannerFragment`) to interface with the database to insert/read records
- `BillAdapter`
  - RecyclerView adapter used in `BillsCalendarFragment`
  - Displays a list of `Bill` objects corresponding to the user's bills (on a specified day)
- `Bill`
  - Container object used to organize data on a bill in `BillAdapter`, `DatabaseHelper`, and `BillsCalendarFragment` 
- `DebtAdapter`
  - RecyclerView adapter used in `DebtTrackerFragment`
  - Displays a list of `Debt` objects corresponding to the user's debts
- `Debt`
  - Container object used to organize data on a debt in `DebtAdapter`, `DatabaseHelper`, and `DebtTrackerFragment`

## Authors

Alex Voelker (voelker0@purdue.edu)

Carissa Bauerband (cbauerba@purdue.edu)

Megan Clecak (mclecak@purdue.edu)
