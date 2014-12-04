#Requirements  
None!  The source comes with the Gradle wrapper.  If you have your own Gradle installation, simply use `gradle` instead of `gradlew`

##Build Tasks  
In order to build, Gradle offers a few different configurations.  The default and most basic is `build`, and `build` will provide you with a runnable jar.  There are however several other options available.  

1. `installApp`, which installs the application to the `build/install` directory.  
2. `distZip`, which creates a `.zip` archive with the install files included.  
3. `distTar`, which creates a `.tar` archive with the install files included.  

If you'd rather not build at all, you can just run the application directly, by using `run`.  This is almost entirely untested, so I'm not sure how or where the application will store it's information, but it shouldn't cause it to break too badly.

In order to use any of these tasks, simple follow your OS specific instructions, and use the task you desire instead of `build`.

##Build Instructions  
Simply follow your OS specific instructions, and everything *should* work fine.

###Windows  
Simply run `gradlew build`  
The executable jar, along with the source and javadoc jar, will be located in `build/libs`

###Mac  
Simply run `sh gradlew build`
The executable jar, along with the source and javadoc jar, will be located in `build/libs`.

###Linux  
Simply run `gradlew build`  
The executable jar, along with the source and javadoc jar, will be located in `build/libs`  
If that doesn't work, try following the [Mac instructions](COMPILING.md#Mac)

###Notes
The runnable jar will be located in `build/libs`, and will be named javatest-VERSION-exec.jar.  None of the other jars are runnable.