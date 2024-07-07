Test Task:

<div style="display: flex; justify-content: center;">
    <img src="https://github.com/fahaddhabib/KotlinCppZipIntegration/blob/mainbranch/assets/1.jpg" alt="1" width="200">
    <img src="https://github.com/fahaddhabib/KotlinCppZipIntegration/blob/mainbranch/assets/2.jpg" alt="2" width="200">
</div>

You write a Kotlin program that downloads a ZIP file from a web server, unpacks this ZIP file and docks the library contained in it (programmed by you in C++) to your Kotlin program.

The library contains a function that displays the text passed by the Kotlin program on the screen, queries the OK button and when the OK button is pressed, the library sends a message back to the Kotlin program
Status: Done

[C++ library in zip](https://drive.google.com/file/d/1EMxJYf8uutC-EO1Xm97AwLaTSSBzGzQA/view?usp=drive_link)

What I did?

1: I created/compiled a cpp library in android studio using NDK
2: zip all the Architectures (armeabi-v7a, arm64-v8a, etc) lib.so and store on google drive

3: Wrote a code as per instructions.
a) Kotlin program that downloads a ZIP file from a web server, unpacks this ZIP file and docks the library contained in it (All on the fly, without interruption and without manual intervention.)

What is happening?
Library contains a function  getMessage() which statically set to "Message from C++" to message from library and a passMessage to pass userInput to Library
 


