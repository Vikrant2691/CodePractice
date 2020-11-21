package com.datastructures.stack;

import java.util.Arrays;

public class InfixToPostfix {


    public String convert(String expression) {

        char[] charArray = expression.toCharArray();
        StackUsingArray stack = new StackUsingArray(expression.length());
        char[] postFix = new char[charArray.length];
        int i = 0;
        int j = 0;

        while (i < charArray.length) {

            if (!isOperator(charArray[i])) {
                postFix[j++] = charArray[i++];
            }
            else {
                if(stack.isEmpty()){
                    stack.push(charArray[i++]);
                }
                else{
                    System.out.println(priority(stack.stackTop())+" "+priority(charArray[i]));
                    System.out.println(priority(stack.stackTop())<priority(charArray[i]));
                    if(priority(stack.stackTop())<priority(charArray[i])){
                        stack.push(charArray[i++]);
                    }
                    else {
                     postFix[j++]= (char) stack.pop();
                    }
                }
            }
        }

        while (!stack.isEmpty()){
            postFix[j++]= (char) stack.pop();
        }
        return Arrays.toString(postFix);
    }

    private boolean isOperator(char x) {
        return x == '+' || x == '-' || x == '*' || x == '/';
    }

    public int priority(char x) {
        if (x == '+' || x == '-')
            return 1;
        else if (x == '*' || x == '/')
            return 2;
        else
            return 0;
    }

}
