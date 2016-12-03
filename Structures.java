import java.io.*;
import java.util.*;
import java.util.LinkedList;
import java.util.ListIterator;

public class Structures{
  //============================================================================
  //LinkedList data structure functions

  private LinkedList<Name> maleList;
  private LinkedList<Name> femaleList;
  private Tree alphaTree;
  private Tree maleFreqTree;
  private Tree femaleFreqTree;
  private static int totalMales;
  private static int totalFemales;

  Structures(){
    maleList = new LinkedList<Name>();
    femaleList = new LinkedList<Name>();
    alphaTree = new Tree();
    maleFreqTree = new Tree();
    femaleFreqTree = new Tree();
    totalMales = 0;
    totalFemales = 0;
  }

  public static class Name {
    private String name;
    private String gender;
    private int frequency;

    Name(String n, String g, int f){
      name = n;
      gender = g;
      frequency = f;
    }

    Name(){
      name = "";
      gender = "";
      frequency = 0;
    }

    public String getName(){
      return name;
    }
    public int getFreq(){
      return frequency;
    }
  }

  public LinkedList<Name> getMaleList(){
    return maleList;
  }

  public LinkedList<Name> getFemaleList(){
    return femaleList;
  }

  public ArrayList<String> loadLists(String nameFile){
    ArrayList<String> allNames = new ArrayList<String>();
    Scanner line = null;
    String name = "";
    String gender = "";
    int frequency = 0;


    try{
      File file = new File(nameFile);
      line = new Scanner(file);
      while (line.hasNextLine()){
        String l = line.nextLine();
        Scanner lineData = new Scanner(l);
        lineData.useDelimiter(",");
        name = lineData.next();
        gender = lineData.next();
        frequency = lineData.nextInt();
        allNames.add(name);
        Name n = new Name(name, gender, frequency);
        Tree.TreeNode node = new Tree.TreeNode(n);
        if (gender.equals("M")){
          maleList.add(n);
          maleFreqTree.treeFreqInsert(node);
          totalMales = totalMales + frequency;
        }else{
          femaleList.add(n);
          femaleFreqTree.treeFreqInsert(node);
          totalFemales = totalFemales + frequency;
        }
        alphaTree.treeAlphaInsert(node);

      }
    }catch (FileNotFoundException ex){
      System.out.println("Error, unable to read file" + nameFile);
      System.exit(1);
    }
    return allNames;
  }

  public void searchNameList(String name){
    int femaleRank = 0;
    int fRank = 0;
    int femaleFreq = 0;
    int maleRank = 0;
    int mRank = 0;
    int maleFreq = 0;

    for (Name n : femaleList){
      fRank++;
      if (n.getName().equals(name)){
        femaleFreq = n.getFreq();
        femaleRank = fRank;
        break;
      }
    }

    for (Name n : maleList){
      mRank++;
      if (n.getName().equals(name)){
        maleFreq = n.getFreq();
        maleRank = mRank;
        break;
      }
    }
    System.out.println("Selected Name: " + name);
    System.out.printf("%-15s%-15s%-15s%-15s%-15s%n", "Year", "# Females", "Female-Rank", "# Males", "Male-Rank");
    System.out.printf("%-15d%-15d%-15d%-15d%-15d%n", 2014, femaleFreq, femaleRank, maleFreq, maleRank);
    }

  public void mostPopularNameList(){
    Name f = null;
    Name m = null;
    int fPercent = 0;
    int mPercent = 0;
    System.out.printf("%-15s%-15s%-15s%-15s%-15s%-15s%n", "Female Name", "Frequency", "%", "Male Name", "Frequency", "%");
    for (int i=0; i<10; i++){
      f = femaleList.get(i);
      fPercent = ((f.getFreq()*100)/totalFemales);
      m = maleList.get(i);
      mPercent = ((m.getFreq()*100)/totalMales);
      System.out.printf("%-15s%-15d%-15s%-15s%-15d%-15s%n", f.getName(), f.getFreq(), fPercent+"%", m.getName(), m.getFreq(), mPercent+"%");

    }

  }

  public void uniqueNameList(){
    Name f = null;
    Name m = null;
    int fPercent = 0;
    int mPercent = 0;
    System.out.printf("%-15s%-15s%-15s%-15s%-15s%-15s%n", "Female Name", "Frequency", "%", "Male Name", "Frequency", "%");
    for (int i=1; i<6; i++){
      f = femaleList.get(femaleList.size()-i);
      fPercent = ((f.getFreq()*100)/totalFemales);
      m = maleList.get(maleList.size()-i);
      mPercent = ((m.getFreq()*100)/totalMales);
      System.out.printf("%-15s%-15d%-15s%-15s%-15d%-15s%n", f.getName(), f.getFreq(), fPercent+"%", m.getName(), m.getFreq(), mPercent+"%");
    }

  }

  public void displayNameList(){
    LinkedList<Name> fullList = new LinkedList<Name>();  //adds all names together to be alphabetized
    for (Name n : femaleList){
      fullList.add(n);
    }
    for (Name n : maleList){
      fullList.add(n);
    }
    Name[] A  = fullList.toArray(new Name[fullList.size()]);
    Sort(A, 0, fullList.size()-1);  //alphabetize the names
    LinkedList<Name> displayList = new LinkedList<Name>(Arrays.asList(A));

    int f = 0;
    int m = 0;
    int fPercent = 0;
    int mPercent = 0;
    Name blank = new Name();
    Name curr = null;

    System.out.printf("%-15s%-15s%-15s%-15s%-15s%n", "Name", "F Frequency", "% Females", "M Frequency", "% Males");

    for (Name next : displayList){     //logic to format names. Looks at next and current
      if (curr != null){
        if (curr.getName().equals(next.getName())){
          if (next.gender.equals("M")){
            m = next.getFreq();
            f = curr.getFreq();
          }else{
            f = next.getFreq();
            m = curr.getFreq();
          }
        }else{
          if (curr.gender.equals("M")){
            m = curr.getFreq();
            f= blank.getFreq();
          }else{
            f = curr.getFreq();
            m = blank.getFreq();
          }
        }
        mPercent = ((m*100)/totalMales);
        fPercent = ((f*100)/totalFemales);
        System.out.printf("%-15s%-15d%-15s%-15d%-15s%n", curr.getName(), f, fPercent+"%", m, mPercent+"%");
      }
      curr = next;
    }
  }

  private static void Sort(Name[] A, int p, int r){
    if (p < r){
      int q = PARTITION(A,p,r);
      Sort(A,p,q-1);
      Sort(A,q+1,r);
    }
  }

  private static int PARTITION(Name[] A, int p, int r){
    Name temp = null;
    String x = A[r].getName();
    int i = p-1;
    for (int j=p; j<r-1; j++){
      if (A[j].getName().compareTo(x) <= 0){
        i++;
        temp = A[i];
        A[i] = A[j];
        A[j] = temp;
      }
    }
    temp = A[i+1];
    A[i+1] = A[r];
    A[r] = temp;
    return i + 1;
  }

  //end LinkedList functionality
  //============================================================================
  //tree data structure class/functions

  //start tree class
  public static class Tree{
    private TreeNode root;
    private TreeNode nilNode;
    private static int black = 1;
    private static int red = 0;
    private Name nullName = new Name("","",0);

    Tree(){
      nilNode = new TreeNode(nullName);
      nilNode.p = nilNode;
      nilNode.left = nilNode;
      nilNode.right = nilNode;

      root = nilNode;
      root.p = nilNode;
      root.left = nilNode;
      root.right = nilNode;
    }

    //node class for tree
    public static class TreeNode{
      private Name data;
      private int color;
      private int rank;
      private TreeNode p;
      private TreeNode left;
      private TreeNode right;

      TreeNode(Name name){
        data = name;
        color = black;
        rank = 0;
        p = null;
        left = null;
        right = null;
      }
    }

    public void treeFreqInsert(TreeNode z){
      TreeNode y = nilNode;
      TreeNode x = root;
      System.out.println(!x.equals(nilNode));
      while (!x.equals(nilNode)){
        y = x;
        if (z.data.getFreq() < x.data.getFreq()){
          x = x.left;
        }else if (z.data.getFreq() >= x.data.getFreq()){
          x = x.right;
        }
      }
      z.p = y;
      if (y.equals(nilNode)){
        root = z;
      } else if (z.data.getFreq() < y.data.getFreq()){
        y.left = z;
      } else {
        y.right = z;
      }
      z.left = nilNode;
      z.right = nilNode;
      z.color = red;
      this.insert_Fixup(z);
    }

    public void treeAlphaInsert(TreeNode z){
      TreeNode y = nilNode;
      TreeNode x = root;
      while (!x.equals(nilNode)){
        y = x;
        if (z.data.getName().compareTo(x.data.getName()) < 0){
          x = x.left;
        }else if (z.data.getName().compareTo(x.data.getName()) >= 0){
          x = x.right;
        }
      }
      z.p = y;
      if (y.equals(nilNode)){
        root = z;
      } else if (z.data.getName().compareTo(x.data.getName())< 0){
        y.left = z;
      } else {
        y.right = z;
      }
      z.left = nilNode;
      z.right = nilNode;
      z.color = red;
      this.insert_Fixup(z);
    }

    private void insert_Fixup(TreeNode z){
      TreeNode y = null;
      while (z.p.color == red){
        if (z.p.equals(z.p.p.left)){
          y = z.p.p.right;
          if (y.color == red){
            z.p.color = black;
            y.color = black;
            z.p.p.color = red;
            z = z.p.p;
          } else if (z.equals(z.p.right)){
            z = z.p;
            this.rotate_Left(z);
          }
          z.p.color = black;
          z.p.p.color = red;
          this.rotate_Right(z.p.p);
        } else {
          y = z.p.p.left;
          if (y.color == red){
            z.p.color = black;
            y.color = black;
            z.p.p.color = red;
            z = z.p.p;
          } else if (z.equals(z.p.left)){
            z = z.p;
            this.rotate_Right(z);
          }
          z.p.color = black;
          z.p.p.color = red;
          this.rotate_Left(z.p.p);
        }
      }
    }

    private TreeNode rotate_Right(TreeNode z){
      TreeNode y = z.left;
      z.left = y.right;
      y.right = z;
      return y;
    }

    private TreeNode rotate_Left(TreeNode z){
      TreeNode y = z.right;
      z.right = y.left;
      y.left = z;
      return y;
    }

  }
  //end tree class
  //============================================================================
  //tree functionality

  public void searchNameTree(String name){
    int femaleRank = 0;
    int femaleFreq = 0;
    int maleRank = 0;
    int maleFreq = 0;
    Tree.TreeNode searchNode = null;

    searchNode = alphaTree.root;
    while (!searchNode.equals(alphaTree.nilNode)){
      if (searchNode.data.getName().equals(name)){
        if (searchNode.data.gender.equals("F")){
          femaleRank = searchNode.rank;
          femaleFreq = searchNode.data.getFreq();
          if (searchNode.right.data.getName().equals(name)){
            maleRank = searchNode.right.rank;
            maleFreq = searchNode.right.data.getFreq();
          }
        } else {
          maleRank = searchNode.rank;
          maleFreq = searchNode.data.getFreq();
          if (searchNode.right.data.getName().equals(name)){
            femaleRank = searchNode.right.rank;
            femaleFreq = searchNode.right.data.getFreq();
          }
        }
      } else if (searchNode.data.getName().compareTo(name) < 0){
        searchNode = searchNode.left;
      } else {
        searchNode = searchNode.right;
      }
    }
    System.out.println("Selected Name: " + name);
    System.out.printf("%-15s%-15s%-15s%-15s%-15s%n", "Year", "# Females", "Female-Rank", "# Males", "Male-Rank");
    System.out.printf("%-15d%-15d%-15d%-15d%-15d%n", 2014, femaleFreq, femaleRank, maleFreq, maleRank);
  }

  public void mostPopularNameTree(){
    ArrayList<Name> femalePops = new ArrayList<Name>();
    ArrayList<Name> malePops = new ArrayList<Name>();
    mostPopularInOrder(femaleFreqTree, femaleFreqTree.root, femalePops);
    mostPopularInOrder(maleFreqTree, maleFreqTree.root, malePops);


    System.out.printf("%-15s%-15s%-15s%-15s%-15s%-15s%n", "Female Name", "Frequency", "%", "Male Name", "Frequency", "%");

    int fPercent = 0;
    int mPercent = 0;

    for (int i = 0; i < 10; i++){
      Name f = femalePops.get(i);
      fPercent = (f.getFreq() * 100) / totalFemales;
      Name m = malePops.get(i);
      mPercent = (m.getFreq() * 100) / totalMales;
      System.out.printf("%-15s%-15d%-15s%-15s%-15d%-15s%n", f.getName(), f.getFreq(), fPercent+"%", m.getName(), m.getFreq(), mPercent+"%");
    }
  }

  private static void mostPopularInOrder(Tree t, Tree.TreeNode z, ArrayList<Name> nodeList){
    if (!z.equals(t.nilNode) && nodeList.size() <= 10){
      mostPopularInOrder(t, z.right, nodeList);
      nodeList.add(z.data);
      mostPopularInOrder(t, z.left, nodeList);
    }
  }

  public void uniqueNameTree(){
    ArrayList<Name> femaleUnis = new ArrayList<Name>();
    ArrayList<Name> maleUnis = new ArrayList<Name>();
    uniqueNameInOrder(femaleFreqTree, femaleFreqTree.root, femaleUnis);
    uniqueNameInOrder(maleFreqTree, maleFreqTree.root, maleUnis);


    System.out.printf("%-15s%-15s%-15s%-15s%-15s%-15s%n", "Female Name", "Frequency", "%", "Male Name", "Frequency", "%");

    int fPercent = 0;
    int mPercent = 0;

    for (int i = 0; i < 5; i++){
      Name f = femaleUnis.get(i);
      fPercent = (f.getFreq() * 100) / totalFemales;
      Name m = maleUnis.get(i);
      mPercent = (m.getFreq() * 100) / totalMales;
      System.out.printf("%-15s%-15d%-15s%-15s%-15d%-15s%n", f.getName(), f.getFreq(), fPercent+"%", m.getName(), m.getFreq(), mPercent+"%");
    }
  }

  private static void uniqueNameInOrder(Tree t, Tree.TreeNode z, ArrayList<Name> nodeList){
    if (!z.equals(t.nilNode) && nodeList.size() <= 5){
      uniqueNameInOrder(t, z.left, nodeList);
      nodeList.add(z.data);
      uniqueNameInOrder(t, z.right, nodeList);
    }
  }

  public void displayNameTree(){
    System.out.printf("%-15s%-15s%-15s%-15s%-15s%n", "Name", "F Frequency", "% Females", "M Frequency", "% Males");
    alphaInOrder(alphaTree, alphaTree.root);
  }

  //helper function for displayNameTree
  private static void alphaInOrder(Tree t, Tree.TreeNode z){
    if (!z.equals(t.nilNode)){
      String name = "";
      int fFreq = 0;
      int fPercent = 0;
      int mFreq = 0;
      int mPercent = 0;

      alphaInOrder(t, z.left);

      if (!z.p.data.getName().equals(z.data.getName())){
        name = z.data.getName();
        if (z.data.gender.equals("F")){
          fFreq = z.data.getFreq();
          fPercent = (fFreq * 100)/totalFemales;
          if (z.right.data.getName().equals(z.data.getName())){
            mFreq = z.right.data.getFreq();
            mPercent = (mFreq * 100)/totalMales;
          }
        } else {
          mFreq = z.data.getFreq();
          mPercent = (mFreq * 100)/totalMales;
          if (z.right.data.getName().equals(z.data.getName())){
            fFreq = z.right.data.getFreq();
            fPercent = (fFreq * 100)/totalFemales;
          }
          System.out.printf("%-15s%-15d%-15s%-15d%-15s%n", name, fFreq, fPercent+"%", mFreq, mPercent+"%");
        }
      }
      alphaInOrder(t, z.right);
    }

  }
}//end Structures
