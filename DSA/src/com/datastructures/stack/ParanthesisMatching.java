package com.datastructures.stack;

public class ParanthesisMatching {

    private StackUsingArray stack;

    public boolean isMatch(String expression) {
        char[] charArray = expression.toCharArray();
        stack = new StackUsingArray(charArray.length);

        for (char c : charArray) {
            if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                if (stack.isEmpty()) {
                    return false;
                } else {
                    stack.pop();
                }
            }
        }
        return stack.isEmpty();
    }

    public boolean isMatchAdvanced(String expression) {
        char[] charArray = expression.toCharArray();
        stack = new StackUsingArray(charArray.length);

        for (char c : charArray) {
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else if (c == ')' || c == ']' || c == '}') {
                if (stack.isEmpty()) {
                    return false;
                } else {
                    int x = stack.pop();
                    if (!((c - x == 1) || (c - x == 2) || (c - x == 2))) {
                        return false;
                    }

                }
            }
        }
        return stack.isEmpty();
    }


}
