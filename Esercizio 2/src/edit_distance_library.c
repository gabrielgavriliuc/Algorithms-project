#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "edit_distance_library.h"

int edit_distance(char *s1, char *s2, int l1, int l2)
{
    //Parameters validation
    if (s1 == NULL || s2 == NULL)
    {
        fprintf(stderr, "edit_distance_library: parameters cannot be NULL");
        exit(EXIT_FAILURE);
    }

    //Parameters are valid

    //We have come to the end of both strings - last 2 characters were the same
    if (strlen(s1) == l1 && strlen(s2) == l2)
    {
        return 0;
    }

    //We have come to the end of the first string
    if (strlen(s1) == l1 && strlen(s2) > l2)
    {
        return strlen(s2) - l2;
    }

    //We have come to the end of the second string
    if (strlen(s1) > l1 && strlen(s2) == l2)
    {
        return strlen(s1) - l1;
    }

    //We still have characters to compare
    if (s1[l1] == s2[l2])
    {
        return min3(no_op(s1, s2, l1, l2), canc(s1, s2, l1, l2), ins(s1, s2, l1, l2));
    }
    else
    {
        return min2(canc(s1, s2, l1, l2), ins(s1, s2, l1, l2));
    }
}

int no_op(char *s1, char *s2, int l1, int l2)
{
    return edit_distance(s1, s2, l1 + 1, l2 + 1);
}

int canc(char *s1, char *s2, int l1, int l2)
{
    return 1 + edit_distance(s1, s2, l1, l2 + 1);
}

int ins(char *s1, char *s2, int l1, int l2)
{
    return 1 + edit_distance(s1, s2, l1 + 1, l2);
}

int edit_distance_wrap(char *s1, char *s2)
{
    return edit_distance(s1, s2, 0, 0);
}

int min3(int a, int b, int c)
{
    if (a <= b && a <= c)
    {
        return a;
    }
    else if (b <= a && b <= c)
    {
        return b;
    }
    else
    {
        return c;
    }
}

int min2(int a, int b)
{
    if (a <= b)
    {
        return a;
    }
    else
    {
        return b;
    }
}

//           ----------------------Dynamic programming starts here-----------------------

int **memo;

int no_op_dyn(char *s1, char *s2, int l1, int l2)
{
    return edit_distance_dyn(s1, s2, l1 + 1, l2 + 1);
}

int canc_dyn(char *s1, char *s2, int l1, int l2)
{
    return 1 + edit_distance_dyn(s1, s2, l1, l2 + 1);
}

int ins_dyn(char *s1, char *s2, int l1, int l2)
{
    return 1 + edit_distance_dyn(s1, s2, l1 + 1, l2);
}

int edit_distance_dyn(char *s1, char *s2, int l1, int l2)
{
    //Parameters validation
    if (s1 == NULL || s2 == NULL)
    {
        fprintf(stderr, "edit_distance_library: parameters cannot be NULL");
        exit(EXIT_FAILURE);
    }

    //Parameters are valid

    //We have come to the end of both strings - last 2 characters were the same
    if (strlen(s1) == l1 && strlen(s2) == l2)
    {
        return 0;
    }

    //We have come to the end of the first string
    if (strlen(s1) == l1 && strlen(s2) > l2)
    {
        return strlen(s2) - l2;
    }

    //We have come to the end of the second string
    if (strlen(s1) > l1 && strlen(s2) == l2)
    {
        return strlen(s1) - l1;
    }

    //We still have characters to compare
    if (memo[l1][l2] == -1)
    {
        //We don't have the result stored in memo so we need to calculate it
        if (s1[l1] == s2[l2])
        {
            //We store the result first and then we return it
            int canc = canc_dyn(s1, s2, l1, l2);
            int ins = ins_dyn(s1, s2, l1, l2);
            int no_op = no_op_dyn(s1, s2, l1, l2);
            memo[l1][l2] = min3(canc, ins, no_op);
            return memo[l1][l2];
        }
        else
        {
            int canc = canc_dyn(s1, s2, l1, l2);
            int ins = ins_dyn(s1, s2, l1, l2);
            memo[l1][l2] = min2(canc, ins);
            return memo[l1][l2];
        }
    }
    else
    {
        return memo[l1][l2];
    }
}

//It is used for the creation of the matrix
//It initialize all the positions to -1
void matrix_alloc(int nr, int nc)
{
    memo = (int **)malloc(nr * sizeof(int *));
    if (memo == NULL)
    {
        fprintf(stderr, "edit_distance_library: unable to allocate memory for memo rows");
        exit(EXIT_FAILURE);
    }

    for (int i = 0; i < nr; i++)
    {
        memo[i] = malloc(nc * sizeof(int *));
        if (memo[i] == NULL)
        {
            fprintf(stderr, "edit_distance_library: unable to allocate memory for memo columns");
            exit(EXIT_FAILURE);
        }
    }

    for (int i = 0; i < nr; i++)
    {
        for (int j = 0; j < nc; j++)
        {
            memo[i][j] = -1;
        }
    }
}

//It frees the memory allocated for the matrix
void matrix_free(int nr)
{
    for (int i = 0; i < nr; i++)
    {
        free(memo[i]);
    }
    free(memo);
}

int edit_distance_dyn_wrap(char *s1, char *s2)
{
    //Creation of the matrix
    int nr = strlen(s1); //if 0 we won't have the matrix
    int nc = strlen(s2); //if 0 we will have the matrix without any column
    matrix_alloc(nr, nc);
    int result = edit_distance_dyn(s1, s2, 0, 0);
    matrix_free(nr);
    return result;
}