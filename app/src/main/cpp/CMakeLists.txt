cmake_minimum_required(VERSION 3.4.1)

# Define the library
add_library(MyLibrary SHARED MyLibrary.cpp)

# Include the JNI headers
include_directories(${CMAKE_CURRENT_SOURCE_DIR})

find_library(log-lib log)

target_link_libraries(MyLibrary ${log-lib})
