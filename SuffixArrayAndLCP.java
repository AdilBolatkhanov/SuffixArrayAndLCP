package com.company;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
class SuffixArrayAndLCP
{
    static class Suffix implements Comparable<com.company.Suffix>{
        public int index;
        public String suffix;

        public Suffix(int i, String s){
            index = i;
            suffix = s;
        }

        @Override
        public int compareTo(com.company.Suffix suf){
            return this.suffix.compareTo(suf.suffix);
        }
    }
    public static int[] suffixArray(String str){
        int n = str.length();
        com.company.Suffix[] suffixes = new com.company.Suffix[n];
        for (int i = 0 ; i < n; i++){
            suffixes[i] = new com.company.Suffix(i, str.substring(i));
        }
        Arrays.sort(suffixes);
        int[] suffixAr = new int[n];
        for (int i = 0 ; i < n ; i++){
            suffixAr[i] = suffixes[i].index;
        }
        return suffixAr;
    }

    public static int[] kasai(String text, int[] suffixAr){
        int n = suffixAr.length;
        int[] lcp = new int[n];
        // An auxiliary array to store inverse of suffix array
        // elements. For example if suffixArr[0] is 5, the
        // invSuff[5] would store 0.  This is used to get next
        // suffix string from suffix array.
        int[] invSuff = new int[n];

        for (int i = 0; i < n ; i++){
            invSuff[suffixAr[i]] = i;
        }
// Initialize length of previous LCP
        int k = 0;
        for (int i = 0; i <n ; i++){
            /* If the current suffix is at n-1, then we don’t
           have next substring to consider. So lcp is not
           defined for this substring, we put zero. */
            if (invSuff[i] == n - 1){
                k = 0;
                continue;
            }
            /* j contains index of the next substring to
           be considered  to compare with the present
           substring, i.e., next string in suffix array */
            int j = suffixAr[invSuff[i] + 1];

            // Directly start matching from k'th index as
            // at-least k-1 characters will match
            while (i + k < n && j + k < n && text.charAt(i + k) == text.charAt(j + k))
                k++;

            lcp[invSuff[i]] = k;

            // Deleting the starting character from the string.
            if (k > 0)
                k--;
        }
        return lcp;

    }

    public static void lcp(String str, int[] suffixAr, int[] lcp){
        int max = 0;
        int maxIndex = 0;
        for (int i = 0 ;i < lcp.length; i++){
            if (lcp[i] > max){
                max = lcp[i];
                maxIndex = i;
            }
        }
        System.out.println("Index: "+suffixAr[maxIndex]+" string: "+ str.substring(suffixAr[maxIndex], suffixAr[maxIndex]+max)+" ,length: "+ max);
    }

    static void printArr(int arr[], int n)
    {
        for (int i = 0; i < n; i++)
            System.out.print(arr[i] + " ");
        System.out.println();
    }

    // Driver Code
    public static void main(String[] args) throws IOException
    {
        DataInputStream input = new DataInputStream(new FileInputStream("corona-dna.txt"));
        //read string from text file including 33 a's
//        String txt = input.readLine()+"$";

        //String from text file without last 33 a's
        String txt = "attaaaggtttataccttcccaggtaacaaaccaaccaactttcgatctcttgtagatctgttctctaaacgaactttaaaatctgtgtggctgtcactcggctgcatgcttagtgcactcacgcagtataattaataactaattactgtcgttgacaggacacgagtaactcgtctatcttctgcaggctgcttacggtttcgtccgtgttgcagccgatcatcagcacatctaggtttcgtccgggtgtgaccgaaaggtaagatggagagccttgtccctggtttcaacgagaaaacacacgtccaactcagtttgcctgttttacaggttcgcgacgtgctcgtacgtggctttggagactccgtggaggaggtcttatcagaggcacgtcaacatcttaaagatggcacttgtggcttagtagaagttgaaaaaggcgttttgcctcaacttgaacagccctatgtgttcatcaaacgttcggatgctcgaactgcacctcatggtcatgttatggttgagctggtagcagaactcgaaggcattcagtacggtcgtagtggtgagacacttggtgtccttgtccctcatgtgggcgaaataccagtggcttaccgcaaggttcttcttcgtaagaacggtaataaaggagctggtggccatagttacggcgccgatctaaagtcatttgacttaggcgacgagcttggcactgatccttatgaagattttcaagaaaactggaacactaaacatagcagtggtgttacccgtgaactcatgcgtgagcttaacggaggggcatacactcgctatgtcgataacaacttctgtggccctgatggctaccctcttgagtgcattaaagaccttctagcacgtgctggtaaagcttcatgcactttgtccgaacaactggactttattgacactaagaggggtgtatactgctgccgtgaacatgagcatgaaattgcttggtacacggaacgttctgaaaagagctatgaattgcagacaccttttgaaattaaattggcaaagaaatttgacaccttcaatggggaatgtccaaattttgtatttcccttaaattccataatcaagactattcaaccaagggttgaaaagaaaaagcttgatggctttatgggtagaattcgatctgtctatccagttgcgtcaccaaatgaatgcaaccaaatgtgcctttcaactctcatgaagtgtgatcattgtggtgaaacttcatggcagacgggcgattttgttaaagccacttgcgaattttgtggcactgagaatttgactaaagaaggtgccactacttgtggttacttaccccaaaatgctgttgttaaaatttattgtccagcatgtcacaattcagaagtaggacctgagcatagtcttgccgaataccataatgaatctggcttgaaaaccattcttcgtaagggtggtcgcactattgcctttggaggctgtgtgttctcttatgttggttgccataacaagtgtgcctattgggttccacgtgctagcgctaacataggttgtaaccatacaggtgttgttggagaaggttccgaaggtcttaatgacaaccttcttgaaatactccaaaaagagaaagtcaacatcaatattgttggtgactttaaacttaatgaagagatcgccattattttggcatctttttctgcttccacaagtgcttttgtggaaactgtgaaaggtttggattataaagcattcaaacaaattgttgaatcctgtggtaattttaaagttacaaaaggaaaagctaaaaaaggtgcctggaatattggtgaacagaaatcaatactgagtcctctttatgcatttgcatcagaggctgctcgtgttgtacgatcaattttctcccgcactcttgaaactgctcaaaattctgtgcgtgttttacagaaggccgctataacaatactagatggaatttcacagtattcactgagactcattgatgctatgatgttcacatctgatttggctactaacaatctagttgtaatggcctacattacaggtggtgttgttcagttgacttcgcagtggctaactaacatctttggcactgtttatgaaaaactcaaacccgtccttgattggcttgaagagaagtttaaggaaggtgtagagtttcttagagacggttgggaaattgttaaatttatctcaacctgtgcttgtgaaattgtcggtggacaaattgtcacctgtgcaaaggaaattaaggagagtgttcagacattctttaagcttgtaaataaatttttggctttgtgtgctgactctatcattattggtggagctaaacttaaagccttgaatttaggtgaaacatttgtcacgcactcaaagggattgtacagaaagtgtgttaaatccagagaagaaactggcctactcatgcctctaaaagccccaaaagaaattatcttcttagagggagaaacacttcccacagaagtgttaacagaggaagttgtcttgaaaactggtgatttacaaccattagaacaacctactagtgaagctgttgaagctccattggttggtacaccagtttgtattaacgggcttatgttgctcgaaatcaaagacacagaaaagtactgtgcccttgcacctaatatgatggtaacaaacaataccttcacactcaaaggcggtgcaccaacaaaggttacttttggtgatgacactgtgatagaagtgcaaggttacaagagtgtgaatatcacttttgaacttgatgaaaggattgataaagtacttaatgagaagtgctctgcctatacagttgaactcggtacagaagtaaatgagttcgcctgtgttgtggcagatgctgtcataaaaactttgcaaccagtatctgaattacttacaccactgggcattgatttagatgagtggagtatggctacatactacttatttgatgagtctggtgagtttaaattggcttcacatatgtattgttctttctaccctccagatgaggatgaagaagaaggtgattgtgaagaagaagagtttgagccatcaactcaatatgagtatggtactgaagatgattaccaaggtaaacctttggaatttggtgccacttctgctgctcttcaacctgaagaagagcaagaagaagattggttagatgatgatagtcaacaaactgttggtcaacaagacggcagtgaggacaatcagacaactactattcaaacaattgttgaggttcaacctcaattagagatggaacttacaccagttgttcagactattgaagtgaatagttttagtggttatttaaaacttactgacaatgtatacattaaaaatgcagacattgtggaagaagctaaaaaggtaaaaccaacagtggttgttaatgcagccaatgtttaccttaaacatggaggaggtgttgcaggagccttaaataaggctactaacaatgccatgcaagttgaatctgatgattacatagctactaatggaccacttaaagtgggtggtagttgtgttttaagcggacacaatcttgctaaacactgtcttcatgttgtcggcccaaatgttaacaaaggtgaagacattcaacttcttaagagtgcttatgaaaattttaatcagcacgaagttctacttgcaccattattatcagctggtatttttggtgctgaccctatacattctttaagagtttgtgtagatactgttcgcacaaatgtctacttagctgtctttgataaaaatctctatgacaaacttgtttcaagctttttggaaatgaagagtgaaaagcaagttgaacaaaagatcgctgagattcctaaagaggaagttaagccatttataactgaaagtaaaccttcagttgaacagagaaaacaagatgataagaaaatcaaagcttgtgttgaagaagttacaacaactctggaagaaactaagttcctcacagaaaacttgttactttatattgacattaatggcaatcttcatccagattctgccactcttgttagtgacattgacatcactttcttaaagaaagatgctccatatatagtgggtgatgttgttcaagagggtgttttaactgctgtggttatacctactaaaaaggctggtggcactactgaaatgctagcgaaagctttgagaaaagtgccaacagacaattatataaccacttacccgggtcagggtttaaatggttacactgtagaggaggcaaagacagtgcttaaaaagtgtaaaagtgccttttacattctaccatctattatctctaatgagaagcaagaaattcttggaactgtttcttggaatttgcgagaaatgcttgcacatgcagaagaaacacgcaaattaatgcctgtctgtgtggaaactaaagccatagtttcaactatacagcgtaaatataagggtattaaaatacaagagggtgtggttgattatggtgctagattttacttttacaccagtaaaacaactgtagcgtcacttatcaacacacttaacgatctaaatgaaactcttgttacaatgccacttggctatgtaacacatggcttaaatttggaagaagctgctcggtatatgagatctctcaaagtgccagctacagtttctgtttcttcacctgatgctgttacagcgtataatggttatcttacttcttcttctaaaacacctgaagaacattttattgaaaccatctcacttgctggttcctataaagattggtcctattctggacaatctacacaactaggtatagaatttcttaagagaggtgataaaagtgtatattacactagtaatcctaccacattccacctagatggtgaagttatcacctttgacaatcttaagacacttctttctttgagagaagtgaggactattaaggtgtttacaacagtagacaacattaacctccacacgcaagttgtggacatgtcaatgacatatggacaacagtttggtccaacttatttggatggagctgatgttactaaaataaaacctcataattcacatgaaggtaaaacattttatgttttacctaatgatgacactctacgtgttgaggcttttgagtactaccacacaactgatcctagttttctgggtaggtacatgtcagcattaaatcacactaaaaagtggaaatacccacaagttaatggtttaacttctattaaatgggcagataacaactgttatcttgccactgcattgttaacactccaacaaatagagttgaagtttaatccacctgctctacaagatgcttattacagagcaagggctggtgaagctgctaacttttgtgcacttatcttagcctactgtaataagacagtaggtgagttaggtgatgttagagaaacaatgagttacttgtttcaacatgccaatttagattcttgcaaaagagtcttgaacgtggtgtgtaaaacttgtggacaacagcagacaacccttaagggtgtagaagctgttatgtacatgggcacactttcttatgaacaatttaagaaaggtgttcagataccttgtacgtgtggtaaacaagctacaaaatatctagtacaacaggagtcaccttttgttatgatgtcagcaccacctgctcagtatgaacttaagcatggtacatttacttgtgctagtgagtacactggtaattaccagtgtggtcactataaacatataacttctaaagaaactttgtattgcatagacggtgctttacttacaaagtcctcagaatacaaaggtcctattacggatgttttctacaaagaaaacagttacacaacaaccataaaaccagttacttataaattggatggtgttgtttgtacagaaattgaccctaagttggacaattattataagaaagacaattcttatttcacagagcaaccaattgatcttgtaccaaaccaaccatatccaaacgcaagcttcgataattttaagtttgtatgtgataatatcaaatttgctgatgatttaaaccagttaactggttataagaaacctgcttcaagagagcttaaagttacatttttccctgacttaaatggtgatgtggtggctattgattataaacactacacaccctcttttaagaaaggagctaaattgttacataaacctattgtttggcatgttaacaatgcaactaataaagccacgtataaaccaaatacctggtgtatacgttgtctttggagcacaaaaccagttgaaacatcaaattcgtttgatgtactgaagtcagaggacgcgcagggaatggataatcttgcctgcgaagatctaaaaccagtctctgaagaagtagtggaaaatcctaccatacagaaagacgttcttgagtgtaatgtgaaaactaccgaagttgtaggagacattatacttaaaccagcaaataatagtttaaaaattacagaagaggttggccacacagatctaatggctgcttatgtagacaattctagtcttactattaagaaacctaatgaattatctagagtattaggtttgaaaacccttgctactcatggtttagctgctgttaatagtgtcccttgggatactatagctaattatgctaagccttttcttaacaaagttgttagtacaactactaacatagttacacggtgtttaaaccgtgtttgtactaattatatgccttatttctttactttattgctacaattgtgtacttttactagaagtacaaattctagaattaaagcatctatgccgactactatagcaaagaatactgttaagagtgtcggtaaattttgtctagaggcttcatttaattatttgaagtcacctaatttttctaaactgataaatattataatttggtttttactattaagtgtttgcctaggttctttaatctactcaaccgctgctttaggtgttttaatgtctaatttaggcatgccttcttactgtactggttacagagaaggctatttgaactctactaatgtcactattgcaacctactgtactggttctataccttgtagtgtttgtcttagtggtttagattctttagacacctatccttctttagaaactatacaaattaccatttcatcttttaaatgggatttaactgcttttggcttagttgcagagtggtttttggcatatattcttttcactaggtttttctatgtacttggattggctgcaatcatgcaattgtttttcagctattttgcagtacattttattagtaattcttggcttatgtggttaataattaatcttgtacaaatggccccgatttcagctatggttagaatgtacatcttctttgcatcattttattatgtatggaaaagttatgtgcatgttgtagacggttgtaattcatcaacttgtatgatgtgttacaaacgtaatagagcaacaagagtcgaatgtacaactattgttaatggtgttagaaggtccttttatgtctatgctaatggaggtaaaggcttttgcaaactacacaattggaattgtgttaattgtgatacattctgtgctggtagtacatttattagtgatgaagttgcgagagacttgtcactacagtttaaaagaccaataaatcctactgaccagtcttcttacatcgttgatagtgttacagtgaagaatggttccatccatctttactttgataaagctggtcaaaagacttatgaaagacattctctctctcattttgttaacttagacaacctgagagctaataacactaaaggttcattgcctattaatgttatagtttttgatggtaaatcaaaatgtgaagaatcatctgcaaaatcagcgtctgtttactacagtcagcttatgtgtcaacctatactgttactagatcaggcattagtgtctgatgttggtgatagtgcggaagttgcagttaaaatgtttgatgcttacgttaatacgttttcatcaacttttaacgtaccaatggaaaaactcaaaacactagttgcaactgcagaagctgaacttgcaaagaatgtgtccttagacaatgtcttatctacttttatttcagcagctcggcaagggtttgttgattcagatgtagaaactaaagatgttgttgaatgtcttaaattgtcacatcaatctgacatagaagttactggcgatagttgtaataactatatgctcacctataacaaagttgaaaacatgacaccccgtgaccttggtgcttgtattgactgtagtgcgcgtcatattaatgcgcaggtagcaaaaagtcacaacattgctttgatatggaacgttaaagatttcatgtcattgtctgaacaactacgaaaacaaatacgtagtgctgctaaaaagaataacttaccttttaagttgacatgtgcaactactagacaagttgttaatgttgtaacaacaaagatagcacttaagggtggtaaaattgttaataattggttgaagcagttaattaaagttacacttgtgttcctttttgttgctgctattttctatttaataacacctgttcatgtcatgtctaaacatactgacttttcaagtgaaatcataggatacaaggctattgatggtggtgtcactcgtgacatagcatctacagatacttgttttgctaacaaacatgctgattttgacacatggtttagccagcgtggtggtagttatactaatgacaaagcttgcccattgattgctgcagtcataacaagagaagtgggttttgtcgtgcctggtttgcctggcacgatattacgcacaactaatggtgactttttgcatttcttacctagagtttttagtgcagttggtaacatctgttacacaccatcaaaacttatagagtacactgactttgcaacatcagcttgtgttttggctgctgaatgtacaatttttaaagatgcttctggtaagccagtaccatattgttatgataccaatgtactagaaggttctgttgcttatgaaagtttacgccctgacacacgttatgtgctcatggatggctctattattcaatttcctaacacctaccttgaaggttctgttagagtggtaacaacttttgattctgagtactgtaggcacggcacttgtgaaagatcagaagctggtgtttgtgtatctactagtggtagatgggtacttaacaatgattattacagatctttaccaggagttttctgtggtgtagatgctgtaaatttacttactaatatgtttacaccactaattcaacctattggtgctttggacatatcagcatctatagtagctggtggtattgtagctatcgtagtaacatgccttgcctactattttatgaggtttagaagagcttttggtgaatacagtcatgtagttgcctttaatactttactattccttatgtcattcactgtactctgtttaacaccagtttactcattcttacctggtgtttattctgttatttacttgtacttgacattttatcttactaatgatgtttcttttttagcacatattcagtggatggttatgttcacacctttagtacctttctggataacaattgcttatatcatttgtatttccacaaagcatttctattggttctttagtaattacctaaagagacgtgtagtctttaatggtgtttcctttagtacttttgaagaagctgcgctgtgcacctttttgttaaataaagaaatgtatctaaagttgcgtagtgatgtgctattacctcttacgcaatataatagatacttagctctttataataagtacaagtattttagtggagcaatggatacaactagctacagagaagctgcttgttgtcatctcgcaaaggctctcaatgacttcagtaactcaggttctgatgttctttaccaaccaccacaaacctctatcacctcagctgttttgcagagtggttttagaaaaatggcattcccatctggtaaagttgagggttgtatggtacaagtaacttgtggtacaactacacttaacggtctttggcttgatgacgtagtttactgtccaagacatgtgatctgcacctctgaagacatgcttaaccctaattatgaagatttactcattcgtaagtctaatcataatttcttggtacaggctggtaatgttcaactcagggttattggacattctatgcaaaattgtgtacttaagcttaaggttgatacagccaatcctaagacacctaagtataagtttgttcgcattcaaccaggacagactttttcagtgttagcttgttacaatggttcaccatctggtgtttaccaatgtgctatgaggcccaatttcactattaagggttcattccttaatggttcatgtggtagtgttggttttaacatagattatgactgtgtctctttttgttacatgcaccatatggaattaccaactggagttcatgctggcacagacttagaaggtaacttttatggaccttttgttgacaggcaaacagcacaagcagctggtacggacacaactattacagttaatgttttagcttggttgtacgctgctgttataaatggagacaggtggtttctcaatcgatttaccacaactcttaatgactttaaccttgtggctatgaagtacaattatgaacctctaacacaagaccatgttgacatactaggacctctttctgctcaaactggaattgccgttttagatatgtgtgcttcattaaaagaattactgcaaaatggtatgaatggacgtaccatattgggtagtgctttattagaagatgaatttacaccttttgatgttgttagacaatgctcaggtgttactttccaaagtgcagtgaaaagaacaatcaagggtacacaccactggttgttactcacaattttgacttcacttttagttttagtccagagtactcaatggtctttgttcttttttttgtatgaaaatgcctttttaccttttgctatgggtattattgctatgtctgcttttgcaatgatgtttgtcaaacataagcatgcatttctctgtttgtttttgttaccttctcttgccactgtagcttattttaatatggtctatatgcctgctagttgggtgatgcgtattatgacatggttggatatggttgatactagtttgtctggttttaagctaaaagactgtgttatgtatgcatcagctgtagtgttactaatccttatgacagcaagaactgtgtatgatgatggtgctaggagagtgtggacacttatgaatgtcttgacactcgtttataaagtttattatggtaatgctttagatcaagccatttccatgtgggctcttataatctctgttacttctaactactcaggtgtagttacaactgtcatgtttttggccagaggtattgtttttatgtgtgttgagtattgccctattttcttcataactggtaatacacttcagtgtataatgctagtttattgtttcttaggctatttttgtacttgttactttggcctcttttgtttactcaaccgctactttagactgactcttggtgtttatgattacttagtttctacacaggagtttagatatatgaattcacagggactactcccacccaagaatagcatagatgccttcaaactcaacattaaattgttgggtgttggtggcaaaccttgtatcaaagtagccactgtacagtctaaaatgtcagatgtaaagtgcacatcagtagtcttactctcagttttgcaacaactcagagtagaatcatcatctaaattgtgggctcaatgtgtccagttacacaatgacattctcttagctaaagatactactgaagcctttgaaaaaatggtttcactactttctgttttgctttccatgcagggtgctgtagacataaacaagctttgtgaagaaatgctggacaacagggcaaccttacaagctatagcctcagagtttagttcccttccatcatatgcagcttttgctactgctcaagaagcttatgagcaggctgttgctaatggtgattctgaagttgttcttaaaaagttgaagaagtctttgaatgtggctaaatctgaatttgaccgtgatgcagccatgcaacgtaagttggaaaagatggctgatcaagctatgacccaaatgtataaacaggctagatctgaggacaagagggcaaaagttactagtgctatgcagacaatgcttttcactatgcttagaaagttggataatgatgcactcaacaacattatcaacaatgcaagagatggttgtgttcccttgaacataatacctcttacaacagcagccaaactaatggttgtcataccagactataacacatataaaaatacgtgtgatggtacaacatttacttatgcatcagcattgtgggaaatccaacaggttgtagatgcagatagtaaaattgttcaacttagtgaaattagtatggacaattcacctaatttagcatggcctcttattgtaacagctttaagggccaattctgctgtcaaattacagaataatgagcttagtcctgttgcactacgacagatgtcttgtgctgccggtactacacaaactgcttgcactgatgacaatgcgttagcttactacaacacaacaaagggaggtaggtttgtacttgcactgttatccgatttacaggatttgaaatgggctagattccctaagagtgatggaactggtactatctatacagaactggaaccaccttgtaggtttgttacagacacacctaaaggtcctaaagtgaagtatttatactttattaaaggattaaacaacctaaatagaggtatggtacttggtagtttagctgccacagtacgtctacaagctggtaatgcaacagaagtgcctgccaattcaactgtattatctttctgtgcttttgctgtagatgctgctaaagcttacaaagattatctagctagtgggggacaaccaatcactaattgtgttaagatgttgtgtacacacactggtactggtcaggcaataacagttacaccggaagccaatatggatcaagaatcctttggtggtgcatcgtgttgtctgtactgccgttgccacatagatcatccaaatcctaaaggattttgtgacttaaaaggtaagtatgtacaaatacctacaacttgtgctaatgaccctgtgggttttacacttaaaaacacagtctgtaccgtctgcggtatgtggaaaggttatggctgtagttgtgatcaactccgcgaacccatgcttcagtcagctgatgcacaatcgtttttaaacgggtttgcggtgtaagtgcagcccgtcttacaccgtgcggcacaggcactagtactgatgtcgtatacagggcttttgacatctacaatgataaagtagctggttttgctaaattcctaaaaactaattgttgtcgcttccaagaaaaggacgaagatgacaatttaattgattcttactttgtagttaagagacacactttctctaactaccaacatgaagaaacaatttataatttacttaaggattgtccagctgttgctaaacatgacttctttaagtttagaatagacggtgacatggtaccacatatatcacgtcaacgtcttactaaatacacaatggcagacctcgtctatgctttaaggcattttgatgaaggtaattgtgacacattaaaagaaatacttgtcacatacaattgttgtgatgatgattatttcaataaaaaggactggtatgattttgtagaaaacccagatatattacgcgtatacgccaacttaggtgaacgtgtacgccaagctttgttaaaaacagtacaattctgtgatgccatgcgaaatgctggtattgttggtgtactgacattagataatcaagatctcaatggtaactggtatgatttcggtgatttcatacaaaccacgccaggtagtggagttcctgttgtagattcttattattcattgttaatgcctatattaaccttgaccagggctttaactgcagagtcacatgttgacactgacttaacaaagccttacattaagtgggatttgttaaaatatgacttcacggaagagaggttaaaactctttgaccgttattttaaatattgggatcagacataccacccaaattgtgttaactgtttggatgacagatgcattctgcattgtgcaaactttaatgttttattctctacagtgttcccacctacaagttttggaccactagtgagaaaaatatttgttgatggtgttccatttgtagtttcaactggataccacttcagagagctaggtgttgtacataatcaggatgtaaacttacatagctctagacttagttttaaggaattacttgtgtatgctgctgaccctgctatgcacgctgcttctggtaatctattactagataaacgcactacgtgcttttcagtagctgcacttactaacaatgttgcttttcaaactgtcaaacccggtaattttaacaaagacttctatgactttgctgtgtctaagggtttctttaaggaaggaagttctgttgaattaaaacacttcttctttgctcaggatggtaatgctgctatcagcgattatgactactatcgttataatctaccaacaatgtgtgatatcagacaactactatttgtagttgaagttgttgataagtactttgattgttacgatggtggctgtattaatgctaaccaagtcatcgtcaacaacctagacaaatcagctggttttccatttaataaatggggtaaggctagactttattatgattcaatgagttatgaggatcaagatgcacttttcgcatatacaaaacgtaatgtcatccctactataactcaaatgaatcttaagtatgccattagtgcaaagaatagagctcgcaccgtagctggtgtctctatctgtagtactatgaccaatagacagtttcatcaaaaattattgaaatcaatagccgccactagaggagctactgtagtaattggaacaagcaaattctatggtggttggcacaacatgttaaaaactgtttatagtgatgtagaaaaccctcaccttatgggttgggattatcctaaatgtgatagagccatgcctaacatgcttagaattatggcctcacttgttcttgctcgcaaacatacaacgtgttgtagcttgtcacaccgtttctatagattagctaatgagtgtgctcaagtattgagtgaaatggtcatgtgtggcggttcactatatgttaaaccaggtggaacctcatcaggagatgccacaactgcttatgctaatagtgtttttaacatttgtcaagctgtcacggccaatgttaatgcacttttatctactgatggtaacaaaattgccgataagtatgtccgcaatttacaacacagactttatgagtgtctctatagaaatagagatgttgacacagactttgtgaatgagttttacgcatatttgcgtaaacatttctcaatgatgatactctctgacgatgctgttgtgtgtttcaatagcacttatgcatctcaaggtctagtggctagcataaagaactttaagtcagttctttattatcaaaacaatgtttttatgtctgaagcaaaatgttggactgagactgaccttactaaaggacctcatgaattttgctctcaacatacaatgctagttaaacagggtgatgattatgtgtaccttccttacccagatccatcaagaatcctaggggccggctgttttgtagatgatatcgtaaaaacagatggtacacttatgattgaacggttcgtgtctttagctatagatgcttacccacttactaaacatcctaatcaggagtatgctgatgtctttcatttgtacttacaatacataagaaagctacatgatgagttaacaggacacatgttagacatgtattctgttatgcttactaatgataacacttcaaggtattgggaacctgagttttatgaggctatgtacacaccgcatacagtcttacaggctgttggggcttgtgttctttgcaattcacagacttcattaagatgtggtgcttgcatacgtagaccattcttatgttgtaaatgctgttacgaccatgtcatatcaacatcacataaattagtcttgtctgttaatccgtatgtttgcaatgctccaggttgtgatgtcacagatgtgactcaactttacttaggaggtatgagctattattgtaaatcacataaaccacccattagttttccattgtgtgctaatggacaagtttttggtttatataaaaatacatgtgttggtagcgataatgttactgactttaatgcaattgcaacatgtgactggacaaatgctggtgattacattttagctaacacctgtactgaaagactcaagctttttgcagcagaaacgctcaaagctactgaggagacatttaaactgtcttatggtattgctactgtacgtgaagtgctgtctgacagagaattacatctttcatgggaagttggtaaacctagaccaccacttaaccgaaattatgtctttactggttatcgtgtaactaaaaacagtaaagtacaaataggagagtacacctttgaaaaaggtgactatggtgatgctgttgtttaccgaggtacaacaacttacaaattaaatgttggtgattattttgtgctgacatcacatacagtaatgccattaagtgcacctacactagtgccacaagagcactatgttagaattactggcttatacccaacactcaatatctcagatgagttttctagcaatgttgcaaattatcaaaaggttggtatgcaaaagtattctacactccagggaccacctggtactggtaagagtcattttgctattggcctagctctctactacccttctgctcgcatagtgtatacagcttgctctcatgccgctgttgatgcactatgtgagaaggcattaaaatatttgcctatagataaatgtagtagaattatacctgcacgtgctcgtgtagagtgttttgataaattcaaagtgaattcaacattagaacagtatgtcttttgtactgtaaatgcattgcctgagacgacagcagatatagttgtctttgatgaaatttcaatggccacaaattatgatttgagtgttgtcaatgccagattacgtgctaagcactatgtgtacattggcgaccctgctcaattacctgcaccacgcacattgctaactaagggcacactagaaccagaatatttcaattcagtgtgtagacttatgaaaactataggtccagacatgttcctcggaacttgtcggcgttgtcctgctgaaattgttgacactgtgagtgctttggtttatgataataagcttaaagcacataaagacaaatcagctcaatgctttaaaatgttttataagggtgttatcacgcatgatgtttcatctgcaattaacaggccacaaataggcgtggtaagagaattccttacacgtaaccctgcttggagaaaagctgtctttatttcaccttataattcacagaatgctgtagcctcaaagattttgggactaccaactcaaactgttgattcatcacagggctcagaatatgactatgtcatattcactcaaaccactgaaacagctcactcttgtaatgtaaacagatttaatgttgctattaccagagcaaaagtaggcatactttgcataatgtctgatagagacctttatgacaagttgcaatttacaagtcttgaaattccacgtaggaatgtggcaactttacaagctgaaaatgtaacaggactctttaaagattgtagtaaggtaatcactgggttacatcctacacaggcacctacacacctcagtgttgacactaaattcaaaactgaaggtttatgtgttgacatacctggcatacctaaggacatgacctatagaagactcatctctatgatgggttttaaaatgaattatcaagttaatggttaccctaacatgtttatcacccgcgaagaagctataagacatgtacgtgcatggattggcttcgatgtcgaggggtgtcatgctactagagaagctgttggtaccaatttacctttacagctaggtttttctacaggtgttaacctagttgctgtacctacaggttatgttgatacacctaataatacagatttttccagagttagtgctaaaccaccgcctggagatcaatttaaacacctcataccacttatgtacaaaggacttccttggaatgtagtgcgtataaagattgtacaaatgttaagtgacacacttaaaaatctctctgacagagtcgtatttgtcttatgggcacatggctttgagttgacatctatgaagtattttgtgaaaataggacctgagcgcacctgttgtctatgtgatagacgtgccacatgcttttccactgcttcagacacttatgcctgttggcatcattctattggatttgattacgtctataatccgtttatgattgatgttcaacaatggggttttacaggtaacctacaaagcaaccatgatctgtattgtcaagtccatggtaatgcacatgtagctagttgtgatgcaatcatgactaggtgtctagctgtccacgagtgctttgttaagcgtgttgactggactattgaatatcctataattggtgatgaactgaagattaatgcggcttgtagaaaggttcaacacatggttgttaaagctgcattattagcagacaaattcccagttcttcacgacattggtaaccctaaagctattaagtgtgtacctcaagctgatgtagaatggaagttctatgatgcacagccttgtagtgacaaagcttataaaatagaagaattattctattcttatgccacacattctgacaaattcacagatggtgtatgcctattttggaattgcaatgtcgatagatatcctgctaattccattgtttgtagatttgacactagagtgctatctaaccttaacttgcctggttgtgatggtggcagtttgtatgtaaataaacatgcattccacacaccagcttttgataaaagtgcttttgttaatttaaaacaattaccatttttctattactctgacagtccatgtgagtctcatggaaaacaagtagtgtcagatatagattatgtaccactaaagtctgctacgtgtataacacgttgcaatttaggtggtgctgtctgtagacatcatgctaatgagtacagattgtatctcgatgcttataacatgatgatctcagctggctttagcttgtgggtttacaaacaatttgatacttataacctctggaacacttttacaagacttcagagtttagaaaatgtggcttttaatgttgtaaataagggacactttgatggacaacagggtgaagtaccagtttctatcattaataacactgtttacacaaaagttgatggtgttgatgtagaattgtttgaaaataaaacaacattacctgttaatgtagcatttgagctttgggctaagcgcaacattaaaccagtaccagaggtgaaaatactcaataatttgggtgtggacattgctgctaatactgtgatctgggactacaaaagagatgctccagcacatatatctactattggtgtttgttctatgactgacatagccaagaaaccaactgaaacgatttgtgcaccactcactgtcttttttgatggtagagttgatggtcaagtagacttatttagaaatgcccgtaatggtgttcttattacagaaggtagtgttaaaggtttacaaccatctgtaggtcccaaacaagctagtcttaatggagtcacattaattggagaagccgtaaaaacacagttcaattattataagaaagttgatggtgttgtccaacaattacctgaaacttactttactcagagtagaaatttacaagaatttaaacccaggagtcaaatggaaattgatttcttagaattagctatggatgaattcattgaacggtataaattagaaggctatgccttcgaacatatcgtttatggagattttagtcatagtcagttaggtggtttacatctactgattggactagctaaacgttttaaggaatcaccttttgaattagaagattttattcctatggacagtacagttaaaaactatttcataacagatgcgcaaacaggttcatctaagtgtgtgtgttctgttattgatttattacttgatgattttgttgaaataataaaatcccaagatttatctgtagtttctaaggttgtcaaagtgactattgactatacagaaatttcatttatgctttggtgtaaagatggccatgtagaaacattttacccaaaattacaatctagtcaagcgtggcaaccgggtgttgctatgcctaatctttacaaaatgcaaagaatgctattagaaaagtgtgaccttcaaaattatggtgatagtgcaacattacctaaaggcataatgatgaatgtcgcaaaatatactcaactgtgtcaatatttaaacacattaacattagctgtaccctataatatgagagttatacattttggtgctggttctgataaaggagttgcaccaggtacagctgttttaagacagtggttgcctacgggtacgctgcttgtcgattcagatcttaatgactttgtctctgatgcagattcaactttgattggtgattgtgcaactgtacatacagctaataaatgggatctcattattagtgatatgtacgaccctaagactaaaaatgttacaaaagaaaatgactctaaagagggttttttcacttacatttgtgggtttatacaacaaaagctagctcttggaggttccgtggctataaagataacagaacattcttggaatgctgatctttataagctcatgggacacttcgcatggtggacagcctttgttactaatgtgaatgcgtcatcatctgaagcatttttaattggatgtaattatcttggcaaaccacgcgaacaaatagatggttatgtcatgcatgcaaattacatattttggaggaatacaaatccaattcagttgtcttcctattctttatttgacatgagtaaatttccccttaaattaaggggtactgctgttatgtctttaaaagaaggtcaaatcaatgatatgattttatctcttcttagtaaaggtagacttataattagagaaaacaacagagttgttatttctagtgatgttcttgttaacaactaaacgaacaatgtttgtttttcttgttttattgccactagtctctagtcagtgtgttaatcttacaaccagaactcaattaccccctgcatacactaattctttcacacgtggtgtttattaccctgacaaagttttcagatcctcagttttacattcaactcaggacttgttcttacctttcttttccaatgttacttggttccatgctatacatgtctctgggaccaatggtactaagaggtttgataaccctgtcctaccatttaatgatggtgtttattttgcttccactgagaagtctaacataataagaggctggatttttggtactactttagattcgaagacccagtccctacttattgttaataacgctactaatgttgttattaaagtctgtgaatttcaattttgtaatgatccatttttgggtgtttattaccacaaaaacaacaaaagttggatggaaagtgagttcagagtttattctagtgcgaataattgcacttttgaatatgtctctcagccttttcttatggaccttgaaggaaaacagggtaatttcaaaaatcttagggaatttgtgtttaagaatattgatggttattttaaaatatattctaagcacacgcctattaatttagtgcgtgatctccctcagggtttttcggctttagaaccattggtagatttgccaataggtattaacatcactaggtttcaaactttacttgctttacatagaagttatttgactcctggtgattcttcttcaggttggacagctggtgctgcagcttattatgtgggttatcttcaacctaggacttttctattaaaatataatgaaaatggaaccattacagatgctgtagactgtgcacttgaccctctctcagaaacaaagtgtacgttgaaatccttcactgtagaaaaaggaatctatcaaacttctaactttagagtccaaccaacagaatctattgttagatttcctaatattacaaacttgtgcccttttggtgaagtttttaacgccaccagatttgcatctgtttatgcttggaacaggaagagaatcagcaactgtgttgctgattattctgtcctatataattccgcatcattttccacttttaagtgttatggagtgtctcctactaaattaaatgatctctgctttactaatgtctatgcagattcatttgtaattagaggtgatgaagtcagacaaatcgctccagggcaaactggaaagattgctgattataattataaattaccagatgattttacaggctgcgttatagcttggaattctaacaatcttgattctaaggttggtggtaattataattacctgtatagattgtttaggaagtctaatctcaaaccttttgagagagatatttcaactgaaatctatcaggccggtagcacaccttgtaatggtgttgaaggttttaattgttactttcctttacaatcatatggtttccaacccactaatggtgttggttaccaaccatacagagtagtagtactttcttttgaacttctacatgcaccagcaactgtttgtggacctaaaaagtctactaatttggttaaaaacaaatgtgtcaatttcaacttcaatggtttaacaggcacaggtgttcttactgagtctaacaaaaagtttctgcctttccaacaatttggcagagacattgctgacactactgatgctgtccgtgatccacagacacttgagattcttgacattacaccatgttcttttggtggtgtcagtgttataacaccaggaacaaatacttctaaccaggttgctgttctttatcaggatgttaactgcacagaagtccctgttgctattcatgcagatcaacttactcctacttggcgtgtttattctacaggttctaatgtttttcaaacacgtgcaggctgtttaataggggctgaacatgtcaacaactcatatgagtgtgacatacccattggtgcaggtatatgcgctagttatcagactcagactaattctcctcggcgggcacgtagtgtagctagtcaatccatcattgcctacactatgtcacttggtgcagaaaattcagttgcttactctaataactctattgccatacccacaaattttactattagtgttaccacagaaattctaccagtgtctatgaccaagacatcagtagattgtacaatgtacatttgtggtgattcaactgaatgcagcaatcttttgttgcaatatggcagtttttgtacacaattaaaccgtgctttaactggaatagctgttgaacaagacaaaaacacccaagaagtttttgcacaagtcaaacaaatttacaaaacaccaccaattaaagattttggtggttttaatttttcacaaatattaccagatccatcaaaaccaagcaagaggtcatttattgaagatctacttttcaacaaagtgacacttgcagatgctggcttcatcaaacaatatggtgattgccttggtgatattgctgctagagacctcatttgtgcacaaaagtttaacggccttactgttttgccacctttgctcacagatgaaatgattgctcaatacacttctgcactgttagcgggtacaatcacttctggttggacctttggtgcaggtgctgcattacaaataccatttgctatgcaaatggcttataggtttaatggtattggagttacacagaatgttctctatgagaaccaaaaattgattgccaaccaatttaatagtgctattggcaaaattcaagactcactttcttccacagcaagtgcacttggaaaacttcaagatgtggtcaaccaaaatgcacaagctttaaacacgcttgttaaacaacttagctccaattttggtgcaatttcaagtgttttaaatgatatcctttcacgtcttgacaaagttgaggctgaagtgcaaattgataggttgatcacaggcagacttcaaagtttgcagacatatgtgactcaacaattaattagagctgcagaaatcagagcttctgctaatcttgctgctactaaaatgtcagagtgtgtacttggacaatcaaaaagagttgatttttgtggaaagggctatcatcttatgtccttccctcagtcagcacctcatggtgtagtcttcttgcatgtgacttatgtccctgcacaagaaaagaacttcacaactgctcctgccatttgtcatgatggaaaagcacactttcctcgtgaaggtgtctttgtttcaaatggcacacactggtttgtaacacaaaggaatttttatgaaccacaaatcattactacagacaacacatttgtgtctggtaactgtgatgttgtaataggaattgtcaacaacacagtttatgatcctttgcaacctgaattagactcattcaaggaggagttagataaatattttaagaatcatacatcaccagatgttgatttaggtgacatctctggcattaatgcttcagttgtaaacattcaaaaagaaattgaccgcctcaatgaggttgccaagaatttaaatgaatctctcatcgatctccaagaacttggaaagtatgagcagtatataaaatggccatggtacatttggctaggttttatagctggcttgattgccatagtaatggtgacaattatgctttgctgtatgaccagttgctgtagttgtctcaagggctgttgttcttgtggatcctgctgcaaatttgatgaagacgactctgagccagtgctcaaaggagtcaaattacattacacataaacgaacttatggatttgtttatgagaatcttcacaattggaactgtaactttgaagcaaggtgaaatcaaggatgctactccttcagattttgttcgcgctactgcaacgataccgatacaagcctcactccctttcggatggcttattgttggcgttgcacttcttgctgtttttcagagcgcttccaaaatcataaccctcaaaaagagatggcaactagcactctccaagggtgttcactttgtttgcaacttgctgttgttgtttgtaacagtttactcacaccttttgctcgttgctgctggccttgaagccccttttctctatctttatgctttagtctacttcttgcagagtataaactttgtaagaataataatgaggctttggctttgctggaaatgccgttccaaaaacccattactttatgatgccaactattttctttgctggcatactaattgttacgactattgtataccttacaatagtgtaacttcttcaattgtcattacttcaggtgatggcacaacaagtcctatttctgaacatgactaccagattggtggttatactgaaaaatgggaatctggagtaaaagactgtgttgtattacacagttacttcacttcagactattaccagctgtactcaactcaattgagtacagacactggtgttgaacatgttaccttcttcatctacaataaaattgttgatgagcctgaagaacatgtccaaattcacacaatcgacggttcatccggagttgttaatccagtaatggaaccaatttatgatgaaccgacgacgactactagcgtgcctttgtaagcacaagctgatgagtacgaacttatgtactcattcgtttcggaagagacaggtacgttaatagttaatagcgtacttctttttcttgctttcgtggtattcttgctagttacactagccatccttactgcgcttcgattgtgtgcgtactgctgcaatattgttaacgtgagtcttgtaaaaccttctttttacgtttactctcgtgttaaaaatctgaattcttctagagttcctgatcttctggtctaaacgaactaaatattatattagtttttctgtttggaactttaattttagccatggcagattccaacggtactattaccgttgaagagcttaaaaagctccttgaacaatggaacctagtaataggtttcctattccttacatggatttgtcttctacaatttgcctatgccaacaggaataggtttttgtatataattaagttaattttcctctggctgttatggccagtaactttagcttgttttgtgcttgctgctgtttacagaataaattggatcaccggtggaattgctatcgcaatggcttgtcttgtaggcttgatgtggctcagctacttcattgcttctttcagactgtttgcgcgtacgcgttccatgtggtcattcaatccagaaactaacattcttctcaacgtgccactccatggcactattctgaccagaccgcttctagaaagtgaactcgtaatcggagctgtgatccttcgtggacatcttcgtattgctggacaccatctaggacgctgtgacatcaaggacctgcctaaagaaatcactgttgctacatcacgaacgctttcttattacaaattgggagcttcgcagcgtgtagcaggtgactcaggttttgctgcatacagtcgctacaggattggcaactataaattaaacacagaccattccagtagcagtgacaatattgctttgcttgtacagtaagtgacaacagatgtttcatctcgttgactttcaggttactatagcagagatattactaattattatgaggacttttaaagtttccatttggaatcttgattacatcataaacctcataattaaaaatttatctaagtcactaactgagaataaatattctcaattagatgaagagcaaccaatggagattgattaaacgaacatgaaaattattcttttcttggcactgataacactcgctacttgtgagctttatcactaccaagagtgtgttagaggtacaacagtacttttaaaagaaccttgctcttctggaacatacgagggcaattcaccatttcatcctctagctgataacaaatttgcactgacttgctttagcactcaatttgcttttgcttgtcctgacggcgtaaaacacgtctatcagttacgtgccagatcagtttcacctaaactgttcatcagacaagaggaagttcaagaactttactctccaatttttcttattgttgcggcaatagtgtttataacactttgcttcacactcaaaagaaagacagaatgattgaactttcattaattgacttctatttgtgctttttagcctttctgctattccttgttttaattatgcttattatcttttggttctcacttgaactgcaagatcataatgaaacttgtcacgcctaaacgaacatgaaatttcttgttttcttaggaatcatcacaactgtagctgcatttcaccaagaatgtagtttacagtcatgtactcaacatcaaccatatgtagttgatgacccgtgtcctattcacttctattctaaatggtatattagagtaggagctagaaaatcagcacctttaattgaattgtgcgtggatgaggctggttctaaatcacccattcagtacatcgatatcggtaattatacagtttcctgtttaccttttacaattaattgccaggaacctaaattgggtagtcttgtagtgcgttgttcgttctatgaagactttttagagtatcatgacgttcgtgttgttttagatttcatctaaacgaacaaactaaaatgtctgataatggaccccaaaatcagcgaaatgcaccccgcattacgtttggtggaccctcagattcaactggcagtaaccagaatggagaacgcagtggggcgcgatcaaaacaacgtcggccccaaggtttacccaataatactgcgtcttggttcaccgctctcactcaacatggcaaggaagaccttaaattccctcgaggacaaggcgttccaattaacaccaatagcagtccagatgaccaaattggctactaccgaagagctaccagacgaattcgtggtggtgacggtaaaatgaaagatctcagtccaagatggtatttctactacctaggaactgggccagaagctggacttccctatggtgctaacaaagacggcatcatatgggttgcaactgagggagccttgaatacaccaaaagatcacattggcacccgcaatcctgctaacaatgctgcaatcgtgctacaacttcctcaaggaacaacattgccaaaaggcttctacgcagaagggagcagaggcggcagtcaagcctcttctcgttcctcatcacgtagtcgcaacagttcaagaaattcaactccaggcagcagtaggggaacttctcctgctagaatggctggcaatggcggtgatgctgctcttgctttgctgctgcttgacagattgaaccagcttgagagcaaaatgtctggtaaaggccaacaacaacaaggccaaactgtcactaagaaatctgctgctgaggcttctaagaagcctcggcaaaaacgtactgccactaaagcatacaatgtaacacaagctttcggcagacgtggtccagaacaaacccaaggaaattttggggaccaggaactaatcagacaaggaactgattacaaacattggccgcaaattgcacaatttgcccccagcgcttcagcgttcttcggaatgtcgcgcattggcatggaagtcacaccttcgggaacgtggttgacctacacaggtgccatcaaattggatgacaaagatccaaatttcaaagatcaagtcattttgctgaataagcatattgacgcatacaaaacattcccaccaacagagcctaaaaaggacaaaaagaagaaggctgatgaaactcaagccttaccgcagagacagaagaaacagcaaactgtgactcttcttcctgctgcagatttggatgatttctccaaacaattgcaacaatccatgagcagtgctgactcaactcaggcctaaactcatgcagaccacacaaggcagatgggctatataaacgttttcgcttttccgtttacgatatatagtctactcttgtgcagaatgaattctcgtaactacatagcacaagtagatgtagttaactttaatctcacatagcaatctttaatcagtgtgtaacattagggaggacttgaaagagccaccacattttcaccgaggccacgcggagtacgatcgagtgtacagtgaacaatgctagggagagctgcctatatggaagagccctaatgtgtaaaattaattttagtagtgctatccccatgtgattttaatagcttcttaggagaatgac$";
        int[] suff_arr = suffixArray(txt);
        int[] lcp = kasai(txt, suff_arr);
        lcp(txt, suff_arr, lcp);
    }
}