import edu.duke.DirectoryResource;
import edu.duke.FileResource;
import org.omg.PortableInterceptor.INACTIVE;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class OONTCompilator {
    ArrayList<TagType> TagTypesWithAttributes = new ArrayList<TagType>();
    ArrayList<TagType> TagTypesWithPosition = new ArrayList<TagType>();
    ArrayList<TagType> TagTypesCollection = new ArrayList<TagType>();

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


    public ArrayList<TagType> mapTagtypeToKeys(){

        //ArrayList<TagType> TagTypesWithAttributes = new ArrayList<TagType>();


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
                    TagType tag = new TagType(tagType,attributes);
                    //System.out.println(tag.getTagName());

                    if (!TagTypesWithAttributes.contains(tag)) {
                        TagTypesWithAttributes.add(tag);
                    }
                }
            }
        }
        System.out.println(TagTypesWithAttributes);
        return TagTypesWithAttributes;
    }

    public ArrayList<TagType> mapPositionTotagtype(){
        int position=0;
        int count =0;
        String positionOfTag="";
        ArrayList<Integer> pos= new ArrayList<Integer>();
        HashMap<String,Integer> mapPos = new HashMap<String, Integer>();
        DirectoryResource dr = new DirectoryResource();
        //FileResource fr = new FileResource();
        for (File f : dr.selectedFiles()) {
            ArrayList<String> tags= new ArrayList<String>();
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
                        TagType tagAndPos = new TagType(tagType,tags.size());
                        //System.out.println(tagAndPos);
                        TagTypesWithPosition.add(tagAndPos);
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
        //System.out.println(mapPos);
        return TagTypesWithPosition;
    }

    public HashMap<String, Integer> tagMap(){
        int counter = 0;
        ArrayList<TagType> position = new ArrayList<>();
        ArrayList<TagType> keyValue = new ArrayList<>();
        ArrayList<TagType> completeTagType = new ArrayList<>();
        position=mapPositionTotagtype();
        keyValue=mapTagtypeToKeys();
        HashMap<String, Integer> map = new HashMap<>();
        System.out.println(position);
        for (int k = 0; k<position.size();k++){
            //System.out.println(position.get(k).getTagName());
             if (!map.keySet().contains(position.get(k).getTagName())){
                 map.put(position.get(k).getTagName(),position.get(k).getPosition());
                 counter = 1;
             }
             else {
                 counter=counter+1;
                 map.put(position.get(k).getTagName(),(map.get(position.get(k).getTagName())+position.get(k).getPosition())/counter);
             }

         }


        //return name of tag and array list of all it's positions
        System.out.println("complete array of objects "+completeTagType);
        System.out.println("map "+map);
        System.out.println("map size "+map.size());
        return map;
    }

    public static void main(String[] args){
OONTCompilator oont = new OONTCompilator();
    //String result= ntc.NTOpener().toString();
        //System.out.println(result);
        //oont.mapTagtypeToKeys();
        oont.tagMap();
        //ntc.mapOcurrencesToTagType();
    }


}
