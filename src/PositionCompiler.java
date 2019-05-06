import edu.duke.DirectoryResource;
import edu.duke.FileResource;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PositionCompiler {
//    ArrayList<String> tagTypeList = new ArrayList<>();
  ArrayList<Integer> positionArray = new ArrayList<Integer>();


    public ArrayList<String> makeList (FileResource fr){
        String tags = fr.asString();
        String tagType="";
        ArrayList<String> tagTypeList = new ArrayList<>();
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
                }
                if (!tagTypeList.contains(tagType) && tagType != null) {
                    tagTypeList.add(tagType);
                }
            }

        //return array list of tags
        //System.out.println("Number of TagTypes: "+tagTypeList.size());
        //System.out.println("tagTypes: "+ tagTypeList);
        return tagTypeList;
    }

    public HashMap<String,Integer> mapPosition(ArrayList<String> tagTypeList){
        int position=0;
        int count =0;
        int endLine=0;
        String positionOfTag="";
        HashMap<String,Integer> mapPos = new HashMap<String,Integer>();

        for (String tagNames : tagTypeList) {

            for (int k =0;k<tagTypeList.size();k++){
                position = tagTypeList.indexOf(tagNames);
                positionOfTag=tagTypeList.get(k);
                //System.out.println(positionOfTag + position);
                if (!mapPos.keySet().contains(tagNames)){
                    mapPos.put(tagNames,tagTypeList.indexOf(tagNames));
                }
            }

        }
        //System.out.println(tags);
        //System.out.println(positionOfTag);
        //System.out.println(mapPos);
        return mapPos;
        //return hash map mapping tagtype to postion in a file
    }

    public int makePosition(HashMap<String,Integer> mapPos, String tagType){
        int positionInList = 0;
        for (String tags : mapPos.keySet()) {
            if (tags.contains(tagType)) {
                positionInList = mapPos.get(tagType);
            }
        }
        //return array list with all positions for a tag typ
      // System.out.println(positionArray2);
        return positionInList;
    }


    public ArrayList<Integer> compiler(String tagType, HashMap<String,Integer> mapPos) {

          //  HashMap<String, Integer> mapPos = mapPosition(tagTypeList);
            //System.out.println(mapPos);
            //tagType= "\"RAILWAY_ERA\"";

            makePosition(mapPos, tagType);

                positionArray.add(makePosition(mapPos, tagType));
                System.out.println(tagType + positionArray);


            return positionArray;
    }

    public void makeArrayForAllTags(){
    /**    ArrayList<String> list = new ArrayList<String>();
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()){
            FileResource fr = new FileResource(f);
            list = makeList(fr);}

     System.out.println("tag list compiled");
     System.out.println(list);
     System.out.println(list.size()); */
      //  for (String tagType : tagTypeList){
            //compiler(tagType);
        //}
    }
    public void tester() {
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            ArrayList<String> tagTypeList = new ArrayList<>();
            tagTypeList = makeList(fr);
           System.out.println(tagTypeList);
            HashMap<String, Integer> mapPos = mapPosition(tagTypeList);
        compiler("\"DIGITS\"",mapPos);


        }
    }

    public static void main(String[] args){
        PositionCompiler pc = new PositionCompiler();

        pc.tester();
    }
}
