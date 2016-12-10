package com.vroozi.customerui.util;

import com.vroozi.customerui.catalogItem.model.CustomFieldValue;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: qureshit
 * Date: 4/20/13
 * Time: 8:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class BinaryInsertSortCustomFieldsOnDisplayOrder {
    public static void sort(List<CustomFieldValue> a,int n){
        for (int i=0;i<n;++i){
            CustomFieldValue temp=a.get(i);

            int left=0;
            int right=i;
            while (left<right){
                int middle=(left+right)/2;
                if (temp.getDisplayOrder()>=a.get(middle).getDisplayOrder())
                    left=middle+1;
                else
                    right=middle;
            }
            for (int j=i;j>left;--j){
                swap(a,j-1,j);
            }

        }

        for (int i = 0; i < n; ++i) {
            CustomFieldValue temp = a.get(i);
            temp.setDisplayOrder(null);
        }

    }

    //    public static void main(String[] args){
//        int a[]=new int[]{12,10,34,23,9,7,8,5,6};
//        sort(a,a.length);
//        for (int i=0;i<a.length;i++){
//            System.out.println(a[i]);
//        }
//    }
    public static void swap(List<CustomFieldValue> a,int i,int j){
        CustomFieldValue k=a.get(i);
        a.set(i,a.get(j));
        a.set(j, k);
    }
}
