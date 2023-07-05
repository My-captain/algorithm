package zliu.elliot.leetcode;

import java.util.Deque;
import java.util.LinkedList;

class MinStack {

    Deque<Integer> stack = null;
    Deque<Integer> minStack = null;

    public MinStack() {
        this.stack = new LinkedList<>();
        this.minStack = new LinkedList<>();
    }

    public void push(int val) {
        this.stack.push(val);
        if (this.minStack.size() > 0 ) {
            this.minStack.push(Math.min(this.minStack.peek(), val));
        } else {
            this.minStack.push(val);
        }
    }

    public void pop() {
        this.stack.pop();
        this.minStack.pop();
    }

    public int top() {
        return this.stack.peek();
    }

    public int getMin() {
        return this.minStack.peek();
    }
}
