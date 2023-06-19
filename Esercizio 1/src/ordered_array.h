#ifndef ORDERED_ARRAY_H_MERGE_BINARY_INSERTION_SORT
#define ORDERED_ARRAY_H_MERGE_BINARY_INSERTION_SORT

//It accepts a pointer to an array of pointers, the portion of the array that we want to order (from first to last) and a pointer to the function as the precedence relation between the elements.
//The function precedes must return 2 if the two elements have the same value, 1 if the first one precedes the other one, 0 otherwise.
//It orders the array passed as parameter.
//Parameters cannot be NULL, first and last must be >= 0 and first has to be < last. 
void merge_binary_insertion_sort(void **array, int first, int last, int (*precedes)(void*, void*));

#endif