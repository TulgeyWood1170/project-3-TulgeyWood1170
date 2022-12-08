import java.util.*;
import java.io.*;

public class GradebookApp {

    private static Student[] students;
    public static final int NUM_COLUMNS = Student.getDataColumnLength();

    public static int[][] getAssignmentGrades() {
        int[][] grades = new int[students.length][NUM_COLUMNS];

        for (int i = 0; i < students.length; i++) {

            for (int j = 0; j < grades[0].length; j++) { 
                grades[i][j] = students[i].getAssignmentScore(j+1);
                System.out.printf("%03d  ", grades[i][j]);
            }
            System.out.printf("%n");
        }
        return grades;
    }

    public static String[][] getStudentData() {
        String[][] allData = new String[students.length][NUM_COLUMNS];
        String record;
        String studentRecord[];
        for (int row = 0; row < students.length; row++) {
            record = students[row].exportStudentData();
            studentRecord = record.split(",");
            for (int col = 0; col < NUM_COLUMNS; col++) {
                allData[row][col] = studentRecord[col];
            }
        }
        return allData;
    }    

    public static String[][] sort(String[][] myArray) {
        String[][] swapArray = new String[students.length][NUM_COLUMNS];
        double avgGrade[] = new double[students.length];
        Student tempStudent = new Student();
        /*
         *  for (int i = 0; i < numberOfStudents; i++) {
                students[i] = new Student();
            }
            //fill students data
            for (int i = 0; i < numberOfStudents; i++) {
                record = inf.nextLine();
                students[i].importStudentData(record);
            }
                        
         */
        
        for (int i = 0; i < students.length; i++) {
            avgGrade[i] = students[i].getGradeAverage();
        }
        //System.out.println("avgGrade.length = " + avgGrade.length);

        //find max student avgGrade and row j, row i indexes to swap
        for (int i = 0; i < students.length; i++) {
            double currentMax = avgGrade[i];
            int currentMaxIndex = i;

            //swap rows in avgGrade
            for (int j = i + 1; j < avgGrade.length; j++) {
                if (currentMax < avgGrade[j]) {
                    currentMax = avgGrade[j];
                    currentMaxIndex = j;
                    //System.out.println("current index = " + j + "current max row is " + currentMaxIndex);
                }
            }

            //swap i and j rows in avgGrade and in myArray
            if (currentMaxIndex != i) {
                avgGrade[currentMaxIndex] = avgGrade[i];
                avgGrade[i] = currentMax;
              
                Student tempStudents = students[i];
                students[i] = students[currentMaxIndex];
                students[currentMaxIndex] = tempStudents;
                
                for (int k = 0; k < NUM_COLUMNS; k++) {
                    swapArray[i][k] = myArray[currentMaxIndex][k];
                    //System.out.println(i + ", " + k);
                }
                for (int k = 0; k < NUM_COLUMNS; k++) {
                    swapArray[students.length-1][k] = myArray[students.length-1][k];    
                }
                
            }
        }
        return swapArray;
    }        

    public static void printArray(String[][] myArray) {
        // print first line of header
        System.out.printf("     ");
        for (int col = 0; col < 2; col++) {
            System.out.printf("%10s ", " ");   
        }
        for (int col = 0; col < Student.TOTAL_EXAMS/2; col++) {
            System.out.printf("%3s   ", " ");
        }
        System.out.printf("%6s", "EXAMS");
        for (int col = Student.TOTAL_EXAMS/2 + 1; col < Student.TOTAL_EXAMS; col++) {
            System.out.printf("%3s   ", " ");
        }
        for (int col = 0; col < Student.TOTAL_ASSIGNMENTS/2; col++) {
            System.out.printf("%3s   ", " ");
        }
        System.out.printf("%11s","ASSIGNMENTS");
        for (int col = Student.TOTAL_ASSIGNMENTS/2 + 2; col < Student.TOTAL_ASSIGNMENTS; col++) {
            System.out.printf("%3s   ", " ");
        }
        System.out.printf("%7s%n", "GRADE");

        //print second line of header
        System.out.printf("     ");
        for (int col = 0; col < 2; col++) {
            System.out.printf("%10s ", " ");        
        }
        for (int col = 0; col < Student.TOTAL_EXAMS; col++) {
            System.out.printf("%3d   ", col+1);
        }
        for (int col = 0; col < Student.TOTAL_ASSIGNMENTS; col++) {
            System.out.printf("%3d   ", col+1);
        }
        System.out.printf("%7s%n", "AVERAGE");

        // print array 
        for (int row = 0; row < students.length; row++) {
            //System.out.printf("%d%n", row);
            System.out.printf("     ");

            for (int col = 0; col < 2; col++) {
                System.out.printf("%-10s ", myArray[row][col]);    
            }
            for (int col = 2; col < NUM_COLUMNS; col++) {
                System.out.printf("%3d   ", Integer.parseInt(myArray[row][col]));
            }
            //print average grade for student row
            System.out.printf("  %5.1f%n", students[row].getGradeAverage());

        }
    }

    public static void main(String[] args) {
        int studentID = 0;
        String examNumber = "";
        int assignmentNumber = 0;
        int score = 0;
        boolean examValid;
        boolean assignmentValid;
        int numberOfStudents = 0;
        String record = "";
        Scanner in = new Scanner(System.in);
        String fileName = "";

        System.out.println("Welcome to the CM111 Gradebook!");

        while(true) {
            System.out.println("");
            System.out.println("Please select an option");
            System.out.println(" 1 - Initialize the gradebook from a file");
            System.out.println(" 2 - List the class roster");
            System.out.println(" 3 - Read a student's exam score");
            System.out.println(" 4 - Read a student's assignment score");
            System.out.println(" 5 - Enter a student's exam score");
            System.out.println(" 6 - Enter a student's assignment score");          
            System.out.println(" 7 - View Overall Grades for the class");
            System.out.println(" 8 - View Student Chart with Final Grade");
            System.out.println(" 9 - Sort Student Chart according to Final Grade");
            System.out.println(" 10 - Save the grade book to a file");
            System.out.println(" 11 - Quit");
            System.out.println("");

            String choice = in.nextLine();
            System.out.println("");

            switch(choice) {

                case "1":
                    //get the filename of the file containing student data
                    //and populate students[] array from it in Students.import...
                    System.out.print("Enter the filename containing student data: ");
                    fileName = in.nextLine();

                    try { 
                        File f = new File(fileName);
                        //if (f.exists() && f.canRead()) no io exception
                        Scanner inf = new Scanner(f);
                        //get the number of students from the first line in the file
                        numberOfStudents = Integer.parseInt(inf.nextLine());
                        //declare students array with length numberOfStudents
                        students = new Student[numberOfStudents];
                        //initialize students array with each index a new object Student
                        for (int i = 0; i < numberOfStudents; i++) {
                            students[i] = new Student();
                        }
                        //fill students data
                        for (int i = 0; i < numberOfStudents; i++) {
                            record = inf.nextLine();
                            students[i].importStudentData(record);
                        }
                        inf.close();

                        System.out.println("");
                        System.out.println("File " + fileName + " loaded.");
                    } catch(NumberFormatException e) {
                        System.out.println("");
                        System.out.println("numberOfStudents needs to be integer.");
                    } catch(IOException e1) {
                        System.out.println("");
                        System.out.println("File doesn't exist.");
                    } catch(NoSuchElementException e2) {
                        System.out.println("");
                        System.out.println("File is missing data.");
                    } catch(IllegalArgumentException e3) {
                        System.out.println("");
                        System.out.println("Bad student data record.");
                    } 
                    break;

                case "2":
                    System.out.println("Class roster:");
                    // print the student ID, first name and last name for each student
                    int i = 0;    
                    try { 
                        while (i < students.length) {
                            System.out.println((i + 1) + ". " + students[i].getFirstName() + " " + 
                                students[i].getLastName());
                            i++;
                        }
                    } catch(NullPointerException e) {
                        System.out.println("Students array not initialized; load input file.");
                    }
                    break;

                case "3":
                    System.out.println("View a student's exam score.");
                    // prompt the user for a student ID and an exam 
                    System.out.print("Enter student id (1-" + numberOfStudents + "): ");
                    examValid = false;
                    try {
                        studentID = Integer.parseInt(in.nextLine());
                        //exams are 1, 2, 3, WrittenFinal, and  PracticalFinal
                        System.out.print("Enter the exam number (1, 2, 3, WrittenFinal or PracticalFinal): ");
                        examNumber = in.nextLine();
                        if (examNumber.equals("1") || examNumber.equals("2") || examNumber.equals("3") ||
                        examNumber.equals("WrittenFinal") || examNumber.equals("PracticalFinal")) {
                            examValid = true;
                        }
                        System.out.println("");
                        if (studentID >= 1 && studentID <= numberOfStudents && examValid) {
                            System.out.print(studentID + ". ");
                        }        

                        //print the score for that student
                        switch(examNumber) {
                            case "1":
                                System.out.println(students[studentID-1].getExam1Score());
                                break;
                            case "2":
                                System.out.println(students[studentID-1].getExam2Score());
                                break;
                            case "3":
                                System.out.println(students[studentID-1].getExam3Score());
                                break;
                            case "WrittenFinal":
                                System.out.println(students[studentID-1].getWrittenFinalScore());
                                break;
                            case "PracticalFinal":
                                System.out.println(students[studentID-1].getPracticalFinalScore());
                                break;
                            default:
                                System.out.println("Not a valid exam selection.");
                        }
                    } catch(NullPointerException e) {
                        System.out.println("Students array not initialized; load input file."); 
                    } catch(NumberFormatException e1) {
                        System.out.println("");
                        System.out.println("Number format exception; studentID must be integer.");
                    } catch(ArrayIndexOutOfBoundsException e2) {
                        System.out.println("Invalid studentID use 1-" + numberOfStudents + ".");
                    }
                    break;

                case "4":
                    System.out.println("View a student's assignment score.");
                    // prompt the user for a student ID and assignment #
                    System.out.print("Enter student id (1-" + numberOfStudents + "): ");
                    assignmentValid = false;
                    try {
                        studentID = Integer.parseInt(in.nextLine());
                        System.out.print("Enter the assignment number (1-" + Student.TOTAL_ASSIGNMENTS + "): ");
                        assignmentNumber = Integer.parseInt(in.nextLine());
                        if (assignmentNumber >= 1 && assignmentNumber <= Student.TOTAL_ASSIGNMENTS) {           
                            assignmentValid = true;
                        }
                        if (studentID >= 1 && studentID <= numberOfStudents && assignmentValid) {
                            System.out.println("");
                            System.out.print(studentID + ". ");
                        }

                        System.out.println(students[studentID-1].getAssignmentScore(assignmentNumber));
                    } catch(NullPointerException e) {
                        System.out.println("");
                        System.out.println("Students array not initialized; load input file."); 
                    } catch(NumberFormatException e1) {
                        System.out.println("");
                        System.out.println("Number format exception: studentID and assignment # must be integer.");
                    } catch(ArrayIndexOutOfBoundsException e2) {
                        System.out.println("");
                        System.out.println("Invalid studentID use 1-" + numberOfStudents + ".");
                    } catch(IllegalArgumentException e3) {
                        System.out.println("");
                        System.out.println("Valid assignment numbers are 1-" + Student.TOTAL_ASSIGNMENTS + ".");
                    }
                    break;

                case "5":
                    System.out.println("Update a student's exam score.");
                    // prompt the user for a student ID, an exam selection, and score
                    System.out.print("Enter student id (1-" + numberOfStudents + "): ");
                    examValid = false;
                    try {
                        studentID = Integer.parseInt(in.nextLine());
                        //exams are 1, 2, 3, WrittenFinal, and  PracticalFinal
                        System.out.print("Enter the exam number (1, 2, 3, WrittenFinal or PracticalFinal): ");
                        examNumber = in.nextLine();
                        System.out.print("Enter exam score (0-100) to save in the gradebook: ");
                        score = Integer.parseInt(in.nextLine());

                        if (examNumber.equals("1") || examNumber.equals("2") || examNumber.equals("3") ||
                        examNumber.equals("WrittenFinal") || examNumber.equals("PracticalFinal")) {
                            examValid = true;
                        }
                        if (studentID >= 1 && studentID <= numberOfStudents && examValid &&
                        score >= 0 && score <= 100) {
                            System.out.println("");
                            System.out.print(studentID + ". ");
                        }        

                        switch(examNumber) {
                            case "1":
                                students[studentID-1].setExam1Score(score); 
                                System.out.println(students[studentID-1].getExam1Score());
                                break;
                            case "2":
                                students[studentID-1].setExam2Score(score); 
                                System.out.println(students[studentID-1].getExam2Score());
                                break;
                            case "3":
                                students[studentID-1].setExam3Score(score);
                                System.out.println(students[studentID-1].getExam3Score());
                                break;
                            case "WrittenFinal":
                                students[studentID-1].setWrittenFinalScore(score);
                                System.out.println(students[studentID-1].getWrittenFinalScore());
                                break;
                            case "PracticalFinal":
                                students[studentID-1].setPracticalFinalScore(score);
                                System.out.println(students[studentID-1].getPracticalFinalScore());
                                break;
                            default:
                                System.out.println("");
                                System.out.println("Not a valid exam selection.");
                        }
                    } catch(NullPointerException e) {
                        System.out.println("");
                        System.out.println("Students array not initialized; load input file.");  
                    } catch(NumberFormatException e1) {
                        System.out.println("");
                        System.out.println("Number format exception; studentID and score must be integer.");
                    } catch(ArrayIndexOutOfBoundsException e2) {
                        System.out.println("");
                        System.out.println("Invalid studentID use 1-" + numberOfStudents + ".");
                    } catch(IllegalArgumentException e3) {
                        System.out.println("");
                        System.out.println("Valid scores are 0-100.");
                    }
                    break;

                case "6":
                    System.out.println("Update a student's assignment score.");
                    // prompt the user for a student ID, an assignment number, and a score, 
                    System.out.print("Enter student id (1-" + numberOfStudents + "): ");
                    assignmentValid = false;
                    try {
                        studentID = Integer.parseInt(in.nextLine());
                        System.out.print("Enter the assignment number (1-" + Student.TOTAL_ASSIGNMENTS + "): ");
                        assignmentNumber = Integer.parseInt(in.nextLine());
                        System.out.print("Enter assignment score (0-100) to save in the gradebook: ");
                        score = Integer.parseInt(in.nextLine());
                        if (assignmentNumber >= 1 && assignmentNumber <= Student.TOTAL_ASSIGNMENTS) {           
                            assignmentValid = true;
                        }
                        if (studentID >= 1 && studentID <= numberOfStudents && assignmentValid &&
                        score >= 0 && score <= 100) {
                            System.out.println("");
                            System.out.print(studentID + ". ");
                        }
                        //save that score in the gradebook
                        students[studentID-1].setAssignmentScore(assignmentNumber, score);
                        System.out.println(students[studentID-1].getAssignmentScore(assignmentNumber));
                    } catch(NullPointerException e) {
                        System.out.println("");
                        System.out.println("Students array not initialized; load input file."); 
                    } catch(NumberFormatException e1) {
                        System.out.println("");
                        System.out.println("Number format exception: all assignment data needs to be integer.");
                    } catch(IllegalArgumentException e2) {
                        System.out.println("");
                        System.out.println("Bad numberOfAssignments use 1-" + Student.TOTAL_ASSIGNMENTS);
                        System.out.println("OR");
                        System.out.println("Bad score use 0-100.");
                    }
                    break;

                case "7":
                    System.out.println("Overall grades for the class:");
                    int j = 0;   
                    try {
                        while (j < students.length) {
                            System.out.println((j+1) + ". " + students[j].getLastName() + ", " +
                                students[j].getFirstName() + ", " + students[j].getGradeAverage());
                            j++;
                        }
                    } catch(NullPointerException e) {
                        System.out.println("Students array not initialized; load input file."); 
                    }
                    break;

                case "8":
                    //double gradeAverage[] = new double[students.length];
                    // get gradeAverage Array
                    //for (int m = 0; m < students.length; m++) {
                    //    gradeAverage[m] = students[m].getGradeAverage();
                    //}
                    String studentArray[][] = getStudentData();
                    printArray(studentArray);
                    break;

                case "9":
                    String[][] sortedArray = sort(getStudentData());
                    //System.out.printf("%d%n", sortedArray.length);
                    printArray(sortedArray);
                    break;

                case "10":
                    // write the current student data to file
                    System.out.print("Enter the name of the output file ");
                    fileName= in.nextLine();
                    try {
                        File fout = new File(fileName);
                        PrintWriter fileOut = new PrintWriter(new FileWriter(fout));
                        //first line of file is numberOfStudents
                        fileOut.println(students.length);
                        //write student data for each student, 1 student per line
                        for (int k = 0; k < students.length; k++) {
                            fileOut.print(students[k].exportStudentData());
                            if (k < students.length - 1) {
                                fileOut.println("");
                            }
                        } 
                        System.out.println("");
                        System.out.println("See output file " + fileName + ".");
                        fileOut.close();
                    } catch(IOException e ) {
                        System.out.println("");
                        System.out.println("File doesn't exist.");
                    } catch(NullPointerException e1) { 
                        System.out.println("");
                        System.out.println("Students array not initialized; load input file."); 
                    }
                    break;

                case "11":
                    System.out.println("Good bye!");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Not a valid choice. Try again.");
            }
        }
    }
}
