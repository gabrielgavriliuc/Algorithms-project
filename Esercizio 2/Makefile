# -*- Makefile -*-

cc = gcc
all: bin/tests bin/main

CFLAGS = -g -Wall -Wextra -Wpedantic -Wconversion

INCLUDES = src/*.h

COMMON_DEPS = $(INCLUDES) Makefile

build/%.o: src/%.c $(COMMON_DEPS)
	$(CC) $(CFLAGS) -c $< -o $@

bin/main: build/edit_distance_library.o build/edit_distance_main.o $(COMMON_DEPS)
	$(CC) -o bin/main build/edit_distance_library.o build/edit_distance_main.o

bin/tests: build/edit_distance_library.o build/edit_distance_library_test.o build/unity.o $(COMMON_DEPS)
	$(CC) -o bin/tests build/edit_distance_library.o build/edit_distance_library_test.o build/unity.o

clean:
	rm -f build/* bin/*
