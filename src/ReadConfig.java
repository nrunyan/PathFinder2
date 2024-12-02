import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadConfig {
    public static char[][] parseFile(String fileName){
        char[][] chars = new char[0][0];
        try{
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            int count = 0;
            int currRow = 0;
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                if(count == 0){
                    count++;
                    int row = Integer.parseInt(String.valueOf(line.charAt(0)));
                    int col = Integer.parseInt(String.valueOf(line.charAt(1)));
                    chars = new char[row][col];
                }else {
                    int lineLen = line.length();
                    for(int i = 0; i < lineLen; i++){
                        chars[currRow][i] = line.charAt(i);
                    }
                    currRow++;
                }
            }
        }catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        return chars;
    }

//    public static void main(String[] args) {
//        char[][] twoD = parseFile("testCases/case-a.txt");
//        for (int i = 0; i < 5; i++){
//            for (int j = 0; j < 7 ; j++){
//                System.out.print(twoD[i][j]);
//            }
//            System.out.println("");
//        }
//    }
}