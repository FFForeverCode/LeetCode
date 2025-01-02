package erfenFa;

import java.util.HashMap;
import java.util.Map;

public class Solutions {

    /**
     * 前缀和+快排+二分法找第一个大于的数
     * @param nums
     * @param queries
     * @return
     */
    public int[] answerQueries(int[] nums, int[] queries) {
        int[]ans = new int[queries.length];
        Quicksort(nums,0,nums.length-1);
        Map<Integer,Integer>map = new HashMap<>();
        int tmp = 0;
        int[]sum = new int[nums.length];
        for(int i = 0;i<nums.length;i++){
            tmp+=nums[i];
            sum[i] = tmp;
        }
        for(int i = 0;i<queries.length;i++){
            int left = 0,right = nums.length-1;
            while(left<=right){
                int mid = (right - left)/2 + left;
                if(sum[mid]>queries[i]){
                    right = mid - 1;
                }else if(sum[mid]<=queries[i]){
                    left = mid + 1;
                }
            }
            ans[i] = left;
        }
        return ans;

    }
    private void Quicksort(int[]nums,int left,int right){
        if(left<right){
            int index = getIndex(nums,0,nums.length-1);
            Quicksort(nums,left,index-1);
            Quicksort(nums,index+1,right);
        }
    }
    private int getIndex(int[]nums,int left,int right){
       int low = left-1;//<flag的子数组的最后一个索引1
       int flag = nums[right];
       for(int i = left;i<right;i++){
           if(nums[i]<flag){
               low++;
               swap(nums,i,low);
           }
       }
       low++;
       swap(nums,right,low);
       return low;
    }
    private void swap(int[]nums,int i,int j){
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}
