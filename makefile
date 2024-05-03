# Compiler and flags
JAVAC = javac
JFLAGS = -d .

# Source directory
SRCDIR = .

# Find all Java source files
SOURCES = $(wildcard $(SRCDIR)/*.java)

# Generate corresponding class file names
CLASSES = $(patsubst $(SRCDIR)/%.java,%.class,$(SOURCES))

# Default target
all: $(CLASSES)

# Compile Java source files
%.class: $(SRCDIR)/%.java
	$(JAVAC) $(JFLAGS) $<

# Clean the output directory
clean:
	rm -rf *.class

.PHONY: all clean