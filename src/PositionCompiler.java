import edu.duke.DirectoryResource;
import edu.duke.FileResource;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**Note to Chris: I split what I wanted to do in methods that only do one thing.
 * In that way I don't have to do for loops inside another for loop, which made it impossible to create an arrayList for each tagtype, but considering all files (not for each file).
 * it worked well and I can get an array list with the positions of a tagtype in each file.
 * However, it only works when i specify the tagtype in tester. i could not find a way
 * to iterate through the tagtype list, and get the positions for all tags at once***/

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
            if (idx != -1 && idx2 != -1 ) {
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

        //return array list of tag types
        //System.out.println("Number of TagTypes: "+tagTypeList.size());
        //System.out.println("tagTypes: "+ tagTypeList);
        return tagTypeList;
    }

    public HashMap<String,Integer> mapPosition(ArrayList<String> tagTypeList){
        int position=0;
        int count =0;
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
        //System.out.println(mapPos);
        //return hash map mapping tagtype to position in a file-- to be used in next method as a way to connect a tag's position with its name
        return mapPos;
    }

    public int makePosition(HashMap<String,Integer> mapPos, String tagType){
        int positionInList = 0;
        for (String tags : mapPos.keySet()) {
            if (tags.contains(tagType)) {
                positionInList = mapPos.get(tagType);
            }
        }
        //return int position of a tag type
      // System.out.println(positionArray2);
        return positionInList;
    }


    public ArrayList<Integer> makePositionArray(String tagType, HashMap<String,Integer> mapPos) {
            makePosition(mapPos, tagType);

                positionArray.add(makePosition(mapPos, tagType));
                System.out.println(tagType + positionArray);
            //return a array with all positions for a tag generated using makePosition()
            return positionArray;
    }

    public void makeArrayForAllTags(){ /**FAILED*/

      /**  for (String tagType : tagTypeList){
            compiler(tagType,mapPos);
        }*/
    }

    public void tester() {   /**puts all methods together to be run for each file**/
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            ArrayList<String> tagTypeList = new ArrayList<>();
            tagTypeList = makeList(fr);
            System.out.println(tagTypeList);
            HashMap<String, Integer> mapPos = mapPosition(tagTypeList);
            makePositionArray("\"DIGITS\"",mapPos);

        }
    }

    public static void main(String[] args){
        PositionCompiler pc = new PositionCompiler();
        pc.tester();
    }
}
