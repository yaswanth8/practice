package com.careerit.jfs.leetcode;

class Solution {

    public static void main(String[] args) {

        Solution sc=new Solution();
        char[] s = {'h', 'e', 'l', 'l', 'o'};
        sc.reverseString(s);
    }
    public void reverseString(char[] s) {
        helper(0,s.length-1,s);
    }
    public void helper(int start,int end,char[] s){
        if(start>end) return ;

        char temp = s[end];
        s[end] = s[start];
        s[start] = temp;
        helper(start + 1, end - 1, s);


    }
}
