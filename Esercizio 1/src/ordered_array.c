#include <stdlib.h>
#include <stdio.h>
#include "ordered_array.h"
#define K 43

int binary_search(void **array, int first, int last, void *element, int (*precedes)(void *, void *))
{
    if (first == last) //We have just 1 element in our ordered part of the array
    {
        if (precedes(element, array[first]))
        {
            return first;
        }
        else
        {
            return first + 1;
        }
    }

    int mid = (first + last) / 2;

    if (precedes(element, array[mid]) == 2) //The elements have the same value
    {
        return mid + 1;
    }
    else if (precedes(element, array[mid]))
    {
        return binary_search(array, first, mid, element, precedes);
    }
    else
    {
        return binary_search(array, mid + 1, last, element, precedes);
    }
}

void binary_insertion_sort(void **array, int first, int last, int (*precedes)(void *, void *))
{
    int i = 0, j = 0, position = 0;
    void *element = NULL;

    for (i = first + 1; i <= last; i++)
    {
        element = array[i];
        j = i - 1;
        position = binary_search(array, first, j, element, precedes);

        while (j >= position)
        {
            array[j + 1] = array[j];
            j--;
        }
        array[position] = element;
    }
}

void merge(void **array, int first, int mid, int last, int (*precedes)(void *, void *))
{
    int n1 = mid - first + 1;
    int n2 = last - mid;

    void **left = (void **)malloc(n1 * sizeof(void *));
    void **right = (void **)malloc(n2 * sizeof(void *));

    for (int i = 0; i < n1; i++)
    {
        left[i] = array[first + i];
    }
    for (int j = 0; j < n2; j++)
    {
        right[j] = array[mid + 1 + j];
    }

    int i = 0, j = 0, k = first;

    while (i < n1 && j < n2)
    {
        if (precedes(left[i], right[j]))
        {
            array[k] = left[i];
            i++;
        }
        else
        {
            array[k] = right[j];
            j++;
        }
        k++;
    }

    while (i < n1)
    {
        array[k] = left[i];
        i++;
        k++;
    }

    while (j < n2)
    {
        array[k] = right[j];
        j++;
        k++;
    }

    free(left);
    free(right);
}

void merge_binary_insertion_sort(void **array, int first, int last, int (*precedes)(void *, void *))
{
    if (array == NULL)
    {
        fprintf(stderr, "merge_binary_insertion_sort: array parameter cannot be NULL");
        exit(EXIT_FAILURE);
    }
    else if (precedes == NULL)
    {
        fprintf(stderr, "merge_binary_insertion_sort: precedes parameter cannot be NULL");
        exit(EXIT_FAILURE);
    }
    else if (first < 0 || last < 0)
    {
        fprintf(stderr, "merge_binary_insertion_sort: first and last MUST be positive");
        exit(EXIT_FAILURE);
    }
    else if (last < first)
    {
        fprintf(stderr, "merge_binary_insertion_sort: last MUST be > first");
        exit(EXIT_FAILURE);
    }
    else if (first < last)
    {
        int mid = (last + first) / 2;

        //First sub-array
        if ((mid - first + 1) <= K)
        {
            binary_insertion_sort(array, first, mid, precedes);
        }
        else
        {
            merge_binary_insertion_sort(array, first, mid, precedes);
        }

        //Second sub-array
        if ((last - mid + 1) <= K)
        {
            binary_insertion_sort(array, mid + 1, last, precedes);
        }
        else
        {
            merge_binary_insertion_sort(array, mid + 1, last, precedes);
        }
        
        merge(array, first, mid, last, precedes);
    }
}