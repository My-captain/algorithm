package zliu.elliot.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class PrefixSumSlideWindow {

    /**
     * tag:同向双指针、滑动窗口
     * 两个指针表示一个子区间，根据条件动态的向前蛄蛹~~~
     * 209. 长度最小的子数组
     * 找出该数组中满足其和 ≥ target 的长度最小的 连续子数组 [numsl, numsl+1, ..., numsr-1, numsr] ，并返回其长度。
     * 如果不存在符合条件的子数组，返回 0 。
     * @param target
     * @param nums
     * @return
     */
    public int minSubArrayLen(int target, int[] nums) {
        int minLen = nums.length+1;
        int sum = 0;
        int l = 0;
        for(int r = 0; r<nums.length;++r) {
            sum += nums[r];
            while(sum-nums[l] >= target) {
                sum -= nums[l];
                ++l;
            }
            if (sum >= target) {
                minLen = Math.min(r-l+1, minLen);
            }
        }
        return minLen <= nums.length ? minLen : 0;
    }

    /**
     * tag:同向双指针、滑动窗口
     * 与209类似，但此处只统计以右界为右端点的子区间个数
     * 因为其他子区间必然被之前枚举右端点时统计过
     * 713. 乘积小于 K 的子数组
     * 返回子数组内所有元素的乘积严格小于 k 的连续子数组的数目。
     * @param nums
     * @param k
     * @return
     */
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        int cnt = 0;
        int acc = 1;
        int l = 0;
        for(int r = 0; r<nums.length;++r) {
            acc *= nums[r];
            while(acc>=k && l <= r) {
                acc /= nums[l];
                ++l;
            }
            if (acc < k) {
                // 只统计以右界为右端点的子区间个数
                cnt += (r-l+1);
            }
        }
        return cnt;
    }

    /**
     * tag: 前缀和、相向双指针
     * 167. 两数之和 II - 输入有序数组
     * @param numbers
     * @param target
     * @return
     */
    public int[] twoSum(int[] numbers, int target) {
        int l = 0;
        int r = numbers.length-1;
        while(true) {
            int sum = numbers[l] + numbers[r];
            if(sum < target) {
                ++l;
            } else if (sum > target) {
                --r;
            } else {
                return new int[]{l+1,r+1};
            }
        }
    }

    /**
     * tag:前缀和、相向双指针
     * 排序、固定一个左端点，用双指针去搜索答案
     * 15. 三数之和
     * 给你一个整数数组 nums ，判断是否存在三元组满足 i != j、i != k 且 j != k ，同时还满足三数之和为0
     * 你返回所有和为 0 且不重复的三元组。
     * 注意：答案中不可以包含重复的三元组。
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        int sum;
        List<List<Integer>> result = new ArrayList<>();
        for(int i = 0; i< nums.length-2; ++i) {
            if (i > 0 && nums[i] == nums[i-1]) {
                continue;
            }
            // 如果➕两侧边界的值，仍超出在0轴以上/下，则没必要继续搜索
            if (nums[i] + nums[i+1]+ nums[i+2] >0) {
                break;
            }
            if (nums[i] + nums[nums.length-1]+ nums[nums.length-2] < 0) {
                continue;
            }
            int j = i+1;
            int k = nums.length-1;
            while (j<k) {
                sum = nums[j]+nums[k];
                if(sum < -nums[i]) {
                    ++j;
                    while (j<k && nums[j]==nums[j-1]) {
                        ++j;
                    }
                } else if (sum > -nums[i]) {
                    --k;
                    while(j<k && nums[k] == nums[k+1]) {
                        --k;
                    }
                } else {
                    List<Integer> res= new ArrayList<Integer>();
                    res.add(nums[i]);
                    res.add(nums[j]);
                    res.add(nums[k]);
                    result.add(res);
                    ++j;
                    while (j<k && nums[j]==nums[j-1]) {
                        ++j;
                    }
                    --k;
                    while(j<k && nums[k] == nums[k+1]) {
                        --k;
                    }
                }
            }
        }
        return result;
    }

    /**
     * 对于每个柱子，能接的雨水量取决于min(左侧最高, 右侧最高)-height
     * 考虑从两侧向中间求前后缀和
     *      若 prefix < suffix, 则可以确定左指针的柱子能接的水为prefix-height, 并将左指针右移
     *      反之右指针左移
     * 42. 接雨水
     * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
     * @param height
     * @return
     */
    public int trap(int[] height) {
        int prefix, suffix, l, r, ans;
        prefix = suffix = l = ans = 0;
        r = height.length - 1;
        while (l <= r) {
            prefix = Math.max(prefix, height[l]);
            suffix = Math.max(suffix, height[r]);
            if (prefix < suffix) {
                ans += (prefix - height[l]);
                ++l;
            } else {
                ans += (suffix - height[r]);
                --r;
            }
        }
        return ans;
    }

    /**
     * 11. 盛最多水的容器
     * 给定一个长度为 n 的整数数组 height 。有 n 条垂线，第 i 条线的两个端点是 (i, 0) 和 (i, height[i]) 。
     * 找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。返回容器可以储存的最大水量。
     * @param height
     * @return
     */
    public int maxArea(int[] height) {
        int maxArea = 0;
        int l = 0, r= height.length - 1;
        while (l < r) {
            maxArea = Math.max(Math.min(height[l], height[r])*(r-l), maxArea);
            if (height[l] < height[r]) {
                ++l;
            } else {
                --r;
            }
        }
        return maxArea;
    }

    public static void main(String[] args) {
        PrefixSumSlideWindow prefixSumSlideWindow = new PrefixSumSlideWindow();
        prefixSumSlideWindow.maxArea(new int[]{1,8,6,2,5,4,8,3,7});
    }


}
