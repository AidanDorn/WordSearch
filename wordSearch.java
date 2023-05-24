//Aidan Dorn
//CS 145
//May 20, 2023
import java.util.*;
public class wordSearch{
    private char [][] grid, solution;
    private List<String> userWords;
    private int sizeX=10, sizeY=10;
    //main
    public static void main (String [] args){
        wordSearch w=new wordSearch();
        w.wordSearch();
    }
    //Main menu and grid generation. This is where the user decides the grid size too.
    public void wordSearch(){
        System.out.println("""
            \nPlease choose a size for the word search grid (it will be the same length on both sides)\n
            This number must be at least 10! The higher the number the more difficult it will be.
                """);
        Scanner scan=new Scanner(System.in);
        int size=scan.nextInt();
        if(size>=10){
            sizeX=size;
            sizeY=size;
        }
        else{
            System.out.println("Invalid, defaulting size to 10 by 10.");
        }
        this.solution=new char[sizeX][sizeY];
        for(int i=0; i < sizeX; i++){
            for(int j=0; j < sizeY; j++){
                solution[i][j]='|';
            }
        } 
        this.grid=new char[sizeX][sizeY];
        for(int i=0; i < sizeX; i++){
            for(int j=0; j < sizeY; j++){
                grid[i][j]='|';
            }
        } 
        userWords=new ArrayList<String>();
        printIntro();
        scan.close();
    }
    //Methods listed in Specification Document.pdf
    public void printIntro(){
        String command;
        do{
            Scanner userInput=new Scanner(System.in);
            System.out.println("""
                \nWelcome to the word search generator!\nEnter a command:
                'g' generate a new word search
                'p' print the word search
                's' show the solution
                't' terminate the program""");
            command=userInput.next().toLowerCase();
            switch(command){
                case("p")->print();
                case("s")->showSolution();
                case("g")->generate(userInput);
                case("t")->System.out.println("Program terminated");
            }
        }while(!command.equals("t"));
    }
    /*
     * This method uses random to select an orientation for the word (0=Horizontal,1=Vertical,2=Diagonal)
     * After the orientation is randomly selected, a for-loop checks if it will fit in its current orientation
     * if it does not, it will not be included.
     * The maxLimit variable is here to set a limit on how many times the word will be tried to be fit into the puzzle
     * the reason this number is a multiple of the grid width is becasue my program allows for custom sizing (also I only use one dimension because all grids are squares).
     * This method also fills the word-less spaces with | as a temporary filler that shows during the showSolution, when printed noramlly it displays Uppercase letters.
     */
    public void generate(Scanner userInput){
        Random random=new Random();
        wordInput(userInput);
        try{
        for(String x : userWords){
            int maxLimit=0, counter=0;
            while(maxLimit < (sizeX*500)&&counter != x.length()){ 
                maxLimit ++;
                int numberY=random.nextInt(sizeY), numberX=random.nextInt(sizeX);
                int direction=random.nextInt(3);
                counter=0;
                if(direction==0){
                    for(int i=0; i < x.length()&&numberX+i < sizeX; i++){
                        if(grid[numberY][numberX+i]=='|')
                            counter++; 
                    }
                    if(counter==x.length()){
                        for(int i=0; i < x.length(); i++)
                            grid[numberY][numberX+i]=x.charAt(i);
                    }
                }else if(direction==1){
                    for(int i=0; i < x.length()&&numberY+i < sizeY; i++){
                        if(grid[numberY+i][numberX]=='|')
                            counter++;
                    }
                    if(counter==x.length()){
                        for(int i=0; i < x.length(); i++)
                            grid[numberY+i][numberX]=x.charAt(i);
                    }                    
                }else if(direction==2){
                    for(int i=0; i < x.length()&&(numberY+i < sizeY&&numberX+i <sizeX); i++){
                        if(grid[numberY+i][numberX+i]=='|')
                            counter++;
                    }
                    if(counter==x.length()){
                        for(int i=0; i < x.length(); i++)
                            grid[numberY+i][numberX+i]=x.charAt(i);
                    }                   
                }
            }
        }
        }catch(Exception e){
            System.out.println(this.userWords+"\n doesn't fit! It will be excluded\n"); //I couldn't get this exception to print.
        }
        for (int i=0; i < grid.length; ++i) {
            solution[i]=new char[solution[i].length];
            for (int j=0; j < grid[i].length; ++j) {
               solution[i][j]=grid[i][j];
            }
        }
        Random letters=new Random();
        for(int i=0; i < sizeY; i++){
            for(int j=0; j < sizeX; j++){
                if(grid[i][j]=='|'){
                char c=(char)(letters.nextInt(26)+'A');
                grid[i][j]= c;
                }
            }
        }         
    }
    //This is a simple print method that uses a nested for-loop.
    public void print(){
        for(int i=0; i < sizeY; i++){
            for(int j=0; j < sizeX; j++)
                System.out.print(grid[i][j]+" ");
            System.out.println();
        }
        System.out.println("Words to find: "+userWords);
    }
    public void showSolution(){
        for(int i=0; i < sizeY; i++){
            for(int j=0; j < sizeX; j++)
                System.out.print(solution[i][j]+" ");
            System.out.println();
        }
        System.out.println("Words to find: "+userWords);
    }
    //Additional method for user input
    private void wordInput(Scanner userInput){
        userWords.clear();
        for(int i=0; i < sizeY; i++){
            for(int j=0; j < sizeX; j++){
                grid[i][j]='|';
            }
        } 
        System.out.println("How many words?");
        int wordCount=userInput.nextInt();
        for(int i=0; i < wordCount; i++){
            System.out.println("userInput: ("+(wordCount-i)+" words left to add)");
            String word=userInput.next().toUpperCase();
            userWords.add(word);
        }      
    }
}