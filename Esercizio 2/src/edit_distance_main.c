#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <time.h>
#include "edit_distance_library.h"

//word: word from correctme.txt
//dictionary_list: array with the say of dictionary.txt with edit_distance min
//min_ed: minimum edit_distance
struct corrections
{
    char *word;
    char **dictionary_corrections;
    int min_ed;
    int n_corrections;
};

typedef struct corrections Corrections;

void **array;
int n_elements = 0, capacity;

//This method deletes all the punctuation from an array of chars
static void erase_punctuation(char *input)
{
    int i = 0;
    while (i < strlen(input))
    {
        if (ispunct(input[i]))
        {
            //memmove covers the position i of input (that is a punctuation) with the remaining of
            //input characters starting from i + 1.
            //The length of the array changes too, every character removed or 'covered' decrements
            //length by 1
            memmove(&input[i], &input[i + 1], strlen(input) - i);
            //we don't increment i because we need to process the new character in i position
        }
        else
        {
            //input[i] is not a punctuation so we can move on to the next position
            i++;
        }
    }
}

//This method transform all the uppercase characters, of an array of chars, into lower case
static void to_down(char *input)
{
    int i = 0;
    while (i < strlen(input))
    {
        if (isupper(input[i]))
        {
            input[i] = tolower(input[i]);
        }
        i++;
    }
}

//Loads the array of struct list with elements that have only the variable word initialized
static void load_from_correctme(char *file_name)
{
    char *read_line;
    char buffer[1024];
    int buf_size = 1024;
    FILE *fp;

    printf("\nLoading from correctme.txt\n");

    fp = fopen(file_name, "r");
    if (fp == NULL)
    {
        fprintf(stderr, "main.load_from_correctme: unable to open the file");
        exit(EXIT_FAILURE);
    }

    while (fgets(buffer, buf_size, fp) != NULL)
    {
        read_line = malloc((strlen(buffer) + 1) * sizeof(char));
        if (read_line == NULL)
        {
            fprintf(stderr, "main.load_from_correctme: unable to allocate memory for the read line");
            exit(EXIT_FAILURE);
        }
        strcpy(read_line, buffer);
        erase_punctuation(read_line);
        to_down(read_line);
        //Now in read_line we have a string without punctuation and with all characters in lowercase

        //Tokenizing the first element of the line
        char *token = strtok(read_line, " ");

        //We setup the array for the first malloc
        //We do this just for inserting the very first element in the array
        if (n_elements == 0)
        {
            array = malloc(sizeof(void *));
            if (array == NULL)
            {
                fprintf(stderr, "main.load_from_correctme: unable to allocate memory for the array");
                exit(EXIT_FAILURE);
            }
            capacity = 1;
        }

        //Inserting the elements
        while (token)
        {
            Corrections *element = malloc(sizeof(Corrections));
            if (element == NULL)
            {
                fprintf(stderr, "main.load_from_correctme: unable to allocate memory for the element");
                exit(EXIT_FAILURE);
            }
            element->word = malloc((strlen(token) + 1) * sizeof(char));
            strcpy(element->word, token);

            //We set the minimum edit_distance and the number of corrections on default values (length of the word and 0)
            element->min_ed = strlen(element->word);
            element->n_corrections = 0;

            //If we reach the capacity of the array we need to double it
            if (n_elements == capacity)
            {
                array = realloc(array, (2 * capacity) * sizeof(void *));
                capacity = capacity * 2;
            }
            array[n_elements] = element;
            n_elements++;

            //Taking next word
            token = strtok(NULL, " ");
        }
        free(read_line);
    }
    fclose(fp);
    printf("\n DATA LOADED\n");
}

static void print_array()
{
    Corrections *temp;

    printf("\nPRINTING THE WORDS FROM CORRECTME AND THEIR POSSIBLE CORRECTIONS\n");

    FILE *fp;
    if ((fp = fopen("possible_corrections.txt", "wt")) == NULL)
    {
        fprintf(stderr, "main.print_array: unable to open possible_corrections.txt");
        exit(EXIT_FAILURE);
    }
    for (int i = 0; i < n_elements; i++)
    {
        temp = array[i];
        fprintf(fp, "Word: %s\nMin ed: %d\n", temp->word, temp->min_ed);

        if (temp->n_corrections > 0)
        {
            for (int j = 0; j < temp->n_corrections; j++)
            {
                fprintf(fp, "Possible correction: %s\n", temp->dictionary_corrections[j]);
            }
        }
        fprintf(fp, "\n");
    }
    fclose(fp);
}

//It is used to free the dictionary_list from an element
static void free_dictionary_corrections(char **a, int rows)
{
    for (int i = 0; i < rows; i++)
    {
        free(a[i]);
    }
    free(a);
}

//It finds a list of the possible corrections (words from dictionary with minimum edit_distance) and adds it to the element Correction of the array.
//If there is a word from dictionary where edit_distance is 0 -> nothing is added
static void load_possible_corrections(char *file_name, Corrections *c)
{
    char *read_line;
    char buffer[1024];
    int buf_size = 1024;

    FILE *fp;
    fp = fopen(file_name, "r");
    if (fp == NULL)
    {
        fprintf(stderr, "main.load_possible_corrections: unable to open %s", file_name);
        exit(EXIT_FAILURE);
    }

    while (fgets(buffer, buf_size, fp) != NULL)
    {
        read_line = malloc((strlen(buffer) + 1) * sizeof(char));
        if (read_line == NULL)
        {
            fprintf(stderr, "main.load_possible_corrections: unable to allocate memory for read_line");
            exit(EXIT_FAILURE);
        }

        strcpy(read_line, buffer);
        char *token = strtok(read_line, "\n");

        //Creation of the list of possible corrections
        int edit_distance = edit_distance_dyn_wrap(c->word, token);

        //If edit_distance == 0 there aren't any issues with the word so we don't need to search for corrections
        //Also we need to free the array with the possible corrections
        if (edit_distance == 0)
        {
            if (c->n_corrections > 0)
            {
                free_dictionary_corrections(c->dictionary_corrections, c->n_corrections);
            }
            c->min_ed = 0;
            c->n_corrections = 0;
            break;
        }
        else
        {
            //If we find a word from dictionary that has lower edit_distance we have to:
            //- free the list of corrections of the element (if corrections were stored)
            //- malloc it
            //- refresh the elements' min_ed and n_corrections
            if (edit_distance < c->min_ed)
            {
                if (c->n_corrections > 0)
                {
                    free_dictionary_corrections(c->dictionary_corrections, c->n_corrections);
                }

                //Alloc the array of corrections
                c->dictionary_corrections = malloc(sizeof(char *));
                if (c->dictionary_corrections == NULL)
                {
                    fprintf(stderr, "main.load_possible_corrections: unable to allocate memory for dictionary_corrections of an element");
                    exit(EXIT_FAILURE);
                }

                //Alloc the correction itself
                char *correction = malloc((strlen(token) + 1) * sizeof(char));
                if (correction == NULL)
                {
                    fprintf(stderr, "main.load_possible_corrections: unable to allocate memory for a correction");
                    exit(EXIT_FAILURE);
                }

                //Adding
                strcpy(correction, token);
                c->dictionary_corrections[0] = correction;
                c->min_ed = edit_distance;
                c->n_corrections = 1;
            }
            else
            {

                //If edit_distance == min_ed there are 2 cases:
                //n_corrections = 0 -> malloc & insert
                //n_corrections > 0 -> realloc & insert
                if (edit_distance == c->min_ed)
                {
                    //We alloc the memory for the correction
                    char *correction = malloc((strlen(token) + 1) * sizeof(char));
                    if (correction == NULL)
                    {
                        fprintf(stderr, "main.load_possible_corrections: unable to allocate memory for a correction");
                        exit(EXIT_FAILURE);
                    }
                    strcpy(correction, token);

                    //Checking if the array has been allocated or not

                    //Not allocated
                    if (c->n_corrections == 0)
                    {
                        c->dictionary_corrections = malloc(sizeof(char *));
                        if (c->dictionary_corrections == NULL)
                        {
                            fprintf(stderr, "main.load_possible_corrections: unable to allocate memory for dictionary_corrections of an element");
                            exit(EXIT_FAILURE);
                        }
                    }
                    //Already allocated
                    else
                    {
                        c->dictionary_corrections = realloc(c->dictionary_corrections, (c->n_corrections + 1) * sizeof(char *));
                        if (c->dictionary_corrections == NULL)
                        {
                            fprintf(stderr, "main.load_possible_corrections: unable to rallocate memory for dictionary_corrections of an element");
                            exit(EXIT_FAILURE);
                        }
                    }

                    //Adding
                    c->dictionary_corrections[c->n_corrections] = correction;
                    c->n_corrections++;
                }
            }
        }
        free(read_line);
    }
}

//Adds the possible dictionary correction to each element of our array
static void get_corrections(char *file_name)
{
    printf("\nLoading the possible corrections for the words...\n");
    Corrections *temp;
    for (int i = 0; i < n_elements; i++)
    {
        printf("\nProcessing the %d element\n", i);
        temp = array[i];
        load_possible_corrections(file_name, temp);
    }
}

int main(int argc, char const *argv[])
{
    load_from_correctme(argv[1]);

    printf("-------Calcolo del tempo di esecuzione-------");
    float startTime = (float)clock() / CLOCKS_PER_SEC; //Inizio del timer
    get_corrections(argv[2]);
    float endTime = (float)clock() / CLOCKS_PER_SEC; //Fine del timer
    float timeElapsed = endTime - startTime;         //Calcolo del tempo trascorso
    printf("%.3f", timeElapsed);

    print_array();
    return 0;
}
