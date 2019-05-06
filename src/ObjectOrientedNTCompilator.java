
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

public class ObjectOrientedNTCompilator {

    private ArrayList<String> tagTypeList = new ArrayList<>();
    private String tagType="";


    public ArrayList<String> storeTagType(){
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
                        if(genderKey.contains(".name2")){
                            tagType="dst2";
                        }
                    }
                    if (!tagTypeList.contains(tagType) && tagType != null) {
                        tagTypeList.add(tagType);
                        mapQuan.put(tagType,1);
                    } else {
                        mapQuan.put(tagType, mapQuan.get(tagType) + 1);
                    }

                }
            }
        }
        System.out.println("Number of TagTypes: "+tagTypeList.size());
        System.out.println("tagTypes: "+ tagTypeList);
        return tagTypeList;
    }

    public HashMap<String,Integer> mapOcurrencesToTagType2(){
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
                        if(genderKey.contains(".name2")){
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
                int idxQuote = line.indexOf("\"", idxKey + 5);
                int idxEnd = line.indexOf("/>");
                if (idx != -1 && idx2 != -1 && idxKey!=-1 && idxQuote!=-1 && idxEnd!=-1) {
                    String tagType = line.substring(idx + 5, idx2);
                    String attributes = line.substring(idxQuote+1,idxEnd);
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

    public HashMap<String,Integer> mapPositionTotagtype(ArrayList<String> tagTypeList){
        int position=0;
        int count =0;
        int endLine=0;
        String positionOfTag="";
        ArrayList<String> tags= storeTagType();
        HashMap<String,Integer> mapPos = new HashMap<String,Integer>();

            for (String tagNames : tags) {

            for (int k =0;k<tags.size();k++){
            position = tags.indexOf(tagNames);
            positionOfTag=tags.get(k);
            //System.out.println(positionOfTag + position);
                if (!mapPos.keySet().contains(tagNames)){
                mapPos.put(tagNames,tags.indexOf(tagNames));
            }
            }

                }
                //System.out.println(tags);
        //System.out.println(positionOfTag);
        System.out.println(mapPos);
        return mapPos;
    }

    public void compilePosition(){


    }

    public static void main(String[] args){
        ObjectOrientedNTCompilator ontc = new ObjectOrientedNTCompilator();
        //String result= ntc.NTOpener().toString();
        //System.out.println(result);
        //ontc.mapPositionTotagtype();
        //ntc.mapTagtypeToKeys();
        //ntc.mapPositionTotagtype();
    }

}
