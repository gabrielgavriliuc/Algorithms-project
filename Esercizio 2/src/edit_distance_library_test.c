#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "unity.h"
#include "edit_distance_library.h"

static void same_strings(void)
{
    char s1[] = "ciao";
    char s2[] = "ciao";
    int result = edit_distance_wrap(s1, s2);
    UNITY_TEST_ASSERT_EQUAL_INT(0, result, NULL, NULL);
}

static void different_strings(void)
{
    char s1[] = "cia";
    char s2[] = "bao";
    int result = edit_distance_wrap(s1, s2);
    UNITY_TEST_ASSERT_EQUAL_INT(4, result, NULL, NULL);
}

static void first_string_empty(void)
{
    char s1[] = "ciao";
    char s2[] = "";
    int result = edit_distance_wrap(s1, s2);
    UNITY_TEST_ASSERT_EQUAL_INT(4, result, NULL, NULL);
}

static void second_string_empty(void)
{
    char s1[] = "";
    char s2[] = "bao";
    int result = edit_distance_wrap(s1, s2);
    UNITY_TEST_ASSERT_EQUAL_INT(3, result, NULL, NULL);
}

//Dynamic programming tests starts here

static void same_strings_dyn(void)
{
    char s1[] = "ciao";
    char s2[] = "ciao";
    int result = edit_distance_dyn_wrap(s1, s2);
    UNITY_TEST_ASSERT_EQUAL_INT(0, result, NULL, NULL);
}

static void different_strings_dyn(void)
{
    char s1[] = "ciao";
    char s2[] = "bao";
    int result = edit_distance_dyn_wrap(s1, s2);
    UNITY_TEST_ASSERT_EQUAL_INT(3, result, NULL, NULL);
}

static void first_string_empty_dyn(void)
{
    char s1[] = "";
    char s2[] = "bao";
    int result = edit_distance_dyn_wrap(s1, s2);
    UNITY_TEST_ASSERT_EQUAL_INT(3, result, NULL, NULL);
}

static void second_string_empty_dyn(void)
{
    char s1[] = "ciao";
    char s2[] = "";
    int result = edit_distance_dyn_wrap(s1, s2);
    UNITY_TEST_ASSERT_EQUAL_INT(4, result, NULL, NULL);
}

int main(void)
{
    //test session
    UNITY_BEGIN();
    RUN_TEST(same_strings);
    RUN_TEST(different_strings);
    RUN_TEST(first_string_empty);
    RUN_TEST(second_string_empty);
    RUN_TEST(same_strings_dyn);
    RUN_TEST(different_strings_dyn);
    RUN_TEST(first_string_empty_dyn);
    RUN_TEST(second_string_empty_dyn);

    return UNITY_END();
}