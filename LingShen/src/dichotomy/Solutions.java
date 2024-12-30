package dichotomy;

import java.lang.annotation.Target;
import java.util.Arrays;

//二分题单
public class Solutions {

    public int[] answerQueries(int[] nums, int[] queries) {
        int[]ans = new int[queries.length];
        Quicksort(nums,0,nums.length-1);
        for(int i = 0;i<queries.length;i++){
            int index = getIndex(nums,queries[i]);//[0,index)<target
            int sum = 0;
            for(int j = 0;j<=index;j++){
                sum+=nums[j];
                if(sum>queries[i]){
                    ans[i] = j;
                    break;
                }
            }
        }
        return ans;
    }
    private int getIndex(int[]nums,int target){
        int left = 0,right = nums.length-1;
        while(left<=right){
            int mid = (right-left)/2 + left;
            if(nums[mid]==target){
                return mid;
            }else if(nums[mid]>target){
                right = mid - 1;
            }else{
                left = mid + 1;
            }
        }
        return left;
    }
    private static void Quicksort(int[]nums,int left,int right){
        if(left<right) {
            int index = getMid(nums, left, right);
            Quicksort(nums, left, index - 1);
            Quicksort(nums, index + 1, right);
        }
    }
    private static int getMid(int[]nums,int left,int right){
        int point = nums[right];
        int last = left - 1;
        for(int i = left;i<right;i++){
            if(nums[i]<point){
                swap(nums,++last,i);
            }
        }
        swap(nums,++last,right);
        return last;
    }
    private static void swap(int[]nums,int i,int j){
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

}
