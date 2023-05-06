package zliu.elliot.leetcode.SegmentTree;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class MyCalendar {
    /*用于表示整个区间都被预订, 必须有,因为动态线段树并不递归插入到叶子结点, 那么在线段树向下搜索的过程中, 必须判断如果整个区间已经被预订, 则停止向下搜索*/
    Set<Integer> lazy = null;
    HashMap<Integer, Integer> lazyCnt = new HashMap<>();
    int range;

    public MyCalendar() {
        this.lazy = new HashSet<>();
        this.range = (int) 1e9;
    }

    private void treeInsert(int pos, int l, int r, int rangeL, int rangeR, int parentCnt) {
        if (rangeL > r || rangeR < l) {
            // 区间无交叉, 返回
            return;
        } else if (rangeL <= l && rangeR >= r) {
            // 当前segment全部被预订
            lazy.add(pos);
            lazyCnt.put(pos, lazyCnt.getOrDefault(pos, 0)+1+parentCnt);
        } else {
            int mid = (l + r) >> 1;
            treeInsert(2 * pos + 1, l, mid, rangeL, rangeR, Math.max(parentCnt, lazyCnt.getOrDefault(pos, 0)));
            treeInsert(2 * pos + 2, mid + 1, r, rangeL, rangeR, Math.max(parentCnt, lazyCnt.getOrDefault(pos, 0)));
        }
    }

    private boolean treeSearch(int pos, int l, int r, int rangeL, int rangeR) {
        if (l == r) {
            return false;
        }
        if (rangeR < l || rangeL > r) {
            // 区间无交叉, 返回
            return false;
        }
        // 当前整个区间都已经被预订2次, 没有必要继续向下搜索
        if (lazy.contains(pos) && lazyCnt.get(pos) > 1) {
            return true;
        }
        int mid = (l + r) >> 1;
        if (rangeL <= l && rangeR >= r) {
            // 当前segment被目标全部包含
            return treeSearch(2 * pos + 1, l, mid, rangeL, rangeR) | treeSearch(2 * pos + 2, mid + 1, r, rangeL, rangeR);
        } else {
            // 此处可剪枝, 如果目标segment在左子树, 则剪掉右子树的搜索尝试
            if (rangeR <= mid) {
                return treeSearch(2 * pos + 1, l, mid, rangeL, rangeR);
            } else if (rangeL > mid) {
                return treeSearch(2 * pos + 2, mid + 1, r, rangeL, rangeR);
            } else {
                return treeSearch(2 * pos + 1, l, mid, rangeL, rangeR) | treeSearch(2 * pos + 2, mid + 1, r, rangeL, rangeR);
            }
        }

    }

    public boolean book(int start, int end) {
        if (treeSearch(0, 0, this.range, start, end-1)) {
            return false;
        }
        treeInsert(0, 0, this.range, start, end-1, this.lazyCnt.getOrDefault(0, 0));
        return true;
    }


    public static void main(String[] args) {
        MyCalendar myCalendar = new MyCalendar();
        myCalendar.book(10, 20); // returns true
        myCalendar.book(50, 60); // returns true
        myCalendar.book(10, 40); // returns true
        myCalendar.book(5, 15); // returns false
        myCalendar.book(5, 10); // returns true
        myCalendar.book(25, 55); // returns true
    }

}
