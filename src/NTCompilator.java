
import com.sun.applet2.AppletParameters;
import edu.duke.DirectoryResource;
import edu.duke.FileResource;
import edu.duke.StorageResource;

import java.util.Map;
import java.util.Scanner.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

/**Note to Chris:
 * Goal: Get HashMap mapping a String(tagtype) to its positions in all XMLs ArrayList<int>
 * Problem: I could not add the int of each file iteration directly to an ArrayList<int> which is inside the HashMap
 * In this class, I first tried to do everything in one method. Iterate each file, get tagtype names, get tagtypes position from each file.
 * This was not possible so I tried it differently in class PositionCompiler.
 * The other two methods (Occurences of a tag type in all files, prefix/sufix text of tag types) work fine***/


public class NTCompilator {

    public HashMap<String,Integer> mapOcurrencesToTagType(){
        String tagType="";
        HashMap<String,Integer> mapQuan = new HashMap<String,Integer>();
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            //System.out.print(xml);
            for (String line : fr.lines()) {
                int idx = line.indexOf("type=", 0);
                int idx2 = line.indexOf(" ", idx);
                int idxKey = line.indexOf("key");
                int idxQuote = line.indexOf("\"", idxKey + 5);
                int idxEnd = line.indexOf("/>");
                if (idx != -1 && idx2 != -1 ) {
                    tagType = line.substring(idx + 5, idx2);
                    if ( idxQuote!=-1) {
                        String genderKey = line.substring(idxKey + 4, idxQuote + 1);
                        //System.out.println(genderKey);
                        if(genderKey.contains("name2")){
                            tagType="dst2";
                        }
                    }
                    if (!mapQuan.keySet().contains(tagType) && tagType != null) {
                        mapQuan.put(tagType, 1);
                    } else {
                        mapQuan.put(tagType, mapQuan.get(tagType) + 1);
                    }


                    }
            }
        }
System.out.println("Number of TagTypes: "+mapQuan.keySet().size());
System.out.println("TagTypes: "+mapQuan.keySet());

System.out.println(mapQuan);
return mapQuan;
    }


    public HashMap<String,String> mapTagtypeToKeys(){

        HashMap<String,String> mapKeys = new HashMap<String,String>();
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            //System.out.print(xml);
            for (String line : fr.lines()) {
                int idx = line.indexOf("type=", 0);
                int idx2 = line.indexOf(" ", idx);
                int idxKey = line.indexOf("key");
                int prefixKey = line.indexOf("prefix");
                int sufixKey = line.indexOf("sufix");
                int idxQuote = line.indexOf("\"", idxKey+5);
                int idxEnd = line.indexOf("/>");
                if (idx != -1 && idx2 != -1 && idxQuote!=-1 && idxEnd!=-1 && (prefixKey!=-1 || sufixKey!=-1)) {
                    String tagType = line.substring(idx + 5, idx2)+":";
                    String attributes = line.substring(prefixKey,idxEnd);
                    //System.out.println(attributes);
                    if ( idxQuote!=-1) {
                        String genderKey = line.substring(idxKey + 4, idxQuote + 1);
                        //System.out.println(genderKey);
                        if(genderKey.contains("name2")){
                            tagType="dst2";
                        }
                    }
                    //System.out.println(attributes);
                    if (!mapKeys.keySet().contains(tagType) && attributes.length()>2) {
                        mapKeys.put(tagType,attributes);
                    }
                }
            }
        }
        System.out.println(mapKeys);
        return mapKeys;
    }

    public HashMap<String,Integer> mapPositionTotagtype(){
        int position=0;
        int count =0;
        String positionOfTag="";
        ArrayList<String> tags= new ArrayList<String>();
        ArrayList<Integer> pos= new ArrayList<Integer>();
        HashMap<String,Integer> mapPos = new HashMap<String, Integer>();
        DirectoryResource dr = new DirectoryResource();
        //FileResource fr = new FileResource();
        for (File f : dr.selectedFiles()) {
            String fileName = f.getName();
            FileResource fr = new FileResource(f);
            //System.out.print(xml);
            for (String line : fr.lines()) {
                int idx = line.indexOf("type=", 0);
                int idx2 = line.indexOf(" ", idx);
                int idxKey = line.indexOf("key");
                int idxQuote = line.indexOf("\"", idxKey + 5);
                if (idx != -1 && idx2 != -1) {
                    String tagType = line.substring(idx + 5, idx2);
                    String genderKey = "";
                    if ( idxQuote!=-1) {
                        genderKey = line.substring(idxKey + 4, idxQuote + 1);
                        //System.out.println(genderKey);
                        if(genderKey.contains("name2")){
                            tagType="dst2";
                        }
                    }
                    //System.out.println(tagType);
                    if (!tags.contains(tagType)) {
                        tags.add(tagType);
                    }
                    if (!mapPos.keySet().contains(tagType)) {
                        mapPos.put(tagType,tags.size());
                        positionOfTag = mapPos.toString();
                        //System.out.println(positionOfTag);
                    }

                }
            }
        }

        //System.out.println(tags);
        //System.out.println(positionOfTag);
        //System.out.println(mapPos.size());
        System.out.println(mapPos);
        return mapPos;
    }

    public static void main(String[] args){
        NTCompilator ntc = new NTCompilator();
        //String result= ntc.NTOpener().toString();
        //System.out.println(result);
        //ntc.mapTagtypeToKeys();
        ntc.mapPositionTotagtype();
       //ntc.mapOcurrencesToTagType();
    }

}
