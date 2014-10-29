package com.malasang.wheatnight;

import android.util.Log;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.util.EncodingUtils;

/**
 * Created by hemiao.hm on 2014/10/22.
 */
public class Utils {

    // write log to file
    public void writeFileSdcard(String fileName, String message) {
        try {
            FileOutputStream fout = new FileOutputStream(fileName, true);
            byte[] bytes = ("\r\n" + message).getBytes();
            fout.write(bytes);
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //clear log of sdcard
    //TODO need to backup the log
    public void clearFileSdcard(String fileName) {
        try {
            FileOutputStream fout = new FileOutputStream(fileName, true);
            fout.write(null);
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //read file from sdcard
    public String readFileSdcard(String fileName) {
        String res = "";
        try {
            FileInputStream fin = new FileInputStream(fileName);
            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer);
            res = EncodingUtils.getString(buffer, "UTF-8");
            fin.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    //cal the costtime
    //result    0: all the num of message received
    //          1: average time cost
    //          2. max time cost
    //          3. min time cost
    //          4. detail time cost
    public List<String> calMsgTimeCost(String fileName) {
        Integer msgNum = 0;
        String line = "";
        String[] arrs = null;
        List<String> result = new ArrayList<String>();
//        Long[] times = null;
        List<Long> times = new ArrayList<Long>();
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            while ((line = br.readLine()) != null) {
//                msgNum++;
                arrs = line.split(":");
                if (arrs.length > 3) {
//                    Log.e(Constants.TAG, arrs[4] + " " + arrs[2]);
//                    Long j = Long.parseLong(arrs[4].toString());
//                    Long k = Long.parseLong(arrs[2].toString());
                    times.add(msgNum, Long.parseLong(arrs[4]) - Long.parseLong(arrs[2]));
                    msgNum++;
                }
            }
            br.close();
            fr.close();

            result.add(0, msgNum.toString());
            result.add(1, getAverage(times).toString());
            result.add(2, getMax(times).toString());
            result.add(3, getMin(times).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }

    public Long getAverage(List<Long> times) {
        Long total = 0L;
        for (int i = 0; i < times.size(); i++) {
            total += times.get(i);
        }
        return total / times.size();
    }

    public Long getMax(List<Long> times) {
        Long max = times.get(0);
        for (int i = 1; i < times.size(); i++) {
            if (max < times.get(i)) {
                max = times.get(i);
            }
        }
        return max;
    }

    public Long getMin(List<Long> times) {
        Long min = times.get(0);
        for (int i = 1; i < times.size(); i++) {
            if (min > times.get(i)) {
                min = times.get(i);
            }
        }
        return min;
    }


}
