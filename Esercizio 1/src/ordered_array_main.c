#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include "ordered_array.h"

int n_elem = 0, capacity;

struct record
{
    char *string_field;
    int integer_field;
    float float_field;
};

//It takes as input two pointers to struct record.
//It returns 2 if the integer fields of the two records have the same value,
//1 if the first one is less than the second one, 0 otherwise.
static int precedes_record_int_field(void *r1_p, void *r2_p)
{
    if (r1_p == NULL)
    {
        fprintf(stderr, "precedes_record_int_field: the first parameter is a null pointer");
        exit(EXIT_FAILURE);
    }
    if (r2_p == NULL)
    {
        fprintf(stderr, "precedes_record_int_field: the second parameter is a null pointer");
        exit(EXIT_FAILURE);
    }
    struct record *rec1_p = (struct record *)r1_p;
    struct record *rec2_p = (struct record *)r2_p;

    if (rec1_p->integer_field == rec2_p->integer_field)
    {
        return (2);
    }
    if (rec1_p->integer_field < rec2_p->integer_field)
    {
        return (1);
    }
    return (0);
}

//It takes as input two pointers to struct record.
//It returns 2 if the string fields of the two recors has the same value,
//1 if the first one is less than the second one, 0 otherwise.
static int precedes_record_string_field(void *r1_p, void *r2_p)
{
    if (r1_p == NULL)
    {
        fprintf(stderr, "precedes_string: the first parameter is a null pointer");
        exit(EXIT_FAILURE);
    }
    if (r2_p == NULL)
    {
        fprintf(stderr, "precedes_string: the second parameter is a null pointer");
        exit(EXIT_FAILURE);
    }
    struct record *rec1_p = (struct record *)r1_p;
    struct record *rec2_p = (struct record *)r2_p;

    if (strcmp(rec1_p->string_field, rec2_p->string_field) == 0)
    {
        return (2);
    }
    if (strcmp(rec1_p->string_field, rec2_p->string_field) < 0)
    {
        return (1);
    }
    return (0);
}

//It takes as input two pointers to struct record.
//It returns 2 if the float fields of the two records have the same value,
//1 if the first one is less than the second one, 0 otherwise.
static int precedes_record_float_field(void *r1_p, void *r2_p)
{
    if (r1_p == NULL)
    {
        fprintf(stderr, "precedes_record_float_field: the first parameter is a null pointer");
        exit(EXIT_FAILURE);
    }
    if (r2_p == NULL)
    {
        fprintf(stderr, "precedes_record_float_field: the second parameter is a null pointer");
        exit(EXIT_FAILURE);
    }
    struct record *rec1_p = (struct record *)r1_p;
    struct record *rec2_p = (struct record *)r2_p;

    if (rec1_p->float_field == rec2_p->float_field)
    {
        return (2);
    }
    if (rec1_p->float_field < rec2_p->float_field)
    {
        return (1);
    }
    return (0);
}

static void print_array(void **array)
{
    struct record *array_element;

    printf("\nORDERED ARRAY OF RECORDS\n");

    FILE *fp;

    if ((fp = fopen("record_ordinati.txt", "wt")) == NULL)
    {
        fprintf(stderr, "print_array: unable to open record_ordinati.txt");
        exit(EXIT_FAILURE);
    }

    for (int i = 0; i < n_elem; i++)
    {
        array_element = (struct record *)array[i];
        fprintf(fp, "<%s,                   %d,                 %.6f>\n", array_element->string_field, array_element->integer_field, array_element->float_field);
    }

    fclose(fp);
}

//It takes the file path of the file
//It inserts the elements of the file in an array of records' structure
//It returns an array of records type
static void **load_array(const char *file_name)
{
    char *read_line_p;
    char buffer[1024];
    int buf_size = 1024;
    void **arr;

    FILE *fp;
    printf("\nLoading data from file...\n");
    fp = fopen(file_name, "r");
    if (fp == NULL)
    {
        fprintf(stderr, "main: unable to open the file");
        exit(EXIT_FAILURE);
    }
    while (fgets(buffer, buf_size, fp) != NULL)
    {
        read_line_p = malloc((strlen(buffer) + 1) * sizeof(char));
        if (read_line_p == NULL)
        {
            fprintf(stderr, "main: unable to allocate memory for the read line");
            exit(EXIT_FAILURE);
        }
        strcpy(read_line_p, buffer);

        char *id_field_in_read_line_p = strtok(read_line_p, ","); //id
        char *string_field_in_read_line_p = strtok(NULL, ",");    //string
        char *integer_field_in_read_line_p = strtok(NULL, ",");   //int
        char *float_field_in_read_line_p = strtok(NULL, ",");     //float

        char *string_field = malloc((strlen(string_field_in_read_line_p) + 1) * sizeof(char));
        if (string_field == NULL)
        {
            fprintf(stderr, "main: unable to allocate memory for the string field of the read record");
            exit(EXIT_FAILURE);
        }
        strcpy(string_field, string_field_in_read_line_p);

        int integer_field = atoi(integer_field_in_read_line_p);
        float float_field = (float)atof(float_field_in_read_line_p);

        struct record *record_p = malloc(sizeof(struct record));
        if (record_p == NULL)
        {
            fprintf(stderr, "main: unable to allocate memory for the read record");
            exit(EXIT_FAILURE);
        }

        record_p->string_field = string_field;
        record_p->integer_field = integer_field;
        record_p->float_field = float_field;

        if (n_elem == 0)
        {
            arr = (void **)malloc(sizeof(void *));
            capacity = 1;
            arr[0] = record_p;
            n_elem++;
        }
        else if (n_elem == capacity)
        {
            arr = (void **)realloc(arr, (capacity * 2) * sizeof(void *));
            capacity = capacity * 2;
            arr[n_elem] = record_p;
            n_elem++;
        }
        else
        {
            arr[n_elem] = record_p;
            n_elem++;
        }

        free(read_line_p);
    }
    fclose(fp);
    printf("\nData loaded\n");
    return arr;
}

//It takes the file path of the file and a pointer to the function that we use to sort the elements
//It measures the amount of time that the algorithm takes to order the array
//It frees also the memory occupied by the records
static void test_with_comparison_function(const char *file_name, int (*compare)(void *, void *))
{
    void **array;

    array = load_array(file_name);

    printf("\nSto per fare il merge\n");
    float startTime = (float)clock() / CLOCKS_PER_SEC;

    merge_binary_insertion_sort(array, 0, n_elem - 1, compare);

    float endTime = (float)clock() / CLOCKS_PER_SEC;
    printf("\nFatto\n");
    float timeElapsed = endTime - startTime;
    printf("%.3f", timeElapsed);

    n_elem = 0;
    free(array);
}

//It should be invoked with one parameter specifying the filepath of the data file
int main(int argc, char const *argv[])
{
    if (argc < 2)
    {
        printf("Usage: ordered_array_main <file_name>\n");
        exit(EXIT_FAILURE);
    }
    test_with_comparison_function(argv[1], precedes_record_string_field);
    test_with_comparison_function(argv[1], precedes_record_int_field);
    test_with_comparison_function(argv[1], precedes_record_float_field);

    return (EXIT_SUCCESS);
}
