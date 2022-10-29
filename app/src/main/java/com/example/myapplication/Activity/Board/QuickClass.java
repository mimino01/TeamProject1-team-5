package com.example.myapplication.Activity.Board;

import androidx.appcompat.app.AppCompatActivity;

public class QuickClass extends AppCompatActivity {

    public static void long_sort(long[] a) {
        long_l_pivot_sort(a, 0, a.length - 1);
    }

    private static void long_l_pivot_sort(long[] a, int lo, int hi) {
        if(lo >= hi) {
            return;
        }
        int pivot = partition(a, lo, hi);

        long_l_pivot_sort(a, lo, pivot - 1);
        long_l_pivot_sort(a, pivot + 1, hi);
    }

    private static int partition(long[] a, int left, int right) {

        int lo = left;
        int hi = right;
        long pivot = a[left];

        while(lo < hi) {

            while(a[hi] > pivot && lo < hi) {
                hi--;
            }
            while(a[lo] <= pivot && lo < hi) {
                lo++;
            }
            long_swap(a, lo, hi);
        }

        long_swap(a, left, lo);
        return lo;
    }

    private static void long_swap(long[] a, int i, int j) {
        long temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static void double_sort(double[] a) {
        double_l_pivot_sort(a, 0, a.length - 1);
    }

    private static void double_l_pivot_sort(double[] a, int lo, int hi) {
        if(lo >= hi) {
            return;
        }
        int pivot = partition(a, lo, hi);

        double_l_pivot_sort(a, lo, pivot - 1);
        double_l_pivot_sort(a, pivot + 1, hi);
    }

    private static int partition(double[] a, int left, int right) {

        int lo = left;
        int hi = right;
        double pivot = a[left];

        while(lo < hi) {

            while(a[hi] > pivot && lo < hi) {
                hi--;
            }
            while(a[lo] <= pivot && lo < hi) {
                lo++;
            }
            double_swap(a, lo, hi);
        }

        double_swap(a, left, lo);
        return lo;
    }

    private static void double_swap(double[] a, int i, int j) {
        double temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
