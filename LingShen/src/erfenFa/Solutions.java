package erfenFa;

import com.sun.jdi.Value;

import javax.swing.*;
import java.util.*;

public class Solutions {

    /**
     * 二分查找模板 O(log n)
     * 条件：数组有序
     */
    public int find(int[]nums,int target){
        Arrays.sort(nums);
        int left = 0,right = nums.length-1;//left,right均在考虑范围内
        while(left<=right){
            int mid = (right - left)/2 + left;
            if(nums[mid]>target){
                right = mid - 1;//mid不符合条件，因此跳过
            }else if(nums[mid]<target){
                left = mid + 1;//mid跳过
            }else{
                return mid;
            }
        }
        return -1;//没有找到该值
    }

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

    /**
     * 统计公平数对
     * @param nums
     * @param lower
     * @param upper
     * @return
     */
    public long countFairPairs(int[] nums, int lower, int upper) {
        Arrays.sort(nums);
        long res = 0;
        for(int j = 0;j<nums.length;j++){
            int r =  lowerBound(nums,j,upper-nums[j] + 1);
            int l = lowerBound(nums,j,lower-nums[j]);
            res+=r - l;
        }
        return res;
    }
    private int lowerBound(int[]nums,int right,int target){
        int left = -1;
        while(left+1<right){
            int mid = (right - left)/2 + left;
            if(nums[mid]<target){
               left = mid;
            }else{
                right = mid;
            }
        }
        return right;
    }


    /**
     * 基于时间戳存储数据
     */
    class TimeMap {

        private class Value{
            int timestamp;
            String value;
            public Value(){

            }
            public Value(int timestamp,String value){
                this.timestamp = timestamp;
                this.value = value;
            }
        }
        Map<String,List<Value>>map;
        public TimeMap() {
            map = new HashMap<>();
        }

        public void set(String key, String value, int timestamp) {
            List<Value>list = map.getOrDefault(key,new LinkedList<>());
            Value node = new Value(timestamp,value);
            list.add(node);
            map.put(key,list);
        }

        public String get(String key, int timestamp) {
            List<Value>queue = map.get(key);
            if(queue == null) return "";
            queue.sort(((o1, o2) -> {
                return o2.timestamp - o1.timestamp;
            }));
            if(queue.isEmpty()){
                return "";
            }
            for (Value value : queue) {
                if(value.timestamp<=timestamp){
                    return value.value;
                }
            }
            return "";
        }
    }


    /**
     * 快照数组 内存过大超了
     */
    class SnapshotArray {

        private int len;
        private int snap_id = 0;
        int[]array;
        Map<Integer,int[]>map = new HashMap<>();
        public SnapshotArray(int length) {
            len = length;
            array = new int[length];
            Arrays.fill(array,0);
        }

        public void set(int index, int val) {
            array[index] = val;
        }

        public int snap() {
            snap_id++;
            int[]arr = Arrays.copyOf(array,array.length);
            map.put(snap_id,arr);
            return snap_id-1;
        }

        public int get(int index, int snap_id) {
            int[]array = map.get(snap_id+1);
            return array[index];
        }

    }








}
