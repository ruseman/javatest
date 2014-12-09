#JavaTest

This application is my final project for CSC 111.  The goal was to write a GUI application that would quiz the user on Java related questions.

##Questions

The questions are hosted on Github [here](https://raw.githubusercontent.com/Commador/JavaTestQuestions/master/questions.json).  They are written in [JSON](http://www.json.org/) format, and are then interpreted programmatically.

##Compiling

See [here](COMPILING.md)

##Release

The releases can be found [here](https://github.com/Commador/JavaTest/releases).  Unless otherwise stated, please be sure to use the 'exec' version of the jar.

##Dependencies

While the project does depend on the [Guava](https://code.google.com/p/guava-libraries/) library and the [GSON](https://code.google.com/p/google-gson/) library, both of these dependencies are handled by Gradle's dependency management.  The source ships with a Gradle wrapper, so there should be no concern over whether or not you have Gradle installed.  Lastly, this application makes use of VoxioLib, my *very* WIP java library.  VoxioLib can be found [here](https://github.com/voxio/lib).

##Licensing

See [here](LICENSE.md)

##Goals

Despite having completed the assignment, I'm planning on reworking this application with a few features
* Question formats (Multiple choice, true/false, etc.)
* Load custom questions files from url
* Cache questions in a place
* Rework class structure for less nonsense

##Credits

* Tim Miller - Primary developer
* Google - Development of the Guava and GSON libraries
* Gradleware - Development of Gradle, which has made dependency management and build distribution so much easier and so much harder
* [StackOverflow](http://stackoverflow.com/) - A fantastic community that I've relied on for years

