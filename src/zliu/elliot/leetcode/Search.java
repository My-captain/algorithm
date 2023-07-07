package zliu.elliot.leetcode;

import java.util.Comparator;
import java.util.TreeSet;

public class Search {

    /**
     * 704. 二分查找
     * @param nums
     * @param target
     * @return
     */
    public int binarySearch(int[] nums, int target) {
        if (nums.length < 1) {
            return -1;
        }
        int l =0, r = nums.length-1, mid;
        while (l <= r) {
            mid = (l+r) >> 1;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return -1;
    }

    public int lowerBound(int[] nums, int target) {
        int l = 0, r = nums.length-1, mid;
        while (l <= r) {
            mid = l + (r - l)>>1;
            if (nums[mid] > target) {
                l = mid +1;
            } else {
                r = mid-1;
            }
        }
        return l;
    }

    /**
     * 34. 在排序数组中查找元素的第一个和最后一个位置
     * @param nums
     * @param target
     * @return
     */
    public int[] searchRange(int[] nums, int target) {
        if (nums.length < 1) {
            return new int[]{-1, -1};
        } else {
            int l = binarySearch(nums, target, 0, nums.length - 1, true);
            if (nums[l] != target) {
                return new int[]{-1, -1};
            }
            int r = binarySearch(nums, target, l, nums.length - 1, false);
            if (nums[r] != target) {
                --r;
            }
            return new int[]{l, r};
        }
    }

    /**
     * 二分查找第一个≥target的元素下标
     * @param nums
     * @param target
     * @param l
     * @param r
     * @param lower 是否要求＝
     * @return
     */
    public int binarySearch(int[]nums, int target, int l, int r, boolean lower) {
        int ans = r, mid = 0;
        while (l <= r) {
            mid = (l+r) >> 1;
            if (nums[mid] > target || (nums[mid] >= target && lower)) {
                r = mid - 1;
                ans = mid;
            } else {
                l = mid + 1;
            }
        }
        return ans;
    }

    /**
     * 162. 寻找峰值
     * 峰值元素是指其值严格大于左右相邻值的元素。
     * 给你一个整数数组 nums，找到峰值元素并返回其索引。数组可能包含多个峰值，在这种情况下，返回 任何一个峰值 所在位置即可。
     * 你可以假设 nums[-1] = nums[n] = -∞ 。
     * @param nums
     * @return
     */
    public int findPeakElement(int[] nums) {
        int l = 0, r = nums.length-1;
        while (l < r) {
            int mid = (l+r)>>1;
            if (nums[mid]> nums[mid+1]) {
                r = mid;
            } else {
                l = mid+1;
            }
        }
        return l;
    }

    /**
     * 153. 寻找旋转排序数组中的最小值
     * 已知一个长度为 n 的数组，预先按照升序排列，经由 1 到 n 次 旋转 后，得到输入数组。例如，原数组 nums = [0,1,2,4,5,6,7] 在变化后可能得到：
     * 给你一个元素值 互不相同 的数组 nums ，它原来是一个升序排列的数组，并按上述情形进行了多次旋转。请你找出并返回数组中的 最小元素 。
     * @param nums
     * @return
     */
    public int findMin(int[] nums) {
        int l = 0, r = nums.length - 1, tail = nums[nums.length-1];
        while (l <= r) {
            int mid = (l+r)>>1;
            if (nums[mid] > tail) {
                l = mid+1;
            } else {
                r = mid - 1;
            }
        }
        return l;
    }

    /**
     * 33. 搜索旋转排序数组
     * 给你 旋转后 的数组 nums 和一个整数 target ，如果 nums 中存在这个目标值 target ，则返回它的下标，否则返回 -1 。
     * @param nums
     * @param target
     * @return
     */
    public int search(int[] nums, int target) {
        int l = 0, r = nums.length - 1, tail = nums[nums.length-1];
        while (l <= r) {
            int mid = (l+r) >> 1;
            if (target == nums[mid]) {
                return mid;
            }
            if (nums[mid] > tail) {         // 有旋转
                if (target < nums[mid]) {
                    if (target < nums[0]) {
                        l = mid + 1;
                    } else {
                        r = mid - 1;
                    }
                } else {
                    l = mid+1;
                }
            } else {
                if (target > nums[mid]) {
                    if (target > tail) {
                        r = mid - 1;
                    } else {
                        l = mid + 1;
                    }
                } else {
                    r = mid - 1;
                }
            }
        }
        return nums[l] == target ? l : -1;
    }

    class MyCalendarBinarySearch {
        TreeSet<int[]> booked = null;
        public MyCalendarBinarySearch(){
            this.booked = new TreeSet<>(Comparator.comparingInt(a -> a[0]));
        }

        public boolean book(int start, int end) {
            if (this.booked.isEmpty()) {
                this.booked.add(new int[] {start, end});
                return true;
            }
            int[] temp = {end, 0};
            int[] next = this.booked.ceiling(temp);
            int[] prev = next == null ? booked.last():booked.lower(next);
            if (prev != null && prev[1] > start) {
                return false;
            }
            this.booked.add(new int[]{start,end});
            return true;
        }

    }

    public static void main(String[] args) {
        Search search = new Search();
        System.out.println(search.search(new int[]{3,5,1}, 3));
    }














}
