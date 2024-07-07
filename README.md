# Kotlin-C++ ZIP Integration
<div style="display: flex; justify-content: center;">
    <img src="https://github.com/fahaddhabib/KotlinCppZipIntegration/blob/mainbranch/assets/1.jpg" alt="1" width="200">
    <img src="https://github.com/fahaddhabib/KotlinCppZipIntegration/blob/mainbranch/assets/2.jpg" alt="2" width="200">
</div>
Overview

This project demonstrates a Kotlin program that downloads a ZIP file from a web server, unpacks the ZIP file, and integrates the contained library (programmed in C++) with the Kotlin program. The library contains a function that displays text passed by the Kotlin program on the screen, queries the OK button, and when the OK button is pressed, sends a message back to the Kotlin program.

Status: Done
C++ Library in ZIP
Implementation Details
Steps Taken:

    Created and Compiled C++ Library:
        Used Android Studio and NDK to create and compile the C++ library.

    Prepared the ZIP File:
        Zipped all architecture-specific (armeabi-v7a, arm64-v8a, etc.) lib.so files and stored the ZIP on Google Drive.

    Wrote the Kotlin Code:
        Developed a Kotlin program that:
            Downloads the ZIP file from the web server.
            Unpacks the ZIP file.
            Integrates the library contained in it.
            Operates on-the-fly, without interruption and manual intervention.

Functionality:

The library includes:

    getMessage(): Returns the message "Message from C++".
    passMessage(userInput: String): Receives user input from the Kotlin program and handles it within the library.
