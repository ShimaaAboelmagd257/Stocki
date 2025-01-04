# Stocki

**Stocki** is an Android application designed to provide users with real-time stock market information and ticker details.

## Features

- **Real-time Stock Market Prices:** View real-time updates on stock market prices.
- **Ticker Information:** Access comprehensive information about different types of tickers in the market.
- **Search Page:** Easily search for specific stocks or companies.
- **Google Sign-In and Sign-Up:** Seamless authentication using Google accounts.
- **Firebase Integration:** Utilizes Firebase services for enhanced authentication and data management.
- **Profile Page:** Personalized profile page for users to manage their preferences and settings.
- **Virtual Trading:** Simulate stock trading with virtual currency to practice and test your trading strategies.
- **News Feed:** Stay updated with the latest news and developments in the stock market.
- **Performance:** Track and analyze the performance of your virtual portfolio over time.
- **Watchlist:** Save and monitor tickers that you want to watch.
- **Explore Features:** Provides cards explaining the basics of stock investment for beginner investors.
  
## User Interface with Jetpack Compose

- **Stocki's** user interface is built using Jetpack Compose, the modern toolkit for building native Android UI. Instead of traditional XML layout files, Jetpack Compose allows for UI construction using composable functions.

   By leveraging Jetpack Compose, **Stocki** achieves a more streamlined and efficient UI development process, empowering developers to create engaging and responsive user interfaces with less boilerplate code.

## Architecture

The app follows the Model-View-Intent (MVI) architecture pattern, which helps in maintaining a clear separation of concerns and facilitates better testability and maintainability. Additionally, it incorporates the following components:

- **Singleton:** Certain components, such as the application context or network client, are implemented as singletons to ensure a single instance throughout the application's lifecycle.
- **Dependency Injection with Hilt:** Hilt is used for dependency injection, providing a standard way to manage dependencies and facilitate testing.
- **Repository Pattern:** The repository pattern is employed to abstract data sources, making it easier to switch between different data providers or sources without affecting the rest of the application.
- **Retrofit:** Retrofit is used for making network requests, offering a type-safe API for interacting with RESTful web services.
- **Room:** Room is utilized for local data storage, providing an abstraction layer over SQLite and enabling efficient data persistence.
- **Coroutines:** Coroutines are used for asynchronous programming, allowing for non-blocking, concurrent code execution with simplified error handling and cancellation.


## API Integration

**Stocki** utilizes the Polygon.io Stocks API to fetch real-time market data and other relevant information. The Polygon.io Stocks API offers a wide range of REST endpoints that provide access to various data sets
By leveraging the Polygon.io Stocks API, **Stocki** ensures reliable and comprehensive data for users, enabling them to make informed decisions about stock investments and market trends.

## Usage

To use Stocki, follow these steps:

1. Clone the repository to your local machine.
2. Open the project in Android Studio.
3. Build and run the project on an Android emulator or physical device.
# Stocki Screenshots

## Splash Screens
![Splash Screen 1](https://github.com/user-attachments/assets/051d40c4-1c1f-4356-8ece-e2d5b9ddd601)
![Splash Screen 2](https://github.com/user-attachments/assets/f3baa259-217e-4d42-89c0-a9682088cf08)
![Splash Screen 3](https://github.com/user-attachments/assets/a4027242-13c5-45f6-9263-6629de08902b)

## Entrance Screen
![Entrance Screen](https://github.com/user-attachments/assets/722aa98a-a472-4cb9-a67f-82ce3380462c)

## Sign In and Sign Up
![Sign In Screen](https://github.com/user-attachments/assets/d02c20c9-1670-4def-8b76-d229bb483395)
![Sign Up Screen](https://github.com/user-attachments/assets/020c4c4e-d046-4532-b175-dedd8f336104)

## Market and Stock Items
![Market Screen](https://github.com/user-attachments/assets/784b0daf-b6c6-458f-83d3-7f30bf7b53f0)
![Stock Item Screen 1](https://github.com/user-attachments/assets/960c2799-6304-4c00-ae60-305045c7cff1)
![Stock Item Screen 2](https://github.com/user-attachments/assets/4a66bcb8-bec1-446c-b04b-13f0d2a89610)

## More Options
![More Options Screen](https://github.com/user-attachments/assets/8228aaf4-2479-4423-bcca-1fbacf5893c3)

## Explore Section
![Explore Screen](https://github.com/user-attachments/assets/d18e9fd5-1bf1-41db-8f86-41f83aa96b62)
![Explore Item Screen](https://github.com/user-attachments/assets/336a4f65-1c70-4253-abe4-c0a282ecd994)


Ensure you have the necessary API keys and configurations set up for Firebase authentication and other services.

## Contributions

Contributions to **Stocki** are welcome! Feel free to submit bug reports, feature requests, or pull requests to help improve the application.

