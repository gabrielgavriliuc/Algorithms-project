#include <stdio.h>
#include <stdlib.h>
#include "unity.h"
#include "ordered_array.h"

static int precedes_int(void *i1_p, void *i2_p)
{
    int *int1_p = (int *)i1_p;
    int *int2_p = (int *)i2_p;
    if ((*int1_p) < (*int2_p))
        return (1);
    else if ((*int1_p) == (*int2_p))
        return (2);
    else return (0);
}

static int i0, i1, i2, i3, i4, i5, i6, i7;

void setUp(void)
{
    i0 = 7;
    i1 = 4;
    i2 = 1;
    i3 = 8;
    i4 = 2;
    i5 = 3;
    i6 = 6;
    i7 = 5;
}

void tearDown(void)
{
}

static void test_ordered_array_merge_binary_sort_one_element(void)
{
    void *actual_array[] = {&i0};
    void *expected_array[] = {&i0};
    merge_binary_insertion_sort(actual_array, 0, 0, precedes_int);
    TEST_ASSERT_EQUAL_PTR_ARRAY(expected_array, actual_array, 1);
}

static void test_ordered_array_merge_binary_sort_eight_elements(void)
{
    void *actual_array[] = {&i0, &i1, &i2, &i3, &i4, &i5, &i6, &i7};
    void *expected_array[] = {&i2, &i4, &i5, &i1, &i7, &i6, &i0, &i3};
    merge_binary_insertion_sort(actual_array, 0, 7, precedes_int);
    TEST_ASSERT_EQUAL_PTR_ARRAY(expected_array, actual_array, 8);
}

int main(void)
{
    //test session
    UNITY_BEGIN();

    RUN_TEST(test_ordered_array_merge_binary_sort_one_element);
    RUN_TEST(test_ordered_array_merge_binary_sort_eight_elements);

    return UNITY_END();
}