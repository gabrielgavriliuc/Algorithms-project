# -*- Makefile -*-

cc = gcc
all: bin/main bin/tests

CFLAGS = -g -Wall -Wextra -Wpedantic -Wconversion

INCLUDES = src/*.h

COMMON_DEPS = $(INCLUDES) Makefile

build/%.o: src/%.c $(COMMON_DEPS)
	$(CC) $(CFLAGS) -c $< -o $@

bin/main: build/ordered_array.o build/ordered_array_main.o $(COMMON_DEPS)
	$(CC) -o bin/main build/ordered_array.o build/ordered_array_main.o

bin/tests: build/ordered_array_tests.o build/ordered_array.o build/unity.o $(COMMON_DEPS)
	$(CC) -o bin/tests  build/ordered_array_tests.o build/ordered_array.o build/unity.o

clean:
	rm -f build/* bin/*
