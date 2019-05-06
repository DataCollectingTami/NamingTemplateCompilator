
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
                if (idx != -1 && idx2 != -1 /**&& idxKey!=-1 && idxQuote!=-1*/) {
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
                if (idx != -1 && idx2 != -1 /**&& idxKey!=-1 && idxQuote!=-1*/) {
                    String tagType = line.substring(idx + 5, idx2);
                    String genderKey = line.substring(idxKey + 4, idxQuote + 1);
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
 /**   public void mergeTemplate(){
        HashMap<String,Integer> map1=NTOpener();
        HashMap<String,Integer> map2=NTOpener();
        ArrayList<Integer> positions = new ArrayList<Integer>();
        HashMap<String,ArrayList<Integer>> mergeMap= new HashMap<String, ArrayList<Integer>>();

        for (Map.Entry<String,Integer> e1 : map1.entrySet()){
            String tagType1 = e1.getKey();
            //System.out.println(tagType1);
            Integer pos1 = e1.getValue();
            //System.out.println(pos1);
            Integer pos2 = map2.get(tagType1);
            System.out.println(map2.get(pos2));
            System.out.println(pos2);


            if (tagType1==map2.keySet().toString()){
                positions.add(pos1);
                positions.add(pos2);
                mergeMap.put(tagType1,positions);
            }

        }



System.out.println(mergeMap);


    }

  */

    public static void main(String[] args){
        NTCompilator ntc = new NTCompilator();
        //String result= ntc.NTOpener().toString();
        //System.out.println(result);
        ntc.mapTagtypeToKeys();
        //ntc.mapPositionTotagtype();
       //ntc.mapOcurrencesToTagType();
    }

}
