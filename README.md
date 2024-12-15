# Submission: Fundamental Android Training - Dicoding

## Introduction

This repository contains the source code for an Android application developed as part of the **Dicoding Fundamental Android Training** program. The project showcases the integration of core Android functionalities, offering a comprehensive learning experience for developers.

## Key Features

- **Search Functionality**: Implements `SearchView` to allow users to search for data within the application.
- **Tab Navigation**: Leverages `TabLayout` and `ViewPager2` for seamless navigation between different application pages.
- **Network Requests (JSON)**: Utilizes LoopJ and Retrofit libraries to perform network requests and handle JSON responses efficiently.
- **CRUD Database Management**: Integrates SQLite and Room to perform Create, Read, Update, and Delete (CRUD) operations for persistent data management.
- **Data Storage (Key-Value)**: Employs DataStore to store and retrieve user preferences and settings using a key-value pair approach.

## Prerequisites

Before starting, ensure you have the following:

- A basic understanding of Android development concepts (Activities, Fragments, Layouts, etc.).
- Familiarity with Java or Kotlin programming languages.
- Android Studio installed with the necessary tools and libraries.

## Setting Up the Project

Follow these steps to set up the project:

1. Clone this repository to your local machine:
   ```bash
   git clone https://github.com/esnanta/UserGithubAPI.git
   ```
2. Open the project in Android Studio:
   - Select **File > Open...** and navigate to the cloned repository.
3. Sync the Gradle files to download required dependencies.

## Running the Application

1. Connect an Android device or start an emulator.
2. In Android Studio, click the **Run** button or select the appropriate configuration to run the app.

## Project Structure

```plaintext
├── app
│   ├── src
│   │   ├── main
│   │   │   ├── java/com/example/app  # Application source code
│   │   │   ├── res                   # Resources (layouts, drawables, strings, etc.)
│   │   ├── test                      # Unit tests
│   │   ├── androidTest               # Instrumented tests
├── build.gradle                      # Project build configuration
```

## Explanation of Key Technologies

### SearchView
Allows users to interact with a search bar to query specific data. Results are dynamically updated based on the search term.

### TabLayout & ViewPager2
Provides a tabbed navigation system that enables users to switch between different application pages with ease.

### LoopJ & Retrofit
Manages network requests and JSON parsing:
- **LoopJ**: Ideal for asynchronous network operations.
- **Retrofit**: Simplifies API calls and response handling, making it a robust solution for RESTful services.

### SQLite & Room
Facilitates persistent storage using:
- **SQLite**: A lightweight database for structured data storage.
- **Room**: An abstraction layer over SQLite, simplifying database access and reducing boilerplate code.

### DataStore
Used for storing and retrieving key-value data, ensuring efficient management of user preferences and application settings.

## Further Exploration and Customization

Feel free to:

- Explore the codebase to understand the implementation details.
- Modify the project to extend functionalities or adapt it to your needs.
- Refer to the documentation of the libraries used:
  - [LoopJ](https://loopj.com/)
  - [Retrofit](https://square.github.io/retrofit/)

## Contribution Guidelines

Contributions are welcome! To contribute:

1. Fork this repository.
2. Create a new branch for your feature or bug fix.
3. Submit a pull request with a detailed explanation of your changes.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.

## Conclusion

This project provides a practical foundation for learning Android development concepts such as `SearchView`, `TabLayout` with `ViewPager2`, network requests (using LoopJ and Retrofit), database management (with SQLite and Room), and data storage (through DataStore). It serves as an excellent starting point for building more complex Android applications. Feel free to explore, customize, and contribute to this repository!
