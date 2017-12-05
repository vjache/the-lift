all: compile run


run:
	java -jar build/libs/the-lift-1.0-SNAPSHOT.jar

compile:
	./gradlew clean build
