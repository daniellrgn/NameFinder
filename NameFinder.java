import java.util.*;
import java.io.*;

public class NameFinder{
  public static void main(String[] args) {
    if(args.length != 1) {
      System.err.println("USAGE: java NameFinder <name_file>");
      System.exit(1);
    }

    Scanner console = new Scanner(System.in);

    String nameFile = args[0];
    Structures Structure = new Structures();
    System.out.println("Loading...");
    ArrayList<String> allNames = Structure.loadLists(nameFile);
    Structure.loadHash(nameFile);
    String struct = "";
    String name = "";

    for(;;){
      System.out.print("Enter preferred data structure (1=Tree, 2=HashMap, 3=LinkedList): ");
      int type = console.nextInt();
      if (type < 1 || type > 3){
        System.out.println("Invalid input");
        System.exit(1);
      }

      System.out.print("Enter method (1=SearchName, 2=MostPopularNames, 3=UniqueNames, 4=DisplayNames): ");
      int method = console.nextInt();
      if (method < 1 || method > 4){
        System.out.println("Invalid input");
        System.exit(2);
      }
      if (method == 1){
        System.out.print("Enter name to search for: ");
        name = console.next();
        name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase(); //corrects input formatting
        if (!allNames.contains(name)){
          System.out.println("Could not find name data for " + name);
          System.exit(3);
        }
      }
      if (type == 1){
        System.out.println("Selected Data Structure: Tree");
        if (method == 1){
          Structure.searchNameTree(name);
        }
        else if (method == 2){
          Structure.mostPopularNameTree();
        }
        else if (method == 3){
          Structure.uniqueNameTree();
        }
        else{
          Structure.displayNameTree();
        }
        
      }else if (type == 2){
        System.out.println("Selected Data Structure: HashMap");
        if (method == 1){
          Structure.searchNameHash(name);
        }
        else if (method == 2){
          Structure.mostPopularNameHash();
        }
        else if (method == 3){
          Structure.uniqueNameHash();
        }
        else{
          Structure.displayNameHash();
        }
        
      } else{
        System.out.println("Selected Data Structure: Linked List");
        if (method == 1){
          Structure.searchNameList(name);
        }
        else if (method == 2){
          Structure.mostPopularNameList();
        }
        else if (method == 3){
          Structure.uniqueNameList();
        }
        else{
          Structure.displayNameList();
        }
      }
    }
  }
}
