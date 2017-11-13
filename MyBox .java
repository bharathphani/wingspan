import com.box.sdk.BoxAPIConnection;
import com.box.sdk.BoxFile;
import com.box.sdk.BoxFolder;
import com.box.sdk.BoxItem;

import java.util.ArrayList;
import java.util.Arrays;


class Connection{
    private static  BoxAPIConnection boxCon;
    private Connection(){}
    public static BoxAPIConnection getConnection(String dev_token){
        if(boxCon==null)
            boxCon=new BoxAPIConnection(dev_token);
        return boxCon;
    }
        }

 class MyBoxOpertaions
 {
     private BoxAPIConnection api;
     BoxFolder rootFolder;
     MyBoxOpertaions(BoxAPIConnection api){
         this.api=api;
         rootFolder=BoxFolder.getRootFolder(api);
     }
     void getFolderDetails(String id,String path){
         BoxFolder folder = new BoxFolder(api,id);
         for (BoxItem.Info itemInfo : folder) {
             if (itemInfo instanceof BoxFile.Info) {
                 BoxFile.Info fileInfo = (BoxFile.Info) itemInfo;
                System.out.println(path+"  "+fileInfo.getName());
             } else if (itemInfo instanceof BoxFolder.Info) {
                 BoxFolder.Info folderInfo = (BoxFolder.Info) itemInfo;
                 getFolderDetails(folderInfo.getID(),path+"/"+folderInfo.getName());
             }
         }


     }

     String createFolderPath(String folderid, ArrayList<String> pathList)
     {
         if(pathList.size()>0)
         {
             BoxFolder folder = new BoxFolder(api,folderid);
             boolean folderFound=false;
             for (BoxItem.Info itemInfo : folder) {
                 if (itemInfo instanceof BoxFolder.Info && itemInfo.getName().equals(pathList.get(0)) ) {
                     {
                         folderFound=true;
                         pathList.remove(0);
                         return(createFolderPath(itemInfo.getID(),pathList));

                     }

                 }
             }
             if(!folderFound)
             {
                 BoxFolder.Info childFolderInfo = folder.createFolder(pathList.get(0));
                 pathList.remove(0);
                 return(createFolderPath(childFolderInfo.getID(),pathList));
             }
         }
         else
             return folderid;
         return folderid;
     }



 }

public class MyBox {
    public static void main(String args[])
    {
        String dev_token="j71XeTPj2xmzxeMsWbc3kO8YnuINAtYq";
        String path[]=new String("kumar/phani/bharath").split("/");
        BoxAPIConnection api=Connection.getConnection(dev_token);
        BoxFolder rootFolder = BoxFolder.getRootFolder(api);
        MyBoxOpertaions boxOp=new MyBoxOpertaions(api);
        boxOp.getFolderDetails(rootFolder.getID(),"root");
        ArrayList<String> folderList=new ArrayList<String>(Arrays.asList(path));
        //String createdfolderid= boxOp.createFolderPath(rootFolder.getID(),folderList);
        //System.out.println("Folder Path created "+createdfolderid);

    }

}