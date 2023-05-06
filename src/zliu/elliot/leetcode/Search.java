package zliu.elliot.leetcode;

import java.util.Comparator;
import java.util.TreeSet;

public class Search {

    /**
     * 33. 搜索旋转排序数组
     * @param nums
     * @param target
     * @return
     */
    public int search(int[] nums, int target) {
        int l = 0, r = nums.length - 1, mid = 0;
        while (l <= r) {
            mid = (l+r) >> 1;
            if (nums[mid] == target) {
                return mid;
            }
            if (nums[mid] >= nums[0]) {
                // 中间>左端
                if (nums[mid] > nums[nums.length - 1]) {
                    // 有旋转
                    if (target < nums[l] || target > nums[mid]) {
                        l = mid + 1;
                    } else {
                        r = mid - 1;
                    }
                } else {
                    // 无旋转
                    if (target < nums[mid]) {
                        r = mid - 1;
                    }
                    if (target > nums[mid]) {
                        l = mid + 1;
                    }
                }
            } else {
                // 有旋转
                if (target > nums[mid] && target <= nums[r]) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
        }
        return -1;
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
