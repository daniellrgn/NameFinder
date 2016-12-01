import java.io.*;
import java.util.*;
import java.util.LinkedList;
import java.util.ListIterator;

public class Structures{
  private LinkedList<Name> maleList;
  private LinkedList<Name> femaleList;
  private int totalMales;
  private int totalFemales;

  public Structures(){
    maleList = new LinkedList<Name>();
    femaleList = new LinkedList<Name>();
    totalMales = 0;
    totalFemales = 0;
  }

  public class Name {
    private String name;
    private String gender;
    private int frequency;

    public Name(String n, String g, int f){
      name = n;
      gender = g;
      frequency = f;
    }

    public Name(){
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
        if (gender.equals("M")){
          maleList.add(n);
          totalMales = totalMales + frequency;
        }else{
          femaleList.add(n);
          totalFemales = totalFemales + frequency;
        }
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
}
