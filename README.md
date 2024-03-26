# Stocki

Stocki is an Android application designed to provide users with real-time stock market information and ticker details.

## Features

- **Real-time Stock Market Prices:** View real-time updates on stock market prices.
- **Ticker Information:** Access comprehensive information about different types of tickers in the market.
- **Search Page:** Easily search for specific stocks or companies.
- **Google Sign-In and Sign-Up:** Seamless authentication using Google accounts.
- **Firebase Integration:** Utilizes Firebase services for enhanced authentication and data management.
- **Profile Page:** Personalized profile page for users to manage their preferences and settings.

- 
## User Interface (UI) with Jetpack Compose

- **Stocki's** user interface is built using Jetpack Compose, the modern toolkit for building native Android UI. Instead of traditional XML layout files, Jetpack Compose allows for UI construction using composable functions, which offer a more declarative and flexible approach to UI development.
With Jetpack Compose, UI components are written as functions that describe their own UI structure and behavior. This enables dynamic UI updates, seamless state management, and improved code organization compared to XML-based layouts.
By leveraging Jetpack Compose, **Stocki** achieves a more streamlined and efficient UI development process, empowering developers to create engaging and responsive user interfaces with less boilerplate code.

## Architecture

The app follows the Model-View-Intent (MVI) architecture pattern, which helps in maintaining a clear separation of concerns and facilitates better testability and maintainability. Additionally, it incorporates the following components:

- **Singleton:** Certain components, such as the application context or network client, are implemented as singletons to ensure a single instance throughout the application's lifecycle.
- **Dependency Injection with Hilt:** Hilt is used for dependency injection, providing a standard way to manage dependencies and facilitate testing.
- **Repository Pattern:** The repository pattern is employed to abstract data sources, making it easier to switch between different data providers or sources without affecting the rest of the application.
- **Retrofit:** Retrofit is used for making network requests, offering a type-safe API for interacting with RESTful web services.
- **Room:** Room is utilized for local data storage, providing an abstraction layer over SQLite and enabling efficient data persistence.
- **Coroutines:** Coroutines are used for asynchronous programming, allowing for non-blocking, concurrent code execution with simplified error handling and cancellation.

- 
## API Integration

**Stocki** utilizes the Polygon.io Stocks API to fetch real-time market data and other relevant information. The Polygon.io Stocks API offers a wide range of REST endpoints that provide access to various data sets
By leveraging the Polygon.io Stocks API, **Stocki** ensures reliable and comprehensive data for users, enabling them to make informed decisions about stock investments and market trends.



## Usage

To use Stocki, follow these steps:

1. Clone the repository to your local machine.
2. Open the project in Android Studio.
3. Build and run the project on an Android emulator or physical device.

Ensure you have the necessary API keys and configurations set up for Firebase authentication and other services.

## Contributions

Contributions to Stocki are welcome! Feel free to submit bug reports, feature requests, or pull requests to help improve the application.

