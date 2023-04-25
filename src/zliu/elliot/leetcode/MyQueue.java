package zliu.elliot.leetcode;

import java.util.ArrayDeque;
import java.util.Deque;

public class MyQueue {

    Deque<Integer> inStack;
    Deque<Integer> outStack;

    public MyQueue() {
        inStack = new ArrayDeque<>();
        outStack = new ArrayDeque<>();
    }

    private void in2out(){
        while (inStack.size() > 0) {
            this.outStack.push(this.inStack.pop());
        }
    }

    public void push(int x) {
        this.inStack.push(x);
    }

    public int pop() {
        if (this.outStack.size() == 0) {
            this.in2out();
        }
        return this.outStack.pop();
    }

    public int peek() {
        if (this.outStack.size() == 0) {
            this.in2out();
        }
        return this.outStack.peek();
    }

    public boolean empty() {
        return this.inStack.size() == 0 && this.outStack.size() == 0;
    }

}
