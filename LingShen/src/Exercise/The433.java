package Exercise;

import java.util.LinkedList;
import java.util.List;

public class The433 {

    /**
     * 第一题 [start,i] 的数组和
     * @param nums
     * @return
     */
    public int subarraySum(int[] nums) {
        int sum = 0;
        for(int i = 0;i<nums.length;i++){
            int start = Math.max(0,i - nums[i]);
            while(start<=i){
                sum+=nums[start++];
            }
        }
        return sum;
    }

    /**
     * 超时
     * 回溯处理 子序列不超过k的最大数与最小数的和
     * @param nums
     * @return
     */
    private static final int MOD = 1000000007;
    int sum = 0;
    int min = Integer.MAX_VALUE;
    int max = Integer.MIN_VALUE;
    public int minMaxSums(int[] nums, int k) {
        for(int i = 0;i<nums.length;i++){
            max = Integer.MIN_VALUE;
            min = Integer.MAX_VALUE;
            dfs(nums,0,k,i);
        }
        return sum%MOD;
    }
    private void dfs(int[]nums,int cnt,int k,int index){
        if(cnt==k||nums.length == index){
            return;
        }
        int m1 = min;
        int m2 = max;
        min = Math.min(nums[index],min);
        max = Math.max(nums[index],max);
        sum+=min;
        sum%=MOD;
        sum+=max;
        sum%=MOD;
        for(int i = index+1;i<nums.length;i++) {
            dfs(nums, cnt + 1, k, i);
        }
        min = Math.max(m1,min);
        max = Math.min(m2,max);
    }


    /**
     * 超时
     * 回溯处理长度不超过k的子数组的最大数，最小数的和
     */
    long sum1 = 0;
    long min1 = Long.MAX_VALUE;
    long max1 = Long.MIN_VALUE;
    public long minMaxSubarraySum(int[] nums, int k) {
        for(int i = 0;i<nums.length;i++){
            max1 = Long.MIN_VALUE;
            min1 = Long.MAX_VALUE;
            dfs1(nums,0,k,i);
        }
        return sum1;
    }
    private void dfs1(int[]nums,int cnt,int k,int index){
        if(cnt==k||nums.length == index){
            return;
        }
        long m1 = min1;
        long m2 = max1;
        min1 = Math.min(min1,nums[index]);
        max1 = Math.max(max1,nums[index]);
        sum1+=min1;
        sum1+=max1;
        dfs1(nums,cnt+1,k,index+1);
        min1 = Math.max(m1,min1);
        max1 = Math.min(m2,max1);
    }

}
