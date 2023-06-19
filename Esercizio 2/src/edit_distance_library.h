#ifndef EDIT_DISTANCE_LIBRARY_H
#define EDIT_DISTANCE_LIBRARY_H

//It returns the number of changes needed to transform s2 in s1.
//It accepts two arrays of characters.
//s1, s2 cannot be NULL
int edit_distance_wrap(char *s1, char *s2);

//It returns the number of changes needed to transform s2 in s1.
//It accepts two arrays of characters.
//s1, s2 cannot be NULL
int edit_distance_dyn_wrap(char *s1, char *s2);

#endif