package zliu.elliot.leetcode.SegmentTree;

public class NumArray {

    int[] num = null;
    int[] oriArr = null;

    private void treeInsert(int pos, int l, int r, int target, int val) {
        this.num[pos] += val;
        if (l == r) {
            return;
        }
        int mid = (l + r) >> 1;
        if (target <= mid) {
            treeInsert(2 * pos + 1, l, mid, target, val);
        } else {
            treeInsert(2 * pos + 2, mid + 1, r, target, val);
        }
    }

    private void treeUpdate(int pos, int l, int r, int target, int val, int oriVal) {
        this.num[pos] -= oriVal;
        this.num[pos] += val;
        if (l == r) {
            return;
        }
        int mid = (l + r) >> 1;
        if (target <= mid) {
            treeUpdate(2 * pos + 1, l, mid, target, val, oriVal);
        } else {
            treeUpdate(2 * pos + 2, mid + 1, r, target, val, oriVal);
        }

    }

    private int treeSearch(int pos, int l, int r, int rangeL, int rangeR) {
        if (l == rangeL && r == rangeR) {
            return this.num[pos];
        } else {
            int mid = (l + r) >> 1;
            if (rangeR <= mid) {
                return treeSearch(2 * pos + 1, l, mid, rangeL, rangeR);
            } else if (rangeL > mid) {
                return treeSearch(2 * pos + 2, mid + 1, r, rangeL, rangeR);
            } else {
                return treeSearch(2 * pos + 1, l, mid, rangeL, mid) + treeSearch(2 * pos + 2, mid + 1, r, mid + 1, rangeR);
            }
        }
    }

    public NumArray(int[] nums) {
        this.num = new int[nums.length * 4];
        this.oriArr = nums;
        for (int i = 0; i < nums.length; ++i) {
            treeInsert(0, 0, nums.length - 1, i, nums[i]);
        }
    }

    public void update(int index, int val) {
        treeUpdate(0, 0, this.oriArr.length - 1, index, val, this.oriArr[index]);
        this.oriArr[index] = val;
    }

    public int sumRange(int left, int right) {
        return treeSearch(0, 0, this.oriArr.length - 1, left, right);
    }

    public static void main(String[] args) {
        NumArray numArray = new NumArray(new int[]{1, 3, 5});
        System.out.println(numArray.sumRange(0, 2));
        numArray.update(1, 2);
        System.out.println(numArray.sumRange(0, 2));
    }

}
