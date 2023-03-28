package zliu.elliot.leetcode;

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

    public static void main(String[] args) {
        Search search = new Search();
        System.out.println(search.search(new int[]{3,5,1}, 3));
    }














}
