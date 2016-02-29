# super simple makefile
# call it using 'make NAME=name_of_code_file_without_extension'
# (assumes a .java extension)
NAME = "Breakout"

all:
	@echo "Compiling..."
	javac *.java Model/*.java View/*.java Misc/*.java

run: all
	@echo "Running..."
	java $(NAME)

clean:
	rm -rf *.class
	rm -rf View/*.class
	rm -rf Model/*.class
	rm -rf Misc/*.class
